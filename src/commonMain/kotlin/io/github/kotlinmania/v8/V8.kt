// port-lint: source V8.rs
package io.github.kotlinmania.v8

/**
 * Callback function invoked when V8 needs a source of entropy (`v8::EntropySource`).
 */
fun interface EntropySourceCallback {
    fun fillEntropy(length: Int): Boolean
}

/**
 * Callback invoked in the case of CHECK failures or fatal V8 errors (`v8::V8FatalErrorCallback`).
 */
fun interface FatalErrorCallback {
    fun onFatalError(
        file: String,
        line: Int,
        message: String,
    )
}

/**
 * Global V8 lifecycle states (`GlobalState` in `V8.rs`).
 */
enum class V8GlobalState {
    Uninitialized,
    PlatformInitialized,
    Initialized,
    Disposed,
    PlatformShutdown,
}

/**
 *Global V8 initialization, flag configuration, and platform lifecycle (`V8.rs`).
 */
object V8Engine {
    private var globalState: V8GlobalState = V8GlobalState.Uninitialized
    private var currentPlatform: Platform? = null
    private var entropySource: EntropySourceCallback? = null
    private var fatalErrorHandler: FatalErrorCallback? = null

    /** Returns the current global lifecycle state. */
    fun state(): V8GlobalState = globalState

    /**
     * Asserts that V8 has been initialized (`V8::assert_initialized`).
     */
    fun assertInitialized() {
        check(globalState == V8GlobalState.Initialized) { "Invalid global state: $globalState" }
    }

    /**
     * Passes command line arguments to V8 (`V8::set_flags_from_command_line`).
     *
     * The first element of `args` (which corresponds to the binary name) is ignored.
     * Returns a list of command line arguments that V8 did not understand.
     */
    fun setFlagsFromCommandLine(args: List<String>): List<String> =
        setFlagsFromCommandLineWithUsage(args, null)

    /**
     * Passes command line arguments to V8 with an optional custom usage string
     * (`V8::set_flags_from_command_line_with_usage`).
     */
    fun setFlagsFromCommandLineWithUsage(
        args: List<String>,
        usage: String?,
    ): List<String> = V8NativeBridge.v8SetFlagsFromCommandLine(args, usage)

    /**
     * Sets V8 flags from a string (`V8::set_flags_from_string`).
     */
    fun setFlagsFromString(flags: String) {
        V8NativeBridge.v8SetFlagsFromString(flags)
    }

    /**
     * Returns the accumulated V8 flags string applied to the C binding layer.
     */
    fun getFlagsString(): String = V8NativeBridge.v8GetFlagsString()

    /**
     * Allows the host application to provide a callback used as a source of entropy
     * (`V8::set_entropy_source`).
     */
    fun setEntropySource(callback: EntropySourceCallback) {
        entropySource = callback
    }

    /** Returns the configured entropy source callback, if any. */
    fun getEntropySource(): EntropySourceCallback? = entropySource

    /**
     * Gets the V8 version string (`V8::get_version`).
     */
    fun getVersion(): String = V8NativeBridge.v8GetVersion()

    /**
     * Sets the `v8::Platform` to use (`V8::initialize_platform`).
     * Must be invoked before V8 is initialized.
     */
    fun initializePlatform(platform: Platform) {
        check(globalState == V8GlobalState.Uninitialized) { "Invalid global state: $globalState" }
        platform.markOwnedByGlobalV8(true)
        currentPlatform = platform
        globalState = V8GlobalState.PlatformInitialized
        V8NativeBridge.v8InitializePlatform(platform.asRawPtr())
    }

    /**
     * Initializes V8 (`V8::initialize`). Must be called after `initializePlatform` and
     * before the first isolate is created.
     */
    fun initialize() {
        check(globalState == V8GlobalState.PlatformInitialized) { "Invalid global state: $globalState" }
        globalState = V8GlobalState.Initialized
        V8NativeBridge.v8Initialize()
    }

    /**
     * Returns the current `v8::Platform` (`V8::get_current_platform`).
     */
    fun getCurrentPlatform(): Platform {
        check(globalState == V8GlobalState.Initialized) { "Invalid global state: $globalState" }
        return checkNotNull(currentPlatform) { "Invalid global state: platform missing" }
    }

    /**
     * Releases resources used by V8 (`V8::dispose`).
     */
    fun dispose(): Boolean {
        check(globalState == V8GlobalState.Initialized) { "Invalid global state: $globalState" }
        globalState = V8GlobalState.Disposed
        val ok = V8NativeBridge.v8Dispose()
        check(ok) { "v8__V8__Dispose returned false" }
        return true
    }

