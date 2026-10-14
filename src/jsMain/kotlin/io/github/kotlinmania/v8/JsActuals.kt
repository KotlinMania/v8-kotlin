// port-lint: source binding.rs
package io.github.kotlinmania.v8

internal actual object V8NativeBridge {
    actual fun bindingMajorVersion(): Int = V8NativeBindings.bindingMajorVersion()

    actual fun bindingMinorVersion(): Int = V8NativeBindings.bindingMinorVersion()

    actual fun bindingBuildNumber(): Int = V8NativeBindings.bindingBuildNumber()

    actual fun bindingPatchLevel(): Int = V8NativeBindings.bindingPatchLevel()

    actual fun bindingVersionString(): String = V8NativeBindings.bindingVersionString()

    actual fun bindingScriptOriginSize(): Long = V8NativeBindings.bindingScriptOriginSize().toLong()

    actual fun bindingCppgcMemberSize(): Long = V8NativeBindings.bindingCppgcMemberSize().toLong()

    actual fun bindingCppgcWeakMemberSize(): Long = V8NativeBindings.bindingCppgcWeakMemberSize().toLong()

    actual fun bindingTracedReferenceSize(): Long = V8NativeBindings.bindingTracedReferenceSize().toLong()

    actual fun bindingEternalSize(): Long = V8NativeBindings.bindingEternalSize().toLong()

    actual fun bindingStringValueViewSize(): Long = V8NativeBindings.bindingStringValueViewSize().toLong()

    actual fun bindingStringMaxLength(): Int = V8NativeBindings.bindingStringMaxLength()

    actual fun bindingTypedArrayMaxByteLength(): Long = V8NativeBindings.bindingTypedArrayMaxByteLength().toLong()

    actual fun bindingTypedArrayMaxSizeInHeap(): Long = V8NativeBindings.bindingTypedArrayMaxSizeInHeap().toLong()

    actual fun bindingUint8ArrayMaxLength(): Long = V8NativeBindings.bindingUint8ArrayMaxLength().toLong()

    actual fun bindingUint16ArrayMaxLength(): Long = V8NativeBindings.bindingUint16ArrayMaxLength().toLong()

    actual fun bindingUint32ArrayMaxLength(): Long = V8NativeBindings.bindingUint32ArrayMaxLength().toLong()

    actual fun bindingBigUint64ArrayMaxLength(): Long = V8NativeBindings.bindingBigUint64ArrayMaxLength().toLong()

    actual fun supportBoolToMaybeBool(value: Boolean): Int = V8NativeBindings.supportBoolToMaybeBool(value)

    actual fun supportMaybeBoolNothing(): Int = V8NativeBindings.supportMaybeBoolNothing()

    actual fun supportMaybeBoolIsNothing(maybeBool: Int): Boolean =
        V8NativeBindings.supportMaybeBoolIsNothing(maybeBool)

    actual fun supportMaybeBoolToBool(maybeBool: Int): Boolean = V8NativeBindings.supportMaybeBoolToBool(maybeBool)

    actual fun supportTwoPointersSize(): Long = V8NativeBindings.supportTwoPointersSize().toLong()

    actual fun supportThreePointersSize(): Long = V8NativeBindings.supportThreePointersSize().toLong()

    actual fun supportHandleScopeSize(): Long = V8NativeBindings.supportHandleScopeSize().toLong()

    actual fun supportEscapableHandleScopeSize(): Long = V8NativeBindings.supportEscapableHandleScopeSize().toLong()

    actual fun supportPromiseRejectMessageSize(): Long = V8NativeBindings.supportPromiseRejectMessageSize().toLong()

    actual fun supportLockerSize(): Long = V8NativeBindings.supportLockerSize().toLong()

    actual fun supportTryCatchSize(): Long = V8NativeBindings.supportTryCatchSize().toLong()

    actual fun supportAllowJavascriptExecutionScopeSize(): Long =
        V8NativeBindings.supportAllowJavascriptExecutionScopeSize().toLong()

    actual fun supportDisallowJavascriptExecutionScopeSize(): Long =
        V8NativeBindings.supportDisallowJavascriptExecutionScopeSize().toLong()

    actual fun supportCachedDataSize(): Long = V8NativeBindings.supportCachedDataSize().toLong()

    actual fun v8GetVersion(): String = V8NativeBindings.v8GetVersion()

    actual fun v8IsSandboxEnabled(): Boolean = V8NativeBindings.v8IsSandboxEnabled()

    actual fun v8SetFlagsFromString(flags: String) {
        V8NativeBindings.v8SetFlagsFromString(flags)
    }

    actual fun v8GetFlagsString(): String = V8NativeBindings.v8GetFlagsString()

    actual fun v8SetFlagsFromCommandLine(
        args: List<String>,
        usage: String?,
    ): List<String> {
        if (args.isEmpty()) return emptyList()
        val joined = args.joinToString("\u001F")
        val out = V8NativeBindings.v8SetFlagsFromCommandLineJoined(joined, usage)
        return if (out.isEmpty()) emptyList() else out.split('\u001F')
    }

    actual fun v8InitializePlatform(platformPtr: Long) {
        V8NativeBindings.v8InitializePlatform(platformPtr.toDouble())
    }

    actual fun v8Initialize() {
        V8NativeBindings.v8Initialize()
    }

    actual fun v8Dispose(): Boolean = V8NativeBindings.v8Dispose()

    actual fun v8DisposePlatform() {
        V8NativeBindings.v8DisposePlatform()
    }

    actual fun platformNewDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long = V8NativeBindings.platformNewDefaultPlatform(threadPoolSize, idleTaskSupport).toLong()

    actual fun platformNewUnprotectedDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long = V8NativeBindings.platformNewUnprotectedDefaultPlatform(threadPoolSize, idleTaskSupport).toLong()

    actual fun platformNewSingleThreadedDefaultPlatform(idleTaskSupport: Boolean): Long =
        V8NativeBindings.platformNewSingleThreadedDefaultPlatform(idleTaskSupport).toLong()

    actual fun platformDelete(platformPtr: Long) {
        V8NativeBindings.platformDelete(platformPtr.toDouble())
    }

    actual fun platformPumpMessageLoop(
        platformPtr: Long,
        isolatePtr: Long,
        waitForWork: Boolean,
    ): Boolean =
        V8NativeBindings.platformPumpMessageLoop(
            platformPtr.toDouble(),
            isolatePtr.toDouble(),
            waitForWork,
        )

    actual fun platformRunIdleTasks(
        platformPtr: Long,
        isolatePtr: Long,
        idleTimeInSeconds: Double,
    ) {
        V8NativeBindings.platformRunIdleTasks(
            platformPtr.toDouble(),
            isolatePtr.toDouble(),
            idleTimeInSeconds,
        )
    }

    actual fun platformNotifyIsolateShutdown(
        platformPtr: Long,
        isolatePtr: Long,
    ) {
        V8NativeBindings.platformNotifyIsolateShutdown(
            platformPtr.toDouble(),
            isolatePtr.toDouble(),
        )
    }

    actual fun platformGetThreadPoolSize(platformPtr: Long): Int =
        V8NativeBindings.platformGetThreadPoolSize(platformPtr.toDouble())

    actual fun platformHasIdleTaskSupport(platformPtr: Long): Boolean =
        V8NativeBindings.platformHasIdleTaskSupport(platformPtr.toDouble())

    actual fun platformIsUnprotected(platformPtr: Long): Boolean =
        V8NativeBindings.platformIsUnprotected(platformPtr.toDouble())

    actual fun platformIsSingleThreaded(platformPtr: Long): Boolean =
        V8NativeBindings.platformIsSingleThreaded(platformPtr.toDouble())

    actual fun isolateNewDefault(): Long = V8NativeBindings.isolateNewDefault().toLong()

    actual fun isolateDispose(isolatePtr: Long) {
        V8NativeBindings.isolateDispose(isolatePtr.toDouble())
    }

    actual fun isolateEnter(isolatePtr: Long) {
        V8NativeBindings.isolateEnter(isolatePtr.toDouble())
    }

    actual fun isolateExit(isolatePtr: Long) {
        V8NativeBindings.isolateExit(isolatePtr.toDouble())
    }

    actual fun isolateGetCurrent(): Long = V8NativeBindings.isolateGetCurrent().toLong()

    actual fun isolateGetNumberOfDataSlots(isolatePtr: Long): Int =
        V8NativeBindings.isolateGetNumberOfDataSlots(isolatePtr.toDouble())

    actual fun isolateGetData(
        isolatePtr: Long,
        slot: Int,
    ): Long = V8NativeBindings.isolateGetData(isolatePtr.toDouble(), slot).toLong()

    actual fun isolateSetData(
        isolatePtr: Long,
        slot: Int,
        dataPtr: Long,
    ) {
        V8NativeBindings.isolateSetData(isolatePtr.toDouble(), slot, dataPtr.toDouble())
    }

    actual fun isolateGetMicrotasksPolicy(isolatePtr: Long): Int =
        V8NativeBindings.isolateGetMicrotasksPolicy(isolatePtr.toDouble())

    actual fun isolateSetMicrotasksPolicy(
        isolatePtr: Long,
        policy: Int,
    ) {
        V8NativeBindings.isolateSetMicrotasksPolicy(isolatePtr.toDouble(), policy)
    }

    actual fun isolatePerformMicrotaskCheckpoint(isolatePtr: Long) {
        V8NativeBindings.isolatePerformMicrotaskCheckpoint(isolatePtr.toDouble())
    }

    actual fun isolateMemoryPressureNotification(
        isolatePtr: Long,
        level: Int,
    ) {
        V8NativeBindings.isolateMemoryPressureNotification(isolatePtr.toDouble(), level)
    }

    actual fun isolateLowMemoryNotification(isolatePtr: Long) {
        V8NativeBindings.isolateLowMemoryNotification(isolatePtr.toDouble())
    }

    actual fun isolateClearKeptObjects(isolatePtr: Long) {
        V8NativeBindings.isolateClearKeptObjects(isolatePtr.toDouble())
    }

    actual fun isolateGetHeapStatistics(isolatePtr: Long): HeapStatistics {
        val ptr = isolatePtr.toDouble()
        return HeapStatistics(
            totalHeapSizeValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 0).toLong().toULong(),
            totalHeapSizeExecutableValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 1).toLong().toULong(),
            totalPhysicalSizeValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 2).toLong().toULong(),
            totalAvailableSizeValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 3).toLong().toULong(),
            usedHeapSizeValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 4).toLong().toULong(),
            heapSizeLimitValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 5).toLong().toULong(),
            mallocedMemoryValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 6).toLong().toULong(),
            externalMemoryValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 7).toLong().toULong(),
            peakMallocedMemoryValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 8).toLong().toULong(),
            doesZapGarbageValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 9).toLong() != 0L,
            numberOfNativeContextsValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 10).toLong().toULong(),
            numberOfDetachedContextsValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 11).toLong().toULong(),
            totalGlobalHandlesSizeValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 12).toLong().toULong(),
            usedGlobalHandlesSizeValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 13).toLong().toULong(),
            totalAllocatedBytesValue = V8NativeBindings.isolateGetHeapStatisticsField(ptr, 14).toLong().toULong(),
        )
    }

    actual fun isolateGetHeapSpaceStatistics(
        isolatePtr: Long,
        index: Int,
    ): HeapSpaceStatistics? {
        if (index < 0) return null
        val ptr = isolatePtr.toDouble()
        val name = V8NativeBindings.isolateGetHeapSpaceName(ptr, index)
        if (name.isEmpty()) return null
        val spaceSize = V8NativeBindings.isolateGetHeapSpaceSizeField(ptr, index, 0)
        if (spaceSize < 0.0) return null
        return HeapSpaceStatistics(
            spaceNameValue = name,
            spaceSizeValue = spaceSize.toLong().toULong(),
            spaceUsedSizeValue = V8NativeBindings.isolateGetHeapSpaceSizeField(ptr, index, 1).toLong().toULong(),
            spaceAvailableSizeValue = V8NativeBindings.isolateGetHeapSpaceSizeField(ptr, index, 2).toLong().toULong(),
            physicalSpaceSizeValue = V8NativeBindings.isolateGetHeapSpaceSizeField(ptr, index, 3).toLong().toULong(),
        )
    }

    actual fun isolateGetHeapCodeStatistics(isolatePtr: Long): HeapCodeStatistics? {
        val ptr = isolatePtr.toDouble()
        val codeSize = V8NativeBindings.isolateGetHeapCodeStatisticsField(ptr, 0)
        if (codeSize < 0.0) return null
        return HeapCodeStatistics(
            codeAndMetadataSizeValue = codeSize.toLong().toULong(),
            bytecodeAndMetadataSizeValue = V8NativeBindings.isolateGetHeapCodeStatisticsField(ptr, 1).toLong().toULong(),
            externalScriptSourceSizeValue = V8NativeBindings.isolateGetHeapCodeStatisticsField(ptr, 2).toLong().toULong(),
            cpuProfilerMetadataSizeValue = V8NativeBindings.isolateGetHeapCodeStatisticsField(ptr, 3).toLong().toULong(),
        )
    }
}
