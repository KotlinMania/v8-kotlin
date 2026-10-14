// port-lint: source binding.rs
package io.github.kotlinmania.v8

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointed
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.IntVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.cstr
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.set
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toKString
import kotlinx.cinterop.toLong
import kotlinx.cinterop.value
import v8.cinterop.v8_HeapCodeStatistics
import v8.cinterop.v8_HeapCodeStatistics_Init
import v8.cinterop.v8_HeapSpaceStatistics
import v8.cinterop.v8_HeapSpaceStatistics_Init
import v8.cinterop.v8_HeapStatistics
import v8.cinterop.v8__Isolate__ClearKeptObjects
import v8.cinterop.v8__Isolate__Dispose
import v8.cinterop.v8__Isolate__Enter
import v8.cinterop.v8__Isolate__Exit
import v8.cinterop.v8__Isolate__GetCurrent
import v8.cinterop.v8__Isolate__GetData
import v8.cinterop.v8__Isolate__GetHeapCodeStatistics
import v8.cinterop.v8__Isolate__GetHeapSpaceStatistics
import v8.cinterop.v8__Isolate__GetHeapStatistics
import v8.cinterop.v8__Isolate__GetMicrotasksPolicy
import v8.cinterop.v8__Isolate__GetNumberOfDataSlots
import v8.cinterop.v8__Isolate__LowMemoryNotification
import v8.cinterop.v8__Isolate__MemoryPressureNotification
import v8.cinterop.v8__Isolate__NewDefault
import v8.cinterop.v8__Isolate__PerformMicrotaskCheckpoint
import v8.cinterop.v8__Isolate__SetData
import v8.cinterop.v8__Isolate__SetMicrotasksPolicy
import v8.cinterop.v8__Platform__DELETE
import v8.cinterop.v8__Platform__GetThreadPoolSize
import v8.cinterop.v8__Platform__HasIdleTaskSupport
import v8.cinterop.v8__Platform__IsSingleThreaded
import v8.cinterop.v8__Platform__IsUnprotected
import v8.cinterop.v8__Platform__NewDefaultPlatform
import v8.cinterop.v8__Platform__NewSingleThreadedDefaultPlatform
import v8.cinterop.v8__Platform__NewUnprotectedDefaultPlatform
import v8.cinterop.v8__Platform__NotifyIsolateShutdown
import v8.cinterop.v8__Platform__PumpMessageLoop
import v8.cinterop.v8__Platform__RunIdleTasks
import v8.cinterop.v8__V8__Dispose
import v8.cinterop.v8__V8__DisposePlatform
import v8.cinterop.v8__V8__GetFlagsString
import v8.cinterop.v8__V8__GetVersion
import v8.cinterop.v8__V8__Initialize
import v8.cinterop.v8__V8__InitializePlatform
import v8.cinterop.v8__V8__IsSandboxEnabled
import v8.cinterop.v8__V8__SetFlagsFromCommandLine
import v8.cinterop.v8__V8__SetFlagsFromString
import v8.cinterop.v8_binding_big_uint64_array_max_length
import v8.cinterop.v8_binding_build_number
import v8.cinterop.v8_binding_cppgc_member_size
import v8.cinterop.v8_binding_cppgc_weak_member_size
import v8.cinterop.v8_binding_eternal_size
import v8.cinterop.v8_binding_major_version
import v8.cinterop.v8_binding_minor_version
import v8.cinterop.v8_binding_patch_level
import v8.cinterop.v8_binding_script_origin_size
import v8.cinterop.v8_binding_string_max_length
import v8.cinterop.v8_binding_string_value_view_size
import v8.cinterop.v8_binding_traced_reference_size
import v8.cinterop.v8_binding_typed_array_max_byte_length
import v8.cinterop.v8_binding_typed_array_max_size_in_heap
import v8.cinterop.v8_binding_uint16_array_max_length
import v8.cinterop.v8_binding_uint32_array_max_length
import v8.cinterop.v8_binding_uint8_array_max_length
import v8.cinterop.v8_binding_version_string
import v8.cinterop.v8_support_allow_javascript_execution_scope_size
import v8.cinterop.v8_support_bool_to_maybe_bool
import v8.cinterop.v8_support_cached_data_size
import v8.cinterop.v8_support_disallow_javascript_execution_scope_size
import v8.cinterop.v8_support_escapable_handle_scope_size
import v8.cinterop.v8_support_handle_scope_size
import v8.cinterop.v8_support_locker_size
import v8.cinterop.v8_support_maybe_bool_is_nothing
import v8.cinterop.v8_support_maybe_bool_nothing
import v8.cinterop.v8_support_maybe_bool_to_bool
import v8.cinterop.v8_support_promise_reject_message_size
import v8.cinterop.v8_support_three_pointers_size
import v8.cinterop.v8_support_try_catch_size
import v8.cinterop.v8_support_two_pointers_size

