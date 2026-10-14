// port-lint: source binding.rs
@file:OptIn(ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.v8

import io.github.kotlinmania.v8.internal.loadV8Addon
import io.github.kotlinmania.v8.internal.napiBindingBigUint64ArrayMaxLength
import io.github.kotlinmania.v8.internal.napiBindingBuildNumber
import io.github.kotlinmania.v8.internal.napiBindingCppgcMemberSize
import io.github.kotlinmania.v8.internal.napiBindingCppgcWeakMemberSize
import io.github.kotlinmania.v8.internal.napiBindingEternalSize
import io.github.kotlinmania.v8.internal.napiBindingMajorVersion
import io.github.kotlinmania.v8.internal.napiBindingMinorVersion
import io.github.kotlinmania.v8.internal.napiBindingPatchLevel
import io.github.kotlinmania.v8.internal.napiBindingScriptOriginSize
import io.github.kotlinmania.v8.internal.napiBindingStringMaxLength
import io.github.kotlinmania.v8.internal.napiBindingStringValueViewSize
import io.github.kotlinmania.v8.internal.napiBindingTracedReferenceSize
import io.github.kotlinmania.v8.internal.napiBindingTypedArrayMaxByteLength
import io.github.kotlinmania.v8.internal.napiBindingTypedArrayMaxSizeInHeap
import io.github.kotlinmania.v8.internal.napiBindingUint16ArrayMaxLength
import io.github.kotlinmania.v8.internal.napiBindingUint32ArrayMaxLength
import io.github.kotlinmania.v8.internal.napiBindingUint8ArrayMaxLength
import io.github.kotlinmania.v8.internal.napiBindingVersionString
import io.github.kotlinmania.v8.internal.napiIsolateClearKeptObjects
import io.github.kotlinmania.v8.internal.napiIsolateDispose
import io.github.kotlinmania.v8.internal.napiIsolateEnter
import io.github.kotlinmania.v8.internal.napiIsolateExit
import io.github.kotlinmania.v8.internal.napiIsolateGetCurrent
import io.github.kotlinmania.v8.internal.napiIsolateGetData
import io.github.kotlinmania.v8.internal.napiIsolateGetHeapCodeStatisticsField
import io.github.kotlinmania.v8.internal.napiIsolateGetHeapSpaceName
import io.github.kotlinmania.v8.internal.napiIsolateGetHeapSpaceSizeField
import io.github.kotlinmania.v8.internal.napiIsolateGetHeapStatisticsField
import io.github.kotlinmania.v8.internal.napiIsolateGetMicrotasksPolicy
import io.github.kotlinmania.v8.internal.napiIsolateGetNumberOfDataSlots
import io.github.kotlinmania.v8.internal.napiIsolateLowMemoryNotification
import io.github.kotlinmania.v8.internal.napiIsolateMemoryPressureNotification
import io.github.kotlinmania.v8.internal.napiIsolateNewDefault
import io.github.kotlinmania.v8.internal.napiIsolatePerformMicrotaskCheckpoint
import io.github.kotlinmania.v8.internal.napiIsolateSetData
import io.github.kotlinmania.v8.internal.napiIsolateSetMicrotasksPolicy
import io.github.kotlinmania.v8.internal.napiPlatformDelete
import io.github.kotlinmania.v8.internal.napiPlatformGetThreadPoolSize
import io.github.kotlinmania.v8.internal.napiPlatformHasIdleTaskSupport
import io.github.kotlinmania.v8.internal.napiPlatformIsSingleThreaded
import io.github.kotlinmania.v8.internal.napiPlatformIsUnprotected
import io.github.kotlinmania.v8.internal.napiPlatformNewDefaultPlatform
import io.github.kotlinmania.v8.internal.napiPlatformNewSingleThreadedDefaultPlatform
import io.github.kotlinmania.v8.internal.napiPlatformNewUnprotectedDefaultPlatform
import io.github.kotlinmania.v8.internal.napiPlatformNotifyIsolateShutdown
import io.github.kotlinmania.v8.internal.napiPlatformPumpMessageLoop
import io.github.kotlinmania.v8.internal.napiPlatformRunIdleTasks
import io.github.kotlinmania.v8.internal.napiSupportAllowJavascriptExecutionScopeSize
import io.github.kotlinmania.v8.internal.napiSupportBoolToMaybeBool
import io.github.kotlinmania.v8.internal.napiSupportCachedDataSize
import io.github.kotlinmania.v8.internal.napiSupportDisallowJavascriptExecutionScopeSize
import io.github.kotlinmania.v8.internal.napiSupportEscapableHandleScopeSize
import io.github.kotlinmania.v8.internal.napiSupportHandleScopeSize
import io.github.kotlinmania.v8.internal.napiSupportLockerSize
import io.github.kotlinmania.v8.internal.napiSupportMaybeBoolIsNothing
import io.github.kotlinmania.v8.internal.napiSupportMaybeBoolNothing
import io.github.kotlinmania.v8.internal.napiSupportMaybeBoolToBool
import io.github.kotlinmania.v8.internal.napiSupportPromiseRejectMessageSize
import io.github.kotlinmania.v8.internal.napiSupportThreePointersSize
import io.github.kotlinmania.v8.internal.napiSupportTryCatchSize
import io.github.kotlinmania.v8.internal.napiSupportTwoPointersSize
import io.github.kotlinmania.v8.internal.napiV8Dispose
import io.github.kotlinmania.v8.internal.napiV8DisposePlatform
import io.github.kotlinmania.v8.internal.napiV8GetFlagsString
import io.github.kotlinmania.v8.internal.napiV8GetVersion
import io.github.kotlinmania.v8.internal.napiV8Initialize
import io.github.kotlinmania.v8.internal.napiV8InitializePlatform
import io.github.kotlinmania.v8.internal.napiV8IsSandboxEnabled
import io.github.kotlinmania.v8.internal.napiV8SetFlagsFromCommandLineJoined
import io.github.kotlinmania.v8.internal.napiV8SetFlagsFromString

internal actual object V8NativeBridge {
    private val addon: JsAny = checkNotNull(loadV8Addon()) { "Failed to load @kotlinmania/v8-native-bindings" }

    actual fun bindingMajorVersion(): Int = napiBindingMajorVersion(addon)

    actual fun bindingMinorVersion(): Int = napiBindingMinorVersion(addon)

    actual fun bindingBuildNumber(): Int = napiBindingBuildNumber(addon)

    actual fun bindingPatchLevel(): Int = napiBindingPatchLevel(addon)

    actual fun bindingVersionString(): String = napiBindingVersionString(addon)

    actual fun bindingScriptOriginSize(): Long = napiBindingScriptOriginSize(addon).toLong()

    actual fun bindingCppgcMemberSize(): Long = napiBindingCppgcMemberSize(addon).toLong()

    actual fun bindingCppgcWeakMemberSize(): Long = napiBindingCppgcWeakMemberSize(addon).toLong()

    actual fun bindingTracedReferenceSize(): Long = napiBindingTracedReferenceSize(addon).toLong()

    actual fun bindingEternalSize(): Long = napiBindingEternalSize(addon).toLong()

    actual fun bindingStringValueViewSize(): Long = napiBindingStringValueViewSize(addon).toLong()

    actual fun bindingStringMaxLength(): Int = napiBindingStringMaxLength(addon)

    actual fun bindingTypedArrayMaxByteLength(): Long = napiBindingTypedArrayMaxByteLength(addon).toLong()

    actual fun bindingTypedArrayMaxSizeInHeap(): Long = napiBindingTypedArrayMaxSizeInHeap(addon).toLong()

    actual fun bindingUint8ArrayMaxLength(): Long = napiBindingUint8ArrayMaxLength(addon).toLong()

    actual fun bindingUint16ArrayMaxLength(): Long = napiBindingUint16ArrayMaxLength(addon).toLong()

    actual fun bindingUint32ArrayMaxLength(): Long = napiBindingUint32ArrayMaxLength(addon).toLong()

    actual fun bindingBigUint64ArrayMaxLength(): Long = napiBindingBigUint64ArrayMaxLength(addon).toLong()

    actual fun supportBoolToMaybeBool(value: Boolean): Int = napiSupportBoolToMaybeBool(addon, value)

    actual fun supportMaybeBoolNothing(): Int = napiSupportMaybeBoolNothing(addon)

    actual fun supportMaybeBoolIsNothing(maybeBool: Int): Boolean = napiSupportMaybeBoolIsNothing(addon, maybeBool)

    actual fun supportMaybeBoolToBool(maybeBool: Int): Boolean = napiSupportMaybeBoolToBool(addon, maybeBool)

    actual fun supportTwoPointersSize(): Long = napiSupportTwoPointersSize(addon).toLong()

    actual fun supportThreePointersSize(): Long = napiSupportThreePointersSize(addon).toLong()

    actual fun supportHandleScopeSize(): Long = napiSupportHandleScopeSize(addon).toLong()

    actual fun supportEscapableHandleScopeSize(): Long = napiSupportEscapableHandleScopeSize(addon).toLong()

    actual fun supportPromiseRejectMessageSize(): Long = napiSupportPromiseRejectMessageSize(addon).toLong()

    actual fun supportLockerSize(): Long = napiSupportLockerSize(addon).toLong()

    actual fun supportTryCatchSize(): Long = napiSupportTryCatchSize(addon).toLong()

    actual fun supportAllowJavascriptExecutionScopeSize(): Long =
        napiSupportAllowJavascriptExecutionScopeSize(addon).toLong()

    actual fun supportDisallowJavascriptExecutionScopeSize(): Long =
        napiSupportDisallowJavascriptExecutionScopeSize(addon).toLong()

    actual fun supportCachedDataSize(): Long = napiSupportCachedDataSize(addon).toLong()

    actual fun v8GetVersion(): String = napiV8GetVersion(addon)

    actual fun v8IsSandboxEnabled(): Boolean = napiV8IsSandboxEnabled(addon)

    actual fun v8SetFlagsFromString(flags: String) {
        napiV8SetFlagsFromString(addon, flags)
    }

    actual fun v8GetFlagsString(): String = napiV8GetFlagsString(addon)

    actual fun v8SetFlagsFromCommandLine(
        args: List<String>,
        usage: String?,
    ): List<String> {
        if (args.isEmpty()) return emptyList()
        val joined = args.joinToString("\u001F")
        val out = napiV8SetFlagsFromCommandLineJoined(addon, joined, usage)
        return if (out.isEmpty()) emptyList() else out.split('\u001F')
    }

    actual fun v8InitializePlatform(platformPtr: Long) {
        napiV8InitializePlatform(addon, platformPtr.toDouble())
    }

    actual fun v8Initialize() {
        napiV8Initialize(addon)
    }

    actual fun v8Dispose(): Boolean = napiV8Dispose(addon)

    actual fun v8DisposePlatform() {
        napiV8DisposePlatform(addon)
    }

    actual fun platformNewDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long = napiPlatformNewDefaultPlatform(addon, threadPoolSize, idleTaskSupport).toLong()

    actual fun platformNewUnprotectedDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long = napiPlatformNewUnprotectedDefaultPlatform(addon, threadPoolSize, idleTaskSupport).toLong()

    actual fun platformNewSingleThreadedDefaultPlatform(idleTaskSupport: Boolean): Long =
        napiPlatformNewSingleThreadedDefaultPlatform(addon, idleTaskSupport).toLong()

    actual fun platformDelete(platformPtr: Long) {
        napiPlatformDelete(addon, platformPtr.toDouble())
    }

    actual fun platformPumpMessageLoop(
        platformPtr: Long,
        isolatePtr: Long,
        waitForWork: Boolean,
    ): Boolean =
        napiPlatformPumpMessageLoop(
            addon,
            platformPtr.toDouble(),
            isolatePtr.toDouble(),
            waitForWork,
        )

    actual fun platformRunIdleTasks(
        platformPtr: Long,
        isolatePtr: Long,
        idleTimeInSeconds: Double,
    ) {
        napiPlatformRunIdleTasks(
            addon,
            platformPtr.toDouble(),
            isolatePtr.toDouble(),
            idleTimeInSeconds,
        )
    }

    actual fun platformNotifyIsolateShutdown(
        platformPtr: Long,
        isolatePtr: Long,
    ) {
        napiPlatformNotifyIsolateShutdown(
            addon,
            platformPtr.toDouble(),
            isolatePtr.toDouble(),
        )
    }

    actual fun platformGetThreadPoolSize(platformPtr: Long): Int =
        napiPlatformGetThreadPoolSize(addon, platformPtr.toDouble())

    actual fun platformHasIdleTaskSupport(platformPtr: Long): Boolean =
        napiPlatformHasIdleTaskSupport(addon, platformPtr.toDouble())

    actual fun platformIsUnprotected(platformPtr: Long): Boolean =
        napiPlatformIsUnprotected(addon, platformPtr.toDouble())

    actual fun platformIsSingleThreaded(platformPtr: Long): Boolean =
        napiPlatformIsSingleThreaded(addon, platformPtr.toDouble())

    actual fun isolateNewDefault(): Long = napiIsolateNewDefault(addon).toLong()

    actual fun isolateDispose(isolatePtr: Long) {
        napiIsolateDispose(addon, isolatePtr.toDouble())
    }

    actual fun isolateEnter(isolatePtr: Long) {
        napiIsolateEnter(addon, isolatePtr.toDouble())
    }

    actual fun isolateExit(isolatePtr: Long) {
        napiIsolateExit(addon, isolatePtr.toDouble())
    }

    actual fun isolateGetCurrent(): Long = napiIsolateGetCurrent(addon).toLong()

    actual fun isolateGetNumberOfDataSlots(isolatePtr: Long): Int =
        napiIsolateGetNumberOfDataSlots(addon, isolatePtr.toDouble())

    actual fun isolateGetData(
        isolatePtr: Long,
        slot: Int,
    ): Long = napiIsolateGetData(addon, isolatePtr.toDouble(), slot).toLong()

    actual fun isolateSetData(
        isolatePtr: Long,
        slot: Int,
        dataPtr: Long,
    ) {
        napiIsolateSetData(addon, isolatePtr.toDouble(), slot, dataPtr.toDouble())
    }

    actual fun isolateGetMicrotasksPolicy(isolatePtr: Long): Int =
        napiIsolateGetMicrotasksPolicy(addon, isolatePtr.toDouble())

    actual fun isolateSetMicrotasksPolicy(
        isolatePtr: Long,
        policy: Int,
    ) {
        napiIsolateSetMicrotasksPolicy(addon, isolatePtr.toDouble(), policy)
    }

    actual fun isolatePerformMicrotaskCheckpoint(isolatePtr: Long) {
        napiIsolatePerformMicrotaskCheckpoint(addon, isolatePtr.toDouble())
    }

    actual fun isolateMemoryPressureNotification(
        isolatePtr: Long,
        level: Int,
    ) {
        napiIsolateMemoryPressureNotification(addon, isolatePtr.toDouble(), level)
    }

    actual fun isolateLowMemoryNotification(isolatePtr: Long) {
        napiIsolateLowMemoryNotification(addon, isolatePtr.toDouble())
    }

    actual fun isolateClearKeptObjects(isolatePtr: Long) {
        napiIsolateClearKeptObjects(addon, isolatePtr.toDouble())
    }

    actual fun isolateGetHeapStatistics(isolatePtr: Long): HeapStatistics {
        val ptr = isolatePtr.toDouble()
        return HeapStatistics(
            totalHeapSizeValue = napiIsolateGetHeapStatisticsField(addon, ptr, 0).toLong().toULong(),
            totalHeapSizeExecutableValue = napiIsolateGetHeapStatisticsField(addon, ptr, 1).toLong().toULong(),
            totalPhysicalSizeValue = napiIsolateGetHeapStatisticsField(addon, ptr, 2).toLong().toULong(),
            totalAvailableSizeValue = napiIsolateGetHeapStatisticsField(addon, ptr, 3).toLong().toULong(),
            usedHeapSizeValue = napiIsolateGetHeapStatisticsField(addon, ptr, 4).toLong().toULong(),
            heapSizeLimitValue = napiIsolateGetHeapStatisticsField(addon, ptr, 5).toLong().toULong(),
            mallocedMemoryValue = napiIsolateGetHeapStatisticsField(addon, ptr, 6).toLong().toULong(),
            externalMemoryValue = napiIsolateGetHeapStatisticsField(addon, ptr, 7).toLong().toULong(),
            peakMallocedMemoryValue = napiIsolateGetHeapStatisticsField(addon, ptr, 8).toLong().toULong(),
            doesZapGarbageValue = napiIsolateGetHeapStatisticsField(addon, ptr, 9).toLong() != 0L,
            numberOfNativeContextsValue = napiIsolateGetHeapStatisticsField(addon, ptr, 10).toLong().toULong(),
            numberOfDetachedContextsValue = napiIsolateGetHeapStatisticsField(addon, ptr, 11).toLong().toULong(),
            totalGlobalHandlesSizeValue = napiIsolateGetHeapStatisticsField(addon, ptr, 12).toLong().toULong(),
            usedGlobalHandlesSizeValue = napiIsolateGetHeapStatisticsField(addon, ptr, 13).toLong().toULong(),
            totalAllocatedBytesValue = napiIsolateGetHeapStatisticsField(addon, ptr, 14).toLong().toULong(),
        )
    }

    actual fun isolateGetHeapSpaceStatistics(
        isolatePtr: Long,
        index: Int,
    ): HeapSpaceStatistics? {
        if (index < 0) return null
        val ptr = isolatePtr.toDouble()
        val name = napiIsolateGetHeapSpaceName(addon, ptr, index)
        if (name.isEmpty()) return null
        val spaceSize = napiIsolateGetHeapSpaceSizeField(addon, ptr, index, 0)
        if (spaceSize < 0.0) return null
        return HeapSpaceStatistics(
            spaceNameValue = name,
            spaceSizeValue = spaceSize.toLong().toULong(),
            spaceUsedSizeValue = napiIsolateGetHeapSpaceSizeField(addon, ptr, index, 1).toLong().toULong(),
            spaceAvailableSizeValue = napiIsolateGetHeapSpaceSizeField(addon, ptr, index, 2).toLong().toULong(),
            physicalSpaceSizeValue = napiIsolateGetHeapSpaceSizeField(addon, ptr, index, 3).toLong().toULong(),
        )
    }

    actual fun isolateGetHeapCodeStatistics(isolatePtr: Long): HeapCodeStatistics? {
        val ptr = isolatePtr.toDouble()
        val codeSize = napiIsolateGetHeapCodeStatisticsField(addon, ptr, 0)
        if (codeSize < 0.0) return null
        return HeapCodeStatistics(
            codeAndMetadataSizeValue = codeSize.toLong().toULong(),
            bytecodeAndMetadataSizeValue = napiIsolateGetHeapCodeStatisticsField(addon, ptr, 1).toLong().toULong(),
            externalScriptSourceSizeValue = napiIsolateGetHeapCodeStatisticsField(addon, ptr, 2).toLong().toULong(),
            cpuProfilerMetadataSizeValue = napiIsolateGetHeapCodeStatisticsField(addon, ptr, 3).toLong().toULong(),
        )
    }
}
