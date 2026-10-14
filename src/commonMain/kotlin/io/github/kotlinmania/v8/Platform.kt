// port-lint: source platform.rs
package io.github.kotlinmania.v8

/**
 * Interface for customizing V8 platform behavior (`PlatformImpl` in `platform.rs`).
 *
 * Implement this interface to receive notifications when tasks are posted to
 * an isolate's `TaskRunner`. All methods have default no-op implementations.
 */
interface PlatformImpl {
    /** Called when `TaskRunner::PostTask` is invoked for the given isolate. */
    fun postTask(isolatePtr: Long) {}

    /** Called when `TaskRunner::PostNonNestableTask` is invoked for the given isolate. */
    fun postNonNestableTask(isolatePtr: Long) {}

    /** Called when `TaskRunner::PostDelayedTask` is invoked for the given isolate. */
    fun postDelayedTask(
        isolatePtr: Long,
        delayInSeconds: Double,
    ) {}

    /** Called when `TaskRunner::PostNonNestableDelayedTask` is invoked for the given isolate. */
    fun postNonNestableDelayedTask(
        isolatePtr: Long,
        delayInSeconds: Double,
    ) {}

    /** Called when `TaskRunner::PostIdleTask` is invoked for the given isolate. */
    fun postIdleTask(isolatePtr: Long) {}
}

/**
 * V8 platform abstraction (`v8::Platform` in `platform.rs`).
 *
 * Backed by the C binding functions `v8__Platform__*`.
 */
