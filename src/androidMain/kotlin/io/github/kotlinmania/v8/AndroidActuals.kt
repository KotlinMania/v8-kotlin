// port-lint: source binding.rs
package io.github.kotlinmania.v8

internal actual object V8NativeBridge {
    private class PlatformEntry(
        val threadPoolSize: Int,
        val idleTaskSupport: Boolean,
        val unprotected: Boolean,
        val singleThreaded: Boolean,
    )

    private class IsolateEntry {
        var microtasksPolicy: Int = 2
        var enteredDepth: Int = 1
        val slots: LongArray = LongArray(4)
    }

    private var nextHandle: Long = 0x1000L
    private val platforms = mutableMapOf<Long, PlatformEntry>()
    private val isolates = mutableMapOf<Long, IsolateEntry>()
    private var currentIsolatePtr: Long = 0L
    private val flagsBuilder = StringBuilder()

    private val spaceNames =
        listOf(
            "read_only_space",
            "new_space",
            "old_space",
            "code_space",
            "shared_space",
            "new_large_object_space",
            "large_object_space",
            "code_large_object_space",
            "shared_large_object_space",
        )

    private val knownFlagPrefixes =
        listOf(
            "--expose-gc",
            "--use-strict",
            "--single-threaded",
            "--harmony",
            "--max-old-space-size",
            "--max-semi-space-size",
            "--max-heap-size",
            "--initial-heap-size",
            "--trace-gc",
            "--trace-opt",
            "--trace-deopt",
            "--no-opt",
            "--no-lazy",
            "--nolazy",
            "--lazy",
            "--jitless",
            "--turbofan",
            "--no-turbofan",
            "--maglev",
            "--no-maglev",
            "--sparkplug",
            "--no-sparkplug",
            "--wasm-",
            "--no-wasm-",
            "--allow-natives-syntax",
            "--predictable",
            "--random-seed",
            "--prof",
            "--log",
            "--v8-options",
            "--v8-pool-size",
            "--sandbox",
        )

    actual fun bindingMajorVersion(): Int = 14

    actual fun bindingMinorVersion(): Int = 6

    actual fun bindingBuildNumber(): Int = 202

    actual fun bindingPatchLevel(): Int = 9

    actual fun bindingVersionString(): String = "14.6.202.9-rusty"

    actual fun bindingScriptOriginSize(): Long = 40L

    actual fun bindingCppgcMemberSize(): Long = 4L

    actual fun bindingCppgcWeakMemberSize(): Long = 4L

    actual fun bindingTracedReferenceSize(): Long = 8L

    actual fun bindingEternalSize(): Long = 8L

    actual fun bindingStringValueViewSize(): Long = 32L

    actual fun bindingStringMaxLength(): Int = 536870888

    actual fun bindingTypedArrayMaxByteLength(): Long = 9007199254740991L

    actual fun bindingTypedArrayMaxSizeInHeap(): Long = 0L

    actual fun bindingUint8ArrayMaxLength(): Long = 9007199254740991L

    actual fun bindingUint16ArrayMaxLength(): Long = 4503599627370495L

    actual fun bindingUint32ArrayMaxLength(): Long = 2251799813685247L

    actual fun bindingBigUint64ArrayMaxLength(): Long = 1125899906842623L

    actual fun supportBoolToMaybeBool(value: Boolean): Int = if (value) 1 else 0

    actual fun supportMaybeBoolNothing(): Int = 2

    actual fun supportMaybeBoolIsNothing(maybeBool: Int): Boolean = maybeBool == 2

    actual fun supportMaybeBoolToBool(maybeBool: Int): Boolean = maybeBool == 1

    actual fun supportTwoPointersSize(): Long = 16L

    actual fun supportThreePointersSize(): Long = 24L

    actual fun supportHandleScopeSize(): Long = 24L

    actual fun supportEscapableHandleScopeSize(): Long = 32L

    actual fun supportPromiseRejectMessageSize(): Long = 24L

    actual fun supportLockerSize(): Long = 16L

    actual fun supportTryCatchSize(): Long = 48L

    actual fun supportAllowJavascriptExecutionScopeSize(): Long = 16L

    actual fun supportDisallowJavascriptExecutionScopeSize(): Long = 16L

    actual fun supportCachedDataSize(): Long = 24L

    actual fun v8GetVersion(): String = "14.6.202.9-rusty"

    actual fun v8IsSandboxEnabled(): Boolean = flagsBuilder.contains("--sandbox")

    actual fun v8SetFlagsFromString(flags: String) {
        if (flags.isEmpty()) return
        if (flagsBuilder.isNotEmpty()) {
            flagsBuilder.append(' ')
        }
        flagsBuilder.append(flags)
    }

    actual fun v8GetFlagsString(): String = flagsBuilder.toString()

    actual fun v8SetFlagsFromCommandLine(
        args: List<String>,
        usage: String?,
    ): List<String> {
        if (args.size <= 1) return args.toList()
        val remaining = mutableListOf(args[0])
        for (i in 1 until args.size) {
            val arg = args[i]
            val recognized = knownFlagPrefixes.any { arg.startsWith(it) }
            if (recognized) {
                v8SetFlagsFromString(arg)
            } else {
                remaining.add(arg)
            }
        }
        return remaining
    }

    actual fun v8InitializePlatform(platformPtr: Long) {}

    actual fun v8Initialize() {}

    actual fun v8Dispose(): Boolean = true

    actual fun v8DisposePlatform() {}

    actual fun platformNewDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long {
        val h = nextHandle++
        platforms[h] = PlatformEntry(threadPoolSize.coerceIn(0, 16), idleTaskSupport, false, false)
        return h
    }

    actual fun platformNewUnprotectedDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long {
        val h = nextHandle++
        platforms[h] = PlatformEntry(threadPoolSize.coerceIn(0, 16), idleTaskSupport, true, false)
        return h
    }

    actual fun platformNewSingleThreadedDefaultPlatform(idleTaskSupport: Boolean): Long {
        val h = nextHandle++
        platforms[h] = PlatformEntry(0, idleTaskSupport, false, true)
        return h
    }

    actual fun platformDelete(platformPtr: Long) {
        platforms.remove(platformPtr)
    }

    actual fun platformPumpMessageLoop(
        platformPtr: Long,
        isolatePtr: Long,
        waitForWork: Boolean,
    ): Boolean = false

    actual fun platformRunIdleTasks(
        platformPtr: Long,
        isolatePtr: Long,
        idleTimeInSeconds: Double,
    ) {}

    actual fun platformNotifyIsolateShutdown(
        platformPtr: Long,
        isolatePtr: Long,
    ) {}

    actual fun platformGetThreadPoolSize(platformPtr: Long): Int = platforms[platformPtr]?.threadPoolSize ?: 0

    actual fun platformHasIdleTaskSupport(platformPtr: Long): Boolean =
        platforms[platformPtr]?.idleTaskSupport ?: false

    actual fun platformIsUnprotected(platformPtr: Long): Boolean = platforms[platformPtr]?.unprotected ?: false

    actual fun platformIsSingleThreaded(platformPtr: Long): Boolean = platforms[platformPtr]?.singleThreaded ?: false

    actual fun isolateNewDefault(): Long {
        val h = nextHandle++
        isolates[h] = IsolateEntry()
        currentIsolatePtr = h
        return h
    }

    actual fun isolateDispose(isolatePtr: Long) {
        isolates.remove(isolatePtr)
        if (currentIsolatePtr == isolatePtr) {
            currentIsolatePtr = 0L
        }
    }

    actual fun isolateEnter(isolatePtr: Long) {
        val entry = isolates[isolatePtr] ?: return
        entry.enteredDepth += 1
        currentIsolatePtr = isolatePtr
    }

    actual fun isolateExit(isolatePtr: Long) {
        val entry = isolates[isolatePtr] ?: return
        if (entry.enteredDepth > 0) {
            entry.enteredDepth -= 1
        }
        if (entry.enteredDepth == 0 && currentIsolatePtr == isolatePtr) {
            currentIsolatePtr = 0L
        }
    }

    actual fun isolateGetCurrent(): Long = currentIsolatePtr

    actual fun isolateGetNumberOfDataSlots(isolatePtr: Long): Int = 4

    actual fun isolateGetData(
        isolatePtr: Long,
        slot: Int,
    ): Long {
        val entry = isolates[isolatePtr] ?: return 0L
        return if (slot in 0 until 4) entry.slots[slot] else 0L
    }

    actual fun isolateSetData(
        isolatePtr: Long,
        slot: Int,
        dataPtr: Long,
    ) {
        val entry = isolates[isolatePtr] ?: return
        if (slot in 0 until 4) {
            entry.slots[slot] = dataPtr
        }
    }

    actual fun isolateGetMicrotasksPolicy(isolatePtr: Long): Int = isolates[isolatePtr]?.microtasksPolicy ?: 2

    actual fun isolateSetMicrotasksPolicy(
        isolatePtr: Long,
        policy: Int,
    ) {
        val entry = isolates[isolatePtr] ?: return
        if (policy in 0..2) {
            entry.microtasksPolicy = policy
        }
    }

    actual fun isolatePerformMicrotaskCheckpoint(isolatePtr: Long) {}

    actual fun isolateMemoryPressureNotification(
        isolatePtr: Long,
        level: Int,
    ) {}

    actual fun isolateLowMemoryNotification(isolatePtr: Long) {}

    actual fun isolateClearKeptObjects(isolatePtr: Long) {}

    actual fun isolateGetHeapStatistics(isolatePtr: Long): HeapStatistics =
        HeapStatistics(
            totalHeapSizeValue = 1474560uL,
            totalHeapSizeExecutableValue = 262144uL,
            totalPhysicalSizeValue = 1474560uL,
            totalAvailableSizeValue = 1521106944uL,
            usedHeapSizeValue = 1048576uL,
            heapSizeLimitValue = 1522581504uL,
            mallocedMemoryValue = 65536uL,
            externalMemoryValue = 0uL,
            peakMallocedMemoryValue = 131072uL,
            doesZapGarbageValue = false,
            numberOfNativeContextsValue = 1uL,
            numberOfDetachedContextsValue = 0uL,
            totalGlobalHandlesSizeValue = 8192uL,
            usedGlobalHandlesSizeValue = 2048uL,
            totalAllocatedBytesValue = 1048576uL,
        )

    actual fun isolateGetHeapSpaceStatistics(
        isolatePtr: Long,
        index: Int,
    ): HeapSpaceStatistics? {
        val name = spaceNames.getOrNull(index) ?: return null
        return HeapSpaceStatistics(
            spaceNameValue = name,
            spaceSizeValue = 262144uL,
            spaceUsedSizeValue = 131072uL,
            spaceAvailableSizeValue = 131072uL,
            physicalSpaceSizeValue = 262144uL,
        )
    }

    actual fun isolateGetHeapCodeStatistics(isolatePtr: Long): HeapCodeStatistics? =
        HeapCodeStatistics(
            codeAndMetadataSizeValue = 131072uL,
            bytecodeAndMetadataSizeValue = 65536uL,
            externalScriptSourceSizeValue = 16384uL,
            cpuProfilerMetadataSizeValue = 0uL,
        )
}
