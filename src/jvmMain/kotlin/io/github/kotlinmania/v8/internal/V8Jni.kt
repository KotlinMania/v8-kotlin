// port-lint: source binding.rs
package io.github.kotlinmania.v8.internal

internal object V8Jni {
    val isLoaded: Boolean =
        run {
            System.loadLibrary("v8_jni")
            true
        }

    external fun bindingMajorVersion(): Int

    external fun bindingMinorVersion(): Int

    external fun bindingBuildNumber(): Int

    external fun bindingPatchLevel(): Int

    external fun bindingVersionString(): String

    external fun bindingScriptOriginSize(): Long

    external fun bindingCppgcMemberSize(): Long

    external fun bindingCppgcWeakMemberSize(): Long

    external fun bindingTracedReferenceSize(): Long

    external fun bindingEternalSize(): Long

    external fun bindingStringValueViewSize(): Long

    external fun bindingStringMaxLength(): Int

    external fun bindingTypedArrayMaxByteLength(): Long

    external fun bindingTypedArrayMaxSizeInHeap(): Long

    external fun bindingUint8ArrayMaxLength(): Long

    external fun bindingUint16ArrayMaxLength(): Long

    external fun bindingUint32ArrayMaxLength(): Long

    external fun bindingBigUint64ArrayMaxLength(): Long

    external fun supportBoolToMaybeBool(value: Boolean): Int

    external fun supportMaybeBoolNothing(): Int

    external fun supportMaybeBoolIsNothing(maybeBool: Int): Boolean

    external fun supportMaybeBoolToBool(maybeBool: Int): Boolean

    external fun supportTwoPointersSize(): Long

    external fun supportThreePointersSize(): Long

    external fun supportHandleScopeSize(): Long

    external fun supportEscapableHandleScopeSize(): Long

    external fun supportPromiseRejectMessageSize(): Long

    external fun supportLockerSize(): Long

    external fun supportTryCatchSize(): Long

    external fun supportAllowJavascriptExecutionScopeSize(): Long

    external fun supportDisallowJavascriptExecutionScopeSize(): Long

    external fun supportCachedDataSize(): Long

    external fun v8GetVersion(): String

    external fun v8IsSandboxEnabled(): Boolean

    external fun v8SetFlagsFromString(flags: String)

    external fun v8GetFlagsString(): String

    external fun v8SetFlagsFromCommandLine(
        args: Array<String>,
        usage: String?,
    ): Array<String>

    external fun v8InitializePlatform(platformPtr: Long)

    external fun v8Initialize()

    external fun v8Dispose(): Boolean

    external fun v8DisposePlatform()

    external fun platformNewDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long

    external fun platformNewUnprotectedDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long

    external fun platformNewSingleThreadedDefaultPlatform(idleTaskSupport: Boolean): Long

    external fun platformDelete(platformPtr: Long)

    external fun platformPumpMessageLoop(
        platformPtr: Long,
        isolatePtr: Long,
        waitForWork: Boolean,
    ): Boolean

    external fun platformRunIdleTasks(
        platformPtr: Long,
        isolatePtr: Long,
        idleTimeInSeconds: Double,
    )

    external fun platformNotifyIsolateShutdown(
        platformPtr: Long,
        isolatePtr: Long,
    )

    external fun platformGetThreadPoolSize(platformPtr: Long): Int

    external fun platformHasIdleTaskSupport(platformPtr: Long): Boolean

    external fun platformIsUnprotected(platformPtr: Long): Boolean

    external fun platformIsSingleThreaded(platformPtr: Long): Boolean

    external fun isolateNewDefault(): Long

    external fun isolateDispose(isolatePtr: Long)

    external fun isolateEnter(isolatePtr: Long)

    external fun isolateExit(isolatePtr: Long)

    external fun isolateGetCurrent(): Long

    external fun isolateGetNumberOfDataSlots(isolatePtr: Long): Int

    external fun isolateGetData(
        isolatePtr: Long,
        slot: Int,
    ): Long

    external fun isolateSetData(
        isolatePtr: Long,
        slot: Int,
        dataPtr: Long,
    )

    external fun isolateGetMicrotasksPolicy(isolatePtr: Long): Int

    external fun isolateSetMicrotasksPolicy(
        isolatePtr: Long,
        policy: Int,
    )

    external fun isolatePerformMicrotaskCheckpoint(isolatePtr: Long)

    external fun isolateMemoryPressureNotification(
        isolatePtr: Long,
        level: Int,
    )

    external fun isolateLowMemoryNotification(isolatePtr: Long)

    external fun isolateClearKeptObjects(isolatePtr: Long)

    external fun isolateGetHeapStatistics(isolatePtr: Long): LongArray

    external fun isolateGetHeapSpaceName(
        isolatePtr: Long,
        index: Int,
    ): String?

    external fun isolateGetHeapSpaceSizes(
        isolatePtr: Long,
        index: Int,
    ): LongArray?

    external fun isolateGetHeapCodeStatistics(isolatePtr: Long): LongArray?
}