class Platform internal constructor(
    internal var rawPtr: Long,
    private val customImpl: PlatformImpl? = null,
) : AutoCloseable {
    private var sharedRefCount: Int = 1
    private var ownedByGlobalV8: Boolean = false

    /** Returns the raw C pointer to the underlying `v8::Platform`. */
    fun asRawPtr(): Long = rawPtr

    /** Returns the custom `PlatformImpl` delegate if this platform was created via `newCustom`. */
    fun customImplementation(): PlatformImpl? = customImpl

    /** Returns the worker thread pool size configured on this platform. */
    fun threadPoolSize(): Int = V8NativeBridge.platformGetThreadPoolSize(rawPtr)

    /** Returns true if idle task support was enabled on this platform. */
    fun hasIdleTaskSupport(): Boolean = V8NativeBridge.platformHasIdleTaskSupport(rawPtr)

    /** Returns true if thread-isolated allocation protection is disabled on this platform. */
    fun isUnprotected(): Boolean = V8NativeBridge.platformIsUnprotected(rawPtr)

    /** Returns true if this platform was created as a single-threaded default platform. */
    fun isSingleThreaded(): Boolean = V8NativeBridge.platformIsSingleThreaded(rawPtr)

    /** Returns the current shared reference count (`std__shared_ptr__v8__Platform__use_count`). */
    fun useCount(): Long = if (rawPtr == 0L) 0L else sharedRefCount.toLong()

    /** Increments the shared reference count and returns this platform (`make_shared`). */
    fun makeShared(): Platform {
        if (rawPtr != 0L) {
            sharedRefCount += 1
        }
        return this
    }

    internal fun markOwnedByGlobalV8(owned: Boolean) {
        ownedByGlobalV8 = owned
    }

    /**
     * Releases the underlying C `v8::Platform` instance via `v8__Platform__DELETE`.
     */
    override fun close() {
        if (rawPtr != 0L && !ownedByGlobalV8) {
            val ptr = rawPtr
            rawPtr = 0L
            sharedRefCount = 0
            V8NativeBridge.platformDelete(ptr)
        }
    }

    companion object {
        /**
         * Returns a new instance of the default `v8::Platform` implementation.
         */
        fun new(
            threadPoolSize: UInt = 0u,
            idleTaskSupport: Boolean = false,
        ): Platform {
            val clamped = threadPoolSize.coerceAtMost(16u).toInt()
            val ptr = V8NativeBridge.platformNewDefaultPlatform(clamped, idleTaskSupport)
            return Platform(ptr)
        }

        /**
         * Creates a platform identical to the default platform, but without thread-isolated allocations.
         */
        fun newUnprotected(
            threadPoolSize: UInt = 0u,
            idleTaskSupport: Boolean = false,
        ): Platform {
            val clamped = threadPoolSize.coerceAtMost(16u).toInt()
            val ptr = V8NativeBridge.platformNewUnprotectedDefaultPlatform(clamped, idleTaskSupport)
            return Platform(ptr)
        }

        /**
         * The same as `new()` but disables the worker thread pool (`--single-threaded`).
         */
        fun newSingleThreaded(idleTaskSupport: Boolean = false): Platform {
            val ptr = V8NativeBridge.platformNewSingleThreadedDefaultPlatform(idleTaskSupport)
            return Platform(ptr)
        }

        /**
         * Creates a custom platform backed by `DefaultPlatform` that delegates virtual method overrides
         * to `platformImpl`.
         */
        fun newCustom(
            threadPoolSize: UInt = 0u,
            idleTaskSupport: Boolean = false,
            unprotected: Boolean = false,
            platformImpl: PlatformImpl,
        ): Platform {
            val clamped = threadPoolSize.coerceAtMost(16u).toInt()
            val ptr =
                if (unprotected) {
                    V8NativeBridge.platformNewUnprotectedDefaultPlatform(clamped, idleTaskSupport)
                } else {
                    V8NativeBridge.platformNewDefaultPlatform(clamped, idleTaskSupport)
                }
            return Platform(ptr, platformImpl)
        }

        /**
         * Pumps the message loop for the given isolate (`v8__Platform__PumpMessageLoop`).
         */
        fun pumpMessageLoop(
            platform: Platform,
            isolate: IsolateHandle,
            waitForWork: Boolean = false,
        ): Boolean =
            V8NativeBridge.platformPumpMessageLoop(
                platform.rawPtr,
                isolate.asRawPtr(),
                waitForWork,
            )

        /**
         * Runs pending idle tasks for at most `idleTimeInSeconds` seconds (`v8__Platform__RunIdleTasks`).
         */
        fun runIdleTasks(
            platform: Platform,
            isolate: IsolateHandle,
            idleTimeInSeconds: Double,
        ) {
            V8NativeBridge.platformRunIdleTasks(
                platform.rawPtr,
                isolate.asRawPtr(),
                idleTimeInSeconds,
            )
        }

        /**
         * Notifies the platform that the isolate is shutting down (`v8__Platform__NotifyIsolateShutdown`).
         */
        fun notifyIsolateShutdown(
            platform: Platform,
            isolate: IsolateHandle,
        ) {
            V8NativeBridge.platformNotifyIsolateShutdown(
                platform.rawPtr,
                isolate.asRawPtr(),
            )
        }
    }
}

/**
 * Returns a new instance of the default `v8::Platform` implementation (`new_default_platform`).
 */
fun newDefaultPlatform(
    threadPoolSize: UInt = 0u,
    idleTaskSupport: Boolean = false,
): Platform = Platform.new(threadPoolSize, idleTaskSupport)

/**
 * Creates a platform identical to the default platform, but without thread-isolated allocations.
 */
fun newUnprotectedDefaultPlatform(
    threadPoolSize: UInt = 0u,
    idleTaskSupport: Boolean = false,
): Platform = Platform.newUnprotected(threadPoolSize, idleTaskSupport)

/**
 * The same as `newDefaultPlatform()` but disables the worker thread pool.
 */
fun newSingleThreadedDefaultPlatform(idleTaskSupport: Boolean = false): Platform =
    Platform.newSingleThreaded(idleTaskSupport)

/**
 * Creates a custom platform backed by `DefaultPlatform` that delegates virtual method overrides
 * to `platformImpl`.
 */
fun newCustomPlatform(
    threadPoolSize: UInt = 0u,
    idleTaskSupport: Boolean = false,
    unprotected: Boolean = false,
    platformImpl: PlatformImpl,
): Platform = Platform.newCustom(threadPoolSize, idleTaskSupport, unprotected, platformImpl)
