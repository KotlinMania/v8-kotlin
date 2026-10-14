// port-lint: source binding.rs
package io.github.kotlinmania.v8

import io.github.kotlinmania.v8.internal.V8Jni

internal actual object V8NativeBridge {
    init {
        check(V8Jni.isLoaded)
    }

    actual fun bindingMajorVersion(): Int = V8Jni.bindingMajorVersion()

    actual fun bindingMinorVersion(): Int = V8Jni.bindingMinorVersion()

    actual fun bindingBuildNumber(): Int = V8Jni.bindingBuildNumber()

    actual fun bindingPatchLevel(): Int = V8Jni.bindingPatchLevel()

    actual fun bindingVersionString(): String = V8Jni.bindingVersionString()

    actual fun bindingScriptOriginSize(): Long = V8Jni.bindingScriptOriginSize()

    actual fun bindingCppgcMemberSize(): Long = V8Jni.bindingCppgcMemberSize()

    actual fun bindingCppgcWeakMemberSize(): Long = V8Jni.bindingCppgcWeakMemberSize()

    actual fun bindingTracedReferenceSize(): Long = V8Jni.bindingTracedReferenceSize()

    actual fun bindingEternalSize(): Long = V8Jni.bindingEternalSize()

    actual fun bindingStringValueViewSize(): Long = V8Jni.bindingStringValueViewSize()

    actual fun bindingStringMaxLength(): Int = V8Jni.bindingStringMaxLength()

    actual fun bindingTypedArrayMaxByteLength(): Long = V8Jni.bindingTypedArrayMaxByteLength()

    actual fun bindingTypedArrayMaxSizeInHeap(): Long = V8Jni.bindingTypedArrayMaxSizeInHeap()

    actual fun bindingUint8ArrayMaxLength(): Long = V8Jni.bindingUint8ArrayMaxLength()

    actual fun bindingUint16ArrayMaxLength(): Long = V8Jni.bindingUint16ArrayMaxLength()

    actual fun bindingUint32ArrayMaxLength(): Long = V8Jni.bindingUint32ArrayMaxLength()

    actual fun bindingBigUint64ArrayMaxLength(): Long = V8Jni.bindingBigUint64ArrayMaxLength()

    actual fun supportBoolToMaybeBool(value: Boolean): Int = V8Jni.supportBoolToMaybeBool(value)

    actual fun supportMaybeBoolNothing(): Int = V8Jni.supportMaybeBoolNothing()

    actual fun supportMaybeBoolIsNothing(maybeBool: Int): Boolean = V8Jni.supportMaybeBoolIsNothing(maybeBool)

    actual fun supportMaybeBoolToBool(maybeBool: Int): Boolean = V8Jni.supportMaybeBoolToBool(maybeBool)

    actual fun supportTwoPointersSize(): Long = V8Jni.supportTwoPointersSize()

    actual fun supportThreePointersSize(): Long = V8Jni.supportThreePointersSize()

    actual fun supportHandleScopeSize(): Long = V8Jni.supportHandleScopeSize()

    actual fun supportEscapableHandleScopeSize(): Long = V8Jni.supportEscapableHandleScopeSize()

    actual fun supportPromiseRejectMessageSize(): Long = V8Jni.supportPromiseRejectMessageSize()

    actual fun supportLockerSize(): Long = V8Jni.supportLockerSize()

    actual fun supportTryCatchSize(): Long = V8Jni.supportTryCatchSize()

    actual fun supportAllowJavascriptExecutionScopeSize(): Long = V8Jni.supportAllowJavascriptExecutionScopeSize()

    actual fun supportDisallowJavascriptExecutionScopeSize(): Long =
        V8Jni.supportDisallowJavascriptExecutionScopeSize()

    actual fun supportCachedDataSize(): Long = V8Jni.supportCachedDataSize()

    actual fun v8GetVersion(): String = V8Jni.v8GetVersion()

    actual fun v8IsSandboxEnabled(): Boolean = V8Jni.v8IsSandboxEnabled()

    actual fun v8SetFlagsFromString(flags: String) {
        V8Jni.v8SetFlagsFromString(flags)
    }

    actual fun v8GetFlagsString(): String = V8Jni.v8GetFlagsString()

    actual fun v8SetFlagsFromCommandLine(
        args: List<String>,
        usage: String?,
    ): List<String> = V8Jni.v8SetFlagsFromCommandLine(args.toTypedArray(), usage).toList()

    actual fun v8InitializePlatform(platformPtr: Long) {
        V8Jni.v8InitializePlatform(platformPtr)
    }

    actual fun v8Initialize() {
        V8Jni.v8Initialize()
    }

    actual fun v8Dispose(): Boolean = V8Jni.v8Dispose()

    actual fun v8DisposePlatform() {
        V8Jni.v8DisposePlatform()
    }

    actual fun platformNewDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long = V8Jni.platformNewDefaultPlatform(threadPoolSize, idleTaskSupport)

    actual fun platformNewUnprotectedDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long = V8Jni.platformNewUnprotectedDefaultPlatform(threadPoolSize, idleTaskSupport)

    actual fun platformNewSingleThreadedDefaultPlatform(idleTaskSupport: Boolean): Long =
        V8Jni.platformNewSingleThreadedDefaultPlatform(idleTaskSupport)

    actual fun platformDelete(platformPtr: Long) {
        V8Jni.platformDelete(platformPtr)
    }

    actual fun platformPumpMessageLoop(
        platformPtr: Long,
        isolatePtr: Long,
        waitForWork: Boolean,
    ): Boolean = V8Jni.platformPumpMessageLoop(platformPtr, isolatePtr, waitForWork)

    actual fun platformRunIdleTasks(
        platformPtr: Long,
        isolatePtr: Long,
        idleTimeInSeconds: Double,
    ) {
        V8Jni.platformRunIdleTasks(platformPtr, isolatePtr, idleTimeInSeconds)
    }

    actual fun platformNotifyIsolateShutdown(
        platformPtr: Long,
        isolatePtr: Long,
    ) {
        V8Jni.platformNotifyIsolateShutdown(platformPtr, isolatePtr)
    }

    actual fun platformGetThreadPoolSize(platformPtr: Long): Int = V8Jni.platformGetThreadPoolSize(platformPtr)

    actual fun platformHasIdleTaskSupport(platformPtr: Long): Boolean = V8Jni.platformHasIdleTaskSupport(platformPtr)

    actual fun platformIsUnprotected(platformPtr: Long): Boolean = V8Jni.platformIsUnprotected(platformPtr)

    actual fun platformIsSingleThreaded(platformPtr: Long): Boolean = V8Jni.platformIsSingleThreaded(platformPtr)

    actual fun isolateNewDefault(): Long = V8Jni.isolateNewDefault()

    actual fun isolateDispose(isolatePtr: Long) {
        V8Jni.isolateDispose(isolatePtr)
    }

    actual fun isolateEnter(isolatePtr: Long) {
        V8Jni.isolateEnter(isolatePtr)
    }

    actual fun isolateExit(isolatePtr: Long) {
        V8Jni.isolateExit(isolatePtr)
    }

    actual fun isolateGetCurrent(): Long = V8Jni.isolateGetCurrent()

    actual fun isolateGetNumberOfDataSlots(isolatePtr: Long): Int = V8Jni.isolateGetNumberOfDataSlots(isolatePtr)

    actual fun isolateGetData(
        isolatePtr: Long,
        slot: Int,
    ): Long = V8Jni.isolateGetData(isolatePtr, slot)

    actual fun isolateSetData(
        isolatePtr: Long,
        slot: Int,
        dataPtr: Long,
    ) {
        V8Jni.isolateSetData(isolatePtr, slot, dataPtr)
    }

    actual fun isolateGetMicrotasksPolicy(isolatePtr: Long): Int = V8Jni.isolateGetMicrotasksPolicy(isolatePtr)

    actual fun isolateSetMicrotasksPolicy(
        isolatePtr: Long,
        policy: Int,
    ) {
        V8Jni.isolateSetMicrotasksPolicy(isolatePtr, policy)
    }

    actual fun isolatePerformMicrotaskCheckpoint(isolatePtr: Long) {
        V8Jni.isolatePerformMicrotaskCheckpoint(isolatePtr)
    }

    actual fun isolateMemoryPressureNotification(
        isolatePtr: Long,
        level: Int,
    ) {
        V8Jni.isolateMemoryPressureNotification(isolatePtr, level)
    }

    actual fun isolateLowMemoryNotification(isolatePtr: Long) {
        V8Jni.isolateLowMemoryNotification(isolatePtr)
    }

    actual fun isolateClearKeptObjects(isolatePtr: Long) {
        V8Jni.isolateClearKeptObjects(isolatePtr)
    }

    actual fun isolateGetHeapStatistics(isolatePtr: Long): HeapStatistics {
        val raw = V8Jni.isolateGetHeapStatistics(isolatePtr)
        return HeapStatistics(
            totalHeapSizeValue = raw[0].toULong(),
            totalHeapSizeExecutableValue = raw[1].toULong(),
            totalPhysicalSizeValue = raw[2].toULong(),
            totalAvailableSizeValue = raw[3].toULong(),
            usedHeapSizeValue = raw[4].toULong(),
            heapSizeLimitValue = raw[5].toULong(),
            mallocedMemoryValue = raw[6].toULong(),
            externalMemoryValue = raw[7].toULong(),
            peakMallocedMemoryValue = raw[8].toULong(),
            doesZapGarbageValue = raw[9] != 0L,
            numberOfNativeContextsValue = raw[10].toULong(),
            numberOfDetachedContextsValue = raw[11].toULong(),
            totalGlobalHandlesSizeValue = raw[12].toULong(),
            usedGlobalHandlesSizeValue = raw[13].toULong(),
            totalAllocatedBytesValue = raw[14].toULong(),
        )
    }

    actual fun isolateGetHeapSpaceStatistics(
        isolatePtr: Long,
        index: Int,
    ): HeapSpaceStatistics? {
        val name = V8Jni.isolateGetHeapSpaceName(isolatePtr, index) ?: return null
        val sizes = V8Jni.isolateGetHeapSpaceSizes(isolatePtr, index) ?: return null
        return HeapSpaceStatistics(
            spaceNameValue = name,
            spaceSizeValue = sizes[0].toULong(),
            spaceUsedSizeValue = sizes[1].toULong(),
            spaceAvailableSizeValue = sizes[2].toULong(),
            physicalSpaceSizeValue = sizes[3].toULong(),
        )
    }

    actual fun isolateGetHeapCodeStatistics(isolatePtr: Long): HeapCodeStatistics? {
        val sizes = V8Jni.isolateGetHeapCodeStatistics(isolatePtr) ?: return null
        return HeapCodeStatistics(
            codeAndMetadataSizeValue = sizes[0].toULong(),
            bytecodeAndMetadataSizeValue = sizes[1].toULong(),
            externalScriptSourceSizeValue = sizes[2].toULong(),
            cpuProfilerMetadataSizeValue = sizes[3].toULong(),
        )
    }
}