@OptIn(ExperimentalForeignApi::class)
private fun Long.toVoidPtr(): COpaquePointer? = if (this == 0L) null else this.toCPointer<CPointed>()

@OptIn(ExperimentalForeignApi::class)
internal actual object V8NativeBridge {
    actual fun bindingMajorVersion(): Int = v8_binding_major_version().toInt()

    actual fun bindingMinorVersion(): Int = v8_binding_minor_version().toInt()

    actual fun bindingBuildNumber(): Int = v8_binding_build_number().toInt()

    actual fun bindingPatchLevel(): Int = v8_binding_patch_level().toInt()

    actual fun bindingVersionString(): String = v8_binding_version_string()?.toKString() ?: ""

    actual fun bindingScriptOriginSize(): Long = v8_binding_script_origin_size().toLong()

    actual fun bindingCppgcMemberSize(): Long = v8_binding_cppgc_member_size().toLong()

    actual fun bindingCppgcWeakMemberSize(): Long = v8_binding_cppgc_weak_member_size().toLong()

    actual fun bindingTracedReferenceSize(): Long = v8_binding_traced_reference_size().toLong()

    actual fun bindingEternalSize(): Long = v8_binding_eternal_size().toLong()

    actual fun bindingStringValueViewSize(): Long = v8_binding_string_value_view_size().toLong()

    actual fun bindingStringMaxLength(): Int = v8_binding_string_max_length()

    actual fun bindingTypedArrayMaxByteLength(): Long = v8_binding_typed_array_max_byte_length().toLong()

    actual fun bindingTypedArrayMaxSizeInHeap(): Long = v8_binding_typed_array_max_size_in_heap().toLong()

    actual fun bindingUint8ArrayMaxLength(): Long = v8_binding_uint8_array_max_length().toLong()

    actual fun bindingUint16ArrayMaxLength(): Long = v8_binding_uint16_array_max_length().toLong()

    actual fun bindingUint32ArrayMaxLength(): Long = v8_binding_uint32_array_max_length().toLong()

    actual fun bindingBigUint64ArrayMaxLength(): Long = v8_binding_big_uint64_array_max_length().toLong()

    actual fun supportBoolToMaybeBool(value: Boolean): Int =
        v8_support_bool_to_maybe_bool(if (value) 1 else 0).toInt()

    actual fun supportMaybeBoolNothing(): Int = v8_support_maybe_bool_nothing().toInt()

    actual fun supportMaybeBoolIsNothing(maybeBool: Int): Boolean =
        v8_support_maybe_bool_is_nothing(maybeBool.toUByte()) != 0

    actual fun supportMaybeBoolToBool(maybeBool: Int): Boolean =
        v8_support_maybe_bool_to_bool(maybeBool.toUByte()) != 0

    actual fun supportTwoPointersSize(): Long = v8_support_two_pointers_size().toLong()

    actual fun supportThreePointersSize(): Long = v8_support_three_pointers_size().toLong()

    actual fun supportHandleScopeSize(): Long = v8_support_handle_scope_size().toLong()

    actual fun supportEscapableHandleScopeSize(): Long = v8_support_escapable_handle_scope_size().toLong()

    actual fun supportPromiseRejectMessageSize(): Long = v8_support_promise_reject_message_size().toLong()

    actual fun supportLockerSize(): Long = v8_support_locker_size().toLong()

    actual fun supportTryCatchSize(): Long = v8_support_try_catch_size().toLong()

    actual fun supportAllowJavascriptExecutionScopeSize(): Long =
        v8_support_allow_javascript_execution_scope_size().toLong()

    actual fun supportDisallowJavascriptExecutionScopeSize(): Long =
        v8_support_disallow_javascript_execution_scope_size().toLong()

    actual fun supportCachedDataSize(): Long = v8_support_cached_data_size().toLong()

    actual fun v8GetVersion(): String = v8__V8__GetVersion()?.toKString() ?: ""

    actual fun v8IsSandboxEnabled(): Boolean = v8__V8__IsSandboxEnabled() != 0

    actual fun v8SetFlagsFromString(flags: String) {
        val bytes = flags.encodeToByteArray()
        v8__V8__SetFlagsFromString(flags, bytes.size.toULong())
    }

    actual fun v8GetFlagsString(): String = v8__V8__GetFlagsString()?.toKString() ?: ""

    actual fun v8SetFlagsFromCommandLine(
        args: List<String>,
        usage: String?,
    ): List<String> =
        memScoped {
            val argcVar = alloc<IntVar>()
            argcVar.value = args.size
            val argv = allocArray<CPointerVar<ByteVar>>(args.size.coerceAtLeast(1))
            for (i in args.indices) {
                argv[i] = args[i].cstr.ptr
            }
            v8__V8__SetFlagsFromCommandLine(argcVar.ptr, argv, usage)
            val newLen = argcVar.value
            val result = mutableListOf<String>()
            for (i in 0 until newLen) {
                result.add(argv[i]?.toKString() ?: "")
            }
            result
        }

    actual fun v8InitializePlatform(platformPtr: Long) {
        v8__V8__InitializePlatform(platformPtr.toVoidPtr())
    }

    actual fun v8Initialize() {
        v8__V8__Initialize()
    }

    actual fun v8Dispose(): Boolean = v8__V8__Dispose() != 0

    actual fun v8DisposePlatform() {
        v8__V8__DisposePlatform()
    }

    actual fun platformNewDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long =
        v8__Platform__NewDefaultPlatform(threadPoolSize, if (idleTaskSupport) 1 else 0)?.toLong() ?: 0L

    actual fun platformNewUnprotectedDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long =
        v8__Platform__NewUnprotectedDefaultPlatform(threadPoolSize, if (idleTaskSupport) 1 else 0)?.toLong() ?: 0L

    actual fun platformNewSingleThreadedDefaultPlatform(idleTaskSupport: Boolean): Long =
        v8__Platform__NewSingleThreadedDefaultPlatform(if (idleTaskSupport) 1 else 0)?.toLong() ?: 0L

    actual fun platformDelete(platformPtr: Long) {
        v8__Platform__DELETE(platformPtr.toVoidPtr())
    }

    actual fun platformPumpMessageLoop(
        platformPtr: Long,
        isolatePtr: Long,
        waitForWork: Boolean,
    ): Boolean =
        v8__Platform__PumpMessageLoop(
            platformPtr.toVoidPtr(),
            isolatePtr.toVoidPtr(),
            if (waitForWork) 1 else 0,
        ) != 0

    actual fun platformRunIdleTasks(
        platformPtr: Long,
        isolatePtr: Long,
        idleTimeInSeconds: Double,
    ) {
        v8__Platform__RunIdleTasks(
            platformPtr.toVoidPtr(),
            isolatePtr.toVoidPtr(),
            idleTimeInSeconds,
        )
    }

    actual fun platformNotifyIsolateShutdown(
        platformPtr: Long,
        isolatePtr: Long,
    ) {
        v8__Platform__NotifyIsolateShutdown(
            platformPtr.toVoidPtr(),
            isolatePtr.toVoidPtr(),
        )
    }

    actual fun platformGetThreadPoolSize(platformPtr: Long): Int =
        v8__Platform__GetThreadPoolSize(platformPtr.toVoidPtr())

    actual fun platformHasIdleTaskSupport(platformPtr: Long): Boolean =
        v8__Platform__HasIdleTaskSupport(platformPtr.toVoidPtr()) != 0

    actual fun platformIsUnprotected(platformPtr: Long): Boolean =
        v8__Platform__IsUnprotected(platformPtr.toVoidPtr()) != 0

    actual fun platformIsSingleThreaded(platformPtr: Long): Boolean =
        v8__Platform__IsSingleThreaded(platformPtr.toVoidPtr()) != 0

    actual fun isolateNewDefault(): Long = v8__Isolate__NewDefault()?.toLong() ?: 0L

    actual fun isolateDispose(isolatePtr: Long) {
        v8__Isolate__Dispose(isolatePtr.toVoidPtr())
    }

    actual fun isolateEnter(isolatePtr: Long) {
        v8__Isolate__Enter(isolatePtr.toVoidPtr())
    }

    actual fun isolateExit(isolatePtr: Long) {
        v8__Isolate__Exit(isolatePtr.toVoidPtr())
    }

    actual fun isolateGetCurrent(): Long = v8__Isolate__GetCurrent()?.toLong() ?: 0L

    actual fun isolateGetNumberOfDataSlots(isolatePtr: Long): Int =
        v8__Isolate__GetNumberOfDataSlots(isolatePtr.toVoidPtr()).toInt()

    actual fun isolateGetData(
        isolatePtr: Long,
        slot: Int,
    ): Long = v8__Isolate__GetData(isolatePtr.toVoidPtr(), slot.toUInt())?.toLong() ?: 0L

    actual fun isolateSetData(
        isolatePtr: Long,
        slot: Int,
        dataPtr: Long,
    ) {
        v8__Isolate__SetData(isolatePtr.toVoidPtr(), slot.toUInt(), dataPtr.toVoidPtr())
    }

    actual fun isolateGetMicrotasksPolicy(isolatePtr: Long): Int =
        v8__Isolate__GetMicrotasksPolicy(isolatePtr.toVoidPtr()).toInt()

    actual fun isolateSetMicrotasksPolicy(
        isolatePtr: Long,
        policy: Int,
    ) {
        v8__Isolate__SetMicrotasksPolicy(isolatePtr.toVoidPtr(), policy.toUInt())
    }

    actual fun isolatePerformMicrotaskCheckpoint(isolatePtr: Long) {
        v8__Isolate__PerformMicrotaskCheckpoint(isolatePtr.toVoidPtr())
    }

    actual fun isolateMemoryPressureNotification(
        isolatePtr: Long,
        level: Int,
    ) {
        v8__Isolate__MemoryPressureNotification(isolatePtr.toVoidPtr(), level.toUByte())
    }

    actual fun isolateLowMemoryNotification(isolatePtr: Long) {
        v8__Isolate__LowMemoryNotification(isolatePtr.toVoidPtr())
    }

    actual fun isolateClearKeptObjects(isolatePtr: Long) {
        v8__Isolate__ClearKeptObjects(isolatePtr.toVoidPtr())
    }

    actual fun isolateGetHeapStatistics(isolatePtr: Long): HeapStatistics =
        memScoped {
            val stats = alloc<v8_HeapStatistics>()
            v8__Isolate__GetHeapStatistics(isolatePtr.toVoidPtr(), stats.ptr)
            HeapStatistics(
                totalHeapSizeValue = stats.total_heap_size_,
                totalHeapSizeExecutableValue = stats.total_heap_size_executable_,
                totalPhysicalSizeValue = stats.total_physical_size_,
                totalAvailableSizeValue = stats.total_available_size_,
                usedHeapSizeValue = stats.used_heap_size_,
                heapSizeLimitValue = stats.heap_size_limit_,
                mallocedMemoryValue = stats.malloced_memory_,
                externalMemoryValue = stats.external_memory_,
                peakMallocedMemoryValue = stats.peak_malloced_memory_,
                doesZapGarbageValue = stats.does_zap_garbage_.toInt() != 0,
                numberOfNativeContextsValue = stats.number_of_native_contexts_,
                numberOfDetachedContextsValue = stats.number_of_detached_contexts_,
                totalGlobalHandlesSizeValue = stats.total_global_handles_size_,
                usedGlobalHandlesSizeValue = stats.used_global_handles_size_,
                totalAllocatedBytesValue = stats.total_allocated_bytes_,
            )
        }

    actual fun isolateGetHeapSpaceStatistics(
        isolatePtr: Long,
        index: Int,
    ): HeapSpaceStatistics? {
        if (index < 0) return null
        return memScoped {
            val stats = alloc<v8_HeapSpaceStatistics>()
            v8_HeapSpaceStatistics_Init(stats.ptr)
            val ok = v8__Isolate__GetHeapSpaceStatistics(isolatePtr.toVoidPtr(), stats.ptr, index.toULong())
            if (ok == 0) {
                null
            } else {
                HeapSpaceStatistics(
                    spaceNameValue = stats.space_name_?.toKString() ?: "",
                    spaceSizeValue = stats.space_size_,
                    spaceUsedSizeValue = stats.space_used_size_,
                    spaceAvailableSizeValue = stats.space_available_size_,
                    physicalSpaceSizeValue = stats.physical_space_size_,
                )
            }
        }
    }

    actual fun isolateGetHeapCodeStatistics(isolatePtr: Long): HeapCodeStatistics? =
        memScoped {
            val stats = alloc<v8_HeapCodeStatistics>()
            v8_HeapCodeStatistics_Init(stats.ptr)
            val ok = v8__Isolate__GetHeapCodeStatistics(isolatePtr.toVoidPtr(), stats.ptr)
            if (ok == 0) {
                null
            } else {
                HeapCodeStatistics(
                    codeAndMetadataSizeValue = stats.code_and_metadata_size_,
                    bytecodeAndMetadataSizeValue = stats.bytecode_and_metadata_size_,
                    externalScriptSourceSizeValue = stats.external_script_source_size_,
                    cpuProfilerMetadataSizeValue = stats.cpu_profiler_metadata_size_,
                )
            }
        }
}