    /**
     * Clears all references to the `v8::Platform` after V8 was disposed (`V8::dispose_platform`).
     */
    fun disposePlatform() {
        check(globalState == V8GlobalState.Disposed) { "Invalid global state: $globalState" }
        V8NativeBridge.v8DisposePlatform()
        val plat = currentPlatform
        currentPlatform = null
        globalState = V8GlobalState.PlatformShutdown
        if (plat != null) {
            plat.markOwnedByGlobalV8(false)
            plat.close()
        }
    }

    /**
     * Sets the callback to invoke in the case of CHECK failures or fatal errors
     * (`V8::set_fatal_error_handler`).
     */
    fun setFatalErrorHandler(callback: FatalErrorCallback) {
        fatalErrorHandler = callback
    }

    /** Returns the configured fatal error callback, if any. */
    fun getFatalErrorHandler(): FatalErrorCallback? = fatalErrorHandler

    /**
     * Returns true if the V8 sandbox is enabled (`v8__V8__IsSandboxEnabled`).
     */
    fun isSandboxEnabled(): Boolean = V8NativeBridge.v8IsSandboxEnabled()

    /**
     * Resets the global V8 state machine to `Uninitialized` so unit tests can exercise
     * multiple initialization/disposal lifecycles in a single process.
     */
    fun resetForTesting() {
        val plat = currentPlatform
        currentPlatform = null
        if (plat != null) {
            plat.markOwnedByGlobalV8(false)
            plat.close()
        }
        V8NativeBridge.v8DisposePlatform()
        globalState = V8GlobalState.Uninitialized
    }
}

/** Asserts that V8 has been initialized (`V8::assert_initialized`). */
fun assertInitialized() = V8Engine.assertInitialized()

/** Passes command line arguments to V8 (`V8::set_flags_from_command_line`). */
fun setFlagsFromCommandLine(args: List<String>): List<String> = V8Engine.setFlagsFromCommandLine(args)

/** Passes command line arguments to V8 with usage (`V8::set_flags_from_command_line_with_usage`). */
fun setFlagsFromCommandLineWithUsage(
    args: List<String>,
    usage: String?,
): List<String> = V8Engine.setFlagsFromCommandLineWithUsage(args, usage)

/** Sets V8 flags from a string (`V8::set_flags_from_string`). */
fun setFlagsFromString(flags: String) = V8Engine.setFlagsFromString(flags)

/** Sets the V8 entropy source callback (`V8::set_entropy_source`). */
fun setEntropySource(callback: EntropySourceCallback) = V8Engine.setEntropySource(callback)

/** Gets the V8 version string (`V8::get_version`). */
fun getVersion(): String = V8Engine.getVersion()

/** Sets the `v8::Platform` to use (`V8::initialize_platform`). */
fun initializePlatform(platform: Platform) = V8Engine.initializePlatform(platform)

/** Initializes V8 (`V8::initialize`). */
fun initialize() = V8Engine.initialize()

/** Gets the current `v8::Platform` (`V8::get_current_platform`). */
fun getCurrentPlatform(): Platform = V8Engine.getCurrentPlatform()

/** Releases resources used by V8 (`V8::dispose`). */
fun dispose(): Boolean = V8Engine.dispose()

/** Disposes the `v8::Platform` after `dispose()` (`V8::dispose_platform`). */
fun disposePlatform() = V8Engine.disposePlatform()

/** Sets the fatal error callback (`V8::set_fatal_error_handler`). */
fun setFatalErrorHandler(callback: FatalErrorCallback) = V8Engine.setFatalErrorHandler(callback)

/** Returns true if the V8 sandbox is enabled (`v8__V8__IsSandboxEnabled`). */
fun isSandboxEnabled(): Boolean = V8Engine.isSandboxEnabled()

/**
 * Handle to a native `v8::Isolate` instance backed by the C ABI (`v8__Isolate__*`).
 */
class IsolateHandle internal constructor(
    private var rawPtr: Long,
) : AutoCloseable {
    /** Returns the raw C pointer to the underlying `v8::Isolate`. */
    fun asRawPtr(): Long = rawPtr

    /** Returns true if this isolate has been disposed. */
    fun isDisposed(): Boolean = rawPtr == 0L

    /** Enters this isolate (`v8__Isolate__Enter`). */
    fun enter() {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateEnter(rawPtr)
    }

    /** Exits this isolate (`v8__Isolate__Exit`). */
    fun exit() {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateExit(rawPtr)
    }

    /** Returns the number of embedder data slots (`v8__Isolate__GetNumberOfDataSlots`). */
    fun getNumberOfDataSlots(): UInt {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return V8NativeBridge.isolateGetNumberOfDataSlots(rawPtr).toUInt()
    }

    /** Gets the pointer stored in the given embedder data slot (`v8__Isolate__GetData`). */
    fun getData(slot: UInt): Long {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return V8NativeBridge.isolateGetData(rawPtr, slot.toInt())
    }

    /** Sets the pointer stored in the given embedder data slot (`v8__Isolate__SetData`). */
    fun setData(
        slot: UInt,
        dataPtr: Long,
    ) {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateSetData(rawPtr, slot.toInt(), dataPtr)
    }

    /** Returns the current microtasks policy (`v8__Isolate__GetMicrotasksPolicy`). */
    fun getMicrotasksPolicy(): MicrotasksPolicy {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return when (V8NativeBridge.isolateGetMicrotasksPolicy(rawPtr)) {
            0 -> MicrotasksPolicy.EXPLICIT
            1 -> MicrotasksPolicy.SCOPED
            else -> MicrotasksPolicy.AUTO
        }
    }

    /** Sets the microtasks policy (`v8__Isolate__SetMicrotasksPolicy`). */
    fun setMicrotasksPolicy(policy: MicrotasksPolicy) {
        check(rawPtr != 0L) { "Isolate is disposed" }
        val raw =
            when (policy) {
                MicrotasksPolicy.EXPLICIT -> 0
                MicrotasksPolicy.SCOPED -> 1
                MicrotasksPolicy.AUTO -> 2
            }
        V8NativeBridge.isolateSetMicrotasksPolicy(rawPtr, raw)
    }

    /** Runs the default MicrotaskQueue until empty (`v8__Isolate__PerformMicrotaskCheckpoint`). */
    fun performMicrotaskCheckpoint() {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolatePerformMicrotaskCheckpoint(rawPtr)
    }

    /** Notifies the isolate of memory pressure (`v8__Isolate__MemoryPressureNotification`). */
    fun memoryPressureNotification(level: MemoryPressureLevel) {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateMemoryPressureNotification(rawPtr, level.code.toInt())
    }

    /** Sends a low-memory notification to the isolate (`v8__Isolate__LowMemoryNotification`). */
    fun lowMemoryNotification() {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateLowMemoryNotification(rawPtr)
    }

    /** Clears WeakRef/FinalizationRegistry kept objects (`v8__Isolate__ClearKeptObjects`). */
    fun clearKeptObjects() {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateClearKeptObjects(rawPtr)
    }

    /** Collects V8 heap statistics for this isolate (`v8__Isolate__GetHeapStatistics`). */
    fun getHeapStatistics(): HeapStatistics {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return V8NativeBridge.isolateGetHeapStatistics(rawPtr)
    }

    /** Collects statistics for a specific V8 heap space (`v8__Isolate__GetHeapSpaceStatistics`). */
    fun getHeapSpaceStatistics(index: UInt): HeapSpaceStatistics? {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return V8NativeBridge.isolateGetHeapSpaceStatistics(rawPtr, index.toInt())
    }

    /** Collects code and metadata statistics for this isolate (`v8__Isolate__GetHeapCodeStatistics`). */
    fun getHeapCodeStatistics(): HeapCodeStatistics? {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return V8NativeBridge.isolateGetHeapCodeStatistics(rawPtr)
    }

    /** Disposes this isolate (`v8__Isolate__Dispose`). */
    fun dispose() {
        if (rawPtr != 0L) {
            val ptr = rawPtr
            rawPtr = 0L
            V8NativeBridge.isolateDispose(ptr)
        }
    }

    override fun close() {
        dispose()
    }

    companion object {
        /** Creates and enters a new isolate (`v8__Isolate__NewDefault`). */
        fun create(params: CreateParams = CreateParams()): IsolateHandle {
            params.finalize()
            val ptr = V8NativeBridge.isolateNewDefault()
            return IsolateHandle(ptr)
        }

        /** Returns the raw pointer to the currently entered isolate (`v8__Isolate__GetCurrent`). */
        fun currentRawPtr(): Long = V8NativeBridge.isolateGetCurrent()
    }
}
