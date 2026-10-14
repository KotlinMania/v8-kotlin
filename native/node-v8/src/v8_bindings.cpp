#include <napi.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>
#include <vector>
#include <string>

#include "v8_wrapper.h"

namespace {

Napi::Value BindingMajorVersion(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_major_version()));
}

Napi::Value BindingMinorVersion(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_minor_version()));
}

Napi::Value BindingBuildNumber(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_build_number()));
}

Napi::Value BindingPatchLevel(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_patch_level()));
}

Napi::Value BindingVersionString(const Napi::CallbackInfo& info) {
    const char* s = v8_binding_version_string();
    return Napi::String::New(info.Env(), s != nullptr ? s : "");
}

Napi::Value BindingScriptOriginSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_script_origin_size()));
}

Napi::Value BindingCppgcMemberSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_cppgc_member_size()));
}

Napi::Value BindingCppgcWeakMemberSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_cppgc_weak_member_size()));
}

Napi::Value BindingTracedReferenceSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_traced_reference_size()));
}

Napi::Value BindingEternalSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_eternal_size()));
}

Napi::Value BindingStringValueViewSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_string_value_view_size()));
}

Napi::Value BindingStringMaxLength(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_string_max_length()));
}

Napi::Value BindingTypedArrayMaxByteLength(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_typed_array_max_byte_length()));
}

Napi::Value BindingTypedArrayMaxSizeInHeap(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_typed_array_max_size_in_heap()));
}

Napi::Value BindingUint8ArrayMaxLength(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_uint8_array_max_length()));
}

Napi::Value BindingUint16ArrayMaxLength(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_uint16_array_max_length()));
}

Napi::Value BindingUint32ArrayMaxLength(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_uint32_array_max_length()));
}

Napi::Value BindingBigUint64ArrayMaxLength(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_binding_big_uint64_array_max_length()));
}

Napi::Value SupportBoolToMaybeBool(const Napi::CallbackInfo& info) {
    int val = (info.Length() > 0 && info[0].ToBoolean().Value()) ? 1 : 0;
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_bool_to_maybe_bool(val)));
}

Napi::Value SupportMaybeBoolNothing(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_maybe_bool_nothing()));
}

Napi::Value SupportMaybeBoolIsNothing(const Napi::CallbackInfo& info) {
    uint8_t raw = info.Length() > 0 ? static_cast<uint8_t>(info[0].As<Napi::Number>().Uint32Value()) : 2U;
    return Napi::Boolean::New(info.Env(), v8_support_maybe_bool_is_nothing(raw) != 0);
}

Napi::Value SupportMaybeBoolToBool(const Napi::CallbackInfo& info) {
    uint8_t raw = info.Length() > 0 ? static_cast<uint8_t>(info[0].As<Napi::Number>().Uint32Value()) : 0U;
    return Napi::Boolean::New(info.Env(), v8_support_maybe_bool_to_bool(raw) != 0);
}

Napi::Value SupportTwoPointersSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_two_pointers_size()));
}

Napi::Value SupportThreePointersSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_three_pointers_size()));
}

Napi::Value SupportHandleScopeSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_handle_scope_size()));
}

Napi::Value SupportEscapableHandleScopeSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_escapable_handle_scope_size()));
}

Napi::Value SupportPromiseRejectMessageSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_promise_reject_message_size()));
}

Napi::Value SupportLockerSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_locker_size()));
}

Napi::Value SupportTryCatchSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_try_catch_size()));
}

Napi::Value SupportAllowJavascriptExecutionScopeSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_allow_javascript_execution_scope_size()));
}

Napi::Value SupportDisallowJavascriptExecutionScopeSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_disallow_javascript_execution_scope_size()));
}

Napi::Value SupportCachedDataSize(const Napi::CallbackInfo& info) {
    return Napi::Number::New(info.Env(), static_cast<double>(v8_support_cached_data_size()));
}

Napi::Value V8GetVersion(const Napi::CallbackInfo& info) {
    const char* s = v8__V8__GetVersion();
    return Napi::String::New(info.Env(), s != nullptr ? s : "");
}

Napi::Value V8IsSandboxEnabled(const Napi::CallbackInfo& info) {
    return Napi::Boolean::New(info.Env(), v8__V8__IsSandboxEnabled() != 0);
}

Napi::Value V8SetFlagsFromString(const Napi::CallbackInfo& info) {
    if (info.Length() > 0 && info[0].IsString()) {
        std::string s = info[0].As<Napi::String>().Utf8Value();
        v8__V8__SetFlagsFromString(s.c_str(), s.size());
    }
    return info.Env().Undefined();
}

Napi::Value V8GetFlagsString(const Napi::CallbackInfo& info) {
    const char* s = v8__V8__GetFlagsString();
    return Napi::String::New(info.Env(), s != nullptr ? s : "");
}

Napi::Value V8SetFlagsFromCommandLineJoined(const Napi::CallbackInfo& info) {
    if (info.Length() == 0 || !info[0].IsString()) {
        return Napi::String::New(info.Env(), "");
    }
    std::string joined = info[0].As<Napi::String>().Utf8Value();
    std::string usage_str;
    const char* usage_ptr = nullptr;
    if (info.Length() > 1 && info[1].IsString()) {
        usage_str = info[1].As<Napi::String>().Utf8Value();
        usage_ptr = usage_str.c_str();
    }
    std::vector<std::string> parts;
    size_t start = 0;
    while (start <= joined.size()) {
        size_t pos = joined.find('\x1F', start);
        if (pos == std::string::npos) {
            parts.push_back(joined.substr(start));
            break;
        }
        parts.push_back(joined.substr(start, pos - start));
        start = pos + 1;
    }
    int argc = static_cast<int>(parts.size());
    std::vector<char*> argv(static_cast<size_t>(argc > 0 ? argc : 1), nullptr);
    for (int i = 0; i < argc; i++) {
        argv[static_cast<size_t>(i)] = parts[static_cast<size_t>(i)].data();
    }
    v8__V8__SetFlagsFromCommandLine(&argc, argv.data(), usage_ptr);
    std::string out;
    for (int i = 0; i < argc; i++) {
        if (i > 0) out.push_back('\x1F');
        if (argv[static_cast<size_t>(i)] != nullptr) {
            out.append(argv[static_cast<size_t>(i)]);
        }
    }
    return Napi::String::New(info.Env(), out);
}

Napi::Value V8InitializePlatform(const Napi::CallbackInfo& info) {
    uintptr_t ptr = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    v8__V8__InitializePlatform(reinterpret_cast<void*>(ptr));
    return info.Env().Undefined();
}

Napi::Value V8Initialize(const Napi::CallbackInfo& info) {
    v8__V8__Initialize();
    return info.Env().Undefined();
}

Napi::Value V8Dispose(const Napi::CallbackInfo& info) {
    return Napi::Boolean::New(info.Env(), v8__V8__Dispose() != 0);
}

Napi::Value V8DisposePlatform(const Napi::CallbackInfo& info) {
    v8__V8__DisposePlatform();
    return info.Env().Undefined();
}

Napi::Value PlatformNewDefaultPlatform(const Napi::CallbackInfo& info) {
    int pool = info.Length() > 0 ? info[0].As<Napi::Number>().Int32Value() : 0;
    int idle = (info.Length() > 1 && info[1].ToBoolean().Value()) ? 1 : 0;
    void* p = v8__Platform__NewDefaultPlatform(pool, idle);
    return Napi::Number::New(info.Env(), static_cast<double>(reinterpret_cast<uintptr_t>(p)));
}

Napi::Value PlatformNewUnprotectedDefaultPlatform(const Napi::CallbackInfo& info) {
    int pool = info.Length() > 0 ? info[0].As<Napi::Number>().Int32Value() : 0;
    int idle = (info.Length() > 1 && info[1].ToBoolean().Value()) ? 1 : 0;
    void* p = v8__Platform__NewUnprotectedDefaultPlatform(pool, idle);
    return Napi::Number::New(info.Env(), static_cast<double>(reinterpret_cast<uintptr_t>(p)));
}

Napi::Value PlatformNewSingleThreadedDefaultPlatform(const Napi::CallbackInfo& info) {
    int idle = (info.Length() > 0 && info[0].ToBoolean().Value()) ? 1 : 0;
    void* p = v8__Platform__NewSingleThreadedDefaultPlatform(idle);
    return Napi::Number::New(info.Env(), static_cast<double>(reinterpret_cast<uintptr_t>(p)));
}

Napi::Value PlatformDelete(const Napi::CallbackInfo& info) {
    uintptr_t ptr = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    v8__Platform__DELETE(reinterpret_cast<void*>(ptr));
    return info.Env().Undefined();
}

Napi::Value PlatformPumpMessageLoop(const Napi::CallbackInfo& info) {
    uintptr_t plat = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    uintptr_t iso = info.Length() > 1 ? static_cast<uintptr_t>(info[1].As<Napi::Number>().Int64Value()) : 0;
    int wait = (info.Length() > 2 && info[2].ToBoolean().Value()) ? 1 : 0;
    int r = v8__Platform__PumpMessageLoop(reinterpret_cast<void*>(plat), reinterpret_cast<void*>(iso), wait);
    return Napi::Boolean::New(info.Env(), r != 0);
}

Napi::Value PlatformRunIdleTasks(const Napi::CallbackInfo& info) {
    uintptr_t plat = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    uintptr_t iso = info.Length() > 1 ? static_cast<uintptr_t>(info[1].As<Napi::Number>().Int64Value()) : 0;
    double secs = info.Length() > 2 ? info[2].As<Napi::Number>().DoubleValue() : 0.0;
    v8__Platform__RunIdleTasks(reinterpret_cast<void*>(plat), reinterpret_cast<void*>(iso), secs);
    return info.Env().Undefined();
}

Napi::Value PlatformNotifyIsolateShutdown(const Napi::CallbackInfo& info) {
    uintptr_t plat = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    uintptr_t iso = info.Length() > 1 ? static_cast<uintptr_t>(info[1].As<Napi::Number>().Int64Value()) : 0;
    v8__Platform__NotifyIsolateShutdown(reinterpret_cast<void*>(plat), reinterpret_cast<void*>(iso));
    return info.Env().Undefined();
}

Napi::Value PlatformGetThreadPoolSize(const Napi::CallbackInfo& info) {
    uintptr_t plat = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    return Napi::Number::New(info.Env(), static_cast<double>(v8__Platform__GetThreadPoolSize(reinterpret_cast<const void*>(plat))));
}

Napi::Value PlatformHasIdleTaskSupport(const Napi::CallbackInfo& info) {
    uintptr_t plat = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    return Napi::Boolean::New(info.Env(), v8__Platform__HasIdleTaskSupport(reinterpret_cast<const void*>(plat)) != 0);
}

Napi::Value PlatformIsUnprotected(const Napi::CallbackInfo& info) {
    uintptr_t plat = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    return Napi::Boolean::New(info.Env(), v8__Platform__IsUnprotected(reinterpret_cast<const void*>(plat)) != 0);
}

Napi::Value PlatformIsSingleThreaded(const Napi::CallbackInfo& info) {
    uintptr_t plat = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    return Napi::Boolean::New(info.Env(), v8__Platform__IsSingleThreaded(reinterpret_cast<const void*>(plat)) != 0);
}

Napi::Value IsolateNewDefault(const Napi::CallbackInfo& info) {
    void* iso = v8__Isolate__NewDefault();
    return Napi::Number::New(info.Env(), static_cast<double>(reinterpret_cast<uintptr_t>(iso)));
}

Napi::Value IsolateDispose(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    v8__Isolate__Dispose(reinterpret_cast<void*>(iso));
    return info.Env().Undefined();
}

Napi::Value IsolateEnter(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    v8__Isolate__Enter(reinterpret_cast<void*>(iso));
    return info.Env().Undefined();
}

Napi::Value IsolateExit(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    v8__Isolate__Exit(reinterpret_cast<void*>(iso));
    return info.Env().Undefined();
}

Napi::Value IsolateGetCurrent(const Napi::CallbackInfo& info) {
    void* iso = v8__Isolate__GetCurrent();
    return Napi::Number::New(info.Env(), static_cast<double>(reinterpret_cast<uintptr_t>(iso)));
}

Napi::Value IsolateGetNumberOfDataSlots(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    return Napi::Number::New(info.Env(), static_cast<double>(v8__Isolate__GetNumberOfDataSlots(reinterpret_cast<const void*>(iso))));
}

Napi::Value IsolateGetData(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    uint32_t slot = info.Length() > 1 ? info[1].As<Napi::Number>().Uint32Value() : 0;
    void* data = v8__Isolate__GetData(reinterpret_cast<const void*>(iso), slot);
    return Napi::Number::New(info.Env(), static_cast<double>(reinterpret_cast<uintptr_t>(data)));
}

Napi::Value IsolateSetData(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    uint32_t slot = info.Length() > 1 ? info[1].As<Napi::Number>().Uint32Value() : 0;
    uintptr_t data = info.Length() > 2 ? static_cast<uintptr_t>(info[2].As<Napi::Number>().Int64Value()) : 0;
    v8__Isolate__SetData(reinterpret_cast<void*>(iso), slot, reinterpret_cast<void*>(data));
    return info.Env().Undefined();
}

Napi::Value IsolateGetMicrotasksPolicy(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    return Napi::Number::New(info.Env(), static_cast<double>(v8__Isolate__GetMicrotasksPolicy(reinterpret_cast<const void*>(iso))));
}

Napi::Value IsolateSetMicrotasksPolicy(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    uint32_t policy = info.Length() > 1 ? info[1].As<Napi::Number>().Uint32Value() : 2U;
    v8__Isolate__SetMicrotasksPolicy(reinterpret_cast<void*>(iso), policy);
    return info.Env().Undefined();
}

Napi::Value IsolatePerformMicrotaskCheckpoint(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    v8__Isolate__PerformMicrotaskCheckpoint(reinterpret_cast<void*>(iso));
    return info.Env().Undefined();
}

Napi::Value IsolateMemoryPressureNotification(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    uint8_t level = info.Length() > 1 ? static_cast<uint8_t>(info[1].As<Napi::Number>().Uint32Value()) : 0U;
    v8__Isolate__MemoryPressureNotification(reinterpret_cast<void*>(iso), level);
    return info.Env().Undefined();
}

Napi::Value IsolateLowMemoryNotification(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    v8__Isolate__LowMemoryNotification(reinterpret_cast<void*>(iso));
    return info.Env().Undefined();
}

Napi::Value IsolateClearKeptObjects(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    v8__Isolate__ClearKeptObjects(reinterpret_cast<void*>(iso));
    return info.Env().Undefined();
}

Napi::Value IsolateGetHeapStatisticsField(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    int field = info.Length() > 1 ? info[1].As<Napi::Number>().Int32Value() : 0;
    v8_HeapStatistics stats;
    v8__Isolate__GetHeapStatistics(reinterpret_cast<void*>(iso), &stats);
    double val = 0.0;
    switch (field) {
        case 0: val = static_cast<double>(stats.total_heap_size_); break;
        case 1: val = static_cast<double>(stats.total_heap_size_executable_); break;
        case 2: val = static_cast<double>(stats.total_physical_size_); break;
        case 3: val = static_cast<double>(stats.total_available_size_); break;
        case 4: val = static_cast<double>(stats.used_heap_size_); break;
        case 5: val = static_cast<double>(stats.heap_size_limit_); break;
        case 6: val = static_cast<double>(stats.malloced_memory_); break;
        case 7: val = static_cast<double>(stats.external_memory_); break;
        case 8: val = static_cast<double>(stats.peak_malloced_memory_); break;
        case 9: val = static_cast<double>(stats.does_zap_garbage_); break;
        case 10: val = static_cast<double>(stats.number_of_native_contexts_); break;
        case 11: val = static_cast<double>(stats.number_of_detached_contexts_); break;
        case 12: val = static_cast<double>(stats.total_global_handles_size_); break;
        case 13: val = static_cast<double>(stats.used_global_handles_size_); break;
        case 14: val = static_cast<double>(stats.total_allocated_bytes_); break;
        default: val = 0.0; break;
    }
    return Napi::Number::New(info.Env(), val);
}

Napi::Value IsolateGetHeapSpaceName(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    int index = info.Length() > 1 ? info[1].As<Napi::Number>().Int32Value() : -1;
    if (index < 0) return Napi::String::New(info.Env(), "");
    v8_HeapSpaceStatistics stats;
    v8_HeapSpaceStatistics_Init(&stats);
    if (!v8__Isolate__GetHeapSpaceStatistics(reinterpret_cast<void*>(iso), &stats, static_cast<size_t>(index))) {
        return Napi::String::New(info.Env(), "");
    }
    return Napi::String::New(info.Env(), stats.space_name_ != nullptr ? stats.space_name_ : "");
}

Napi::Value IsolateGetHeapSpaceSizeField(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    int index = info.Length() > 1 ? info[1].As<Napi::Number>().Int32Value() : -1;
    int field = info.Length() > 2 ? info[2].As<Napi::Number>().Int32Value() : 0;
    if (index < 0) return Napi::Number::New(info.Env(), -1.0);
    v8_HeapSpaceStatistics stats;
    v8_HeapSpaceStatistics_Init(&stats);
    if (!v8__Isolate__GetHeapSpaceStatistics(reinterpret_cast<void*>(iso), &stats, static_cast<size_t>(index))) {
        return Napi::Number::New(info.Env(), -1.0);
    }
    double val = 0.0;
    switch (field) {
        case 0: val = static_cast<double>(stats.space_size_); break;
        case 1: val = static_cast<double>(stats.space_used_size_); break;
        case 2: val = static_cast<double>(stats.space_available_size_); break;
        case 3: val = static_cast<double>(stats.physical_space_size_); break;
        default: val = 0.0; break;
    }
    return Napi::Number::New(info.Env(), val);
}

Napi::Value IsolateGetHeapCodeStatisticsField(const Napi::CallbackInfo& info) {
    uintptr_t iso = info.Length() > 0 ? static_cast<uintptr_t>(info[0].As<Napi::Number>().Int64Value()) : 0;
    int field = info.Length() > 1 ? info[1].As<Napi::Number>().Int32Value() : 0;
    v8_HeapCodeStatistics stats;
    v8_HeapCodeStatistics_Init(&stats);
    if (!v8__Isolate__GetHeapCodeStatistics(reinterpret_cast<void*>(iso), &stats)) {
        return Napi::Number::New(info.Env(), -1.0);
    }
    double val = 0.0;
    switch (field) {
        case 0: val = static_cast<double>(stats.code_and_metadata_size_); break;
        case 1: val = static_cast<double>(stats.bytecode_and_metadata_size_); break;
        case 2: val = static_cast<double>(stats.external_script_source_size_); break;
        case 3: val = static_cast<double>(stats.cpu_profiler_metadata_size_); break;
        default: val = 0.0; break;
    }
    return Napi::Number::New(info.Env(), val);
}

}  // namespace

Napi::Object Init(Napi::Env env, Napi::Object exports) {
    exports.Set("bindingMajorVersion", Napi::Function::New(env, BindingMajorVersion));
    exports.Set("bindingMinorVersion", Napi::Function::New(env, BindingMinorVersion));
    exports.Set("bindingBuildNumber", Napi::Function::New(env, BindingBuildNumber));
    exports.Set("bindingPatchLevel", Napi::Function::New(env, BindingPatchLevel));
    exports.Set("bindingVersionString", Napi::Function::New(env, BindingVersionString));
    exports.Set("bindingScriptOriginSize", Napi::Function::New(env, BindingScriptOriginSize));
    exports.Set("bindingCppgcMemberSize", Napi::Function::New(env, BindingCppgcMemberSize));
    exports.Set("bindingCppgcWeakMemberSize", Napi::Function::New(env, BindingCppgcWeakMemberSize));
    exports.Set("bindingTracedReferenceSize", Napi::Function::New(env, BindingTracedReferenceSize));
    exports.Set("bindingEternalSize", Napi::Function::New(env, BindingEternalSize));
    exports.Set("bindingStringValueViewSize", Napi::Function::New(env, BindingStringValueViewSize));
    exports.Set("bindingStringMaxLength", Napi::Function::New(env, BindingStringMaxLength));
    exports.Set("bindingTypedArrayMaxByteLength", Napi::Function::New(env, BindingTypedArrayMaxByteLength));
    exports.Set("bindingTypedArrayMaxSizeInHeap", Napi::Function::New(env, BindingTypedArrayMaxSizeInHeap));
    exports.Set("bindingUint8ArrayMaxLength", Napi::Function::New(env, BindingUint8ArrayMaxLength));
    exports.Set("bindingUint16ArrayMaxLength", Napi::Function::New(env, BindingUint16ArrayMaxLength));
    exports.Set("bindingUint32ArrayMaxLength", Napi::Function::New(env, BindingUint32ArrayMaxLength));
    exports.Set("bindingBigUint64ArrayMaxLength", Napi::Function::New(env, BindingBigUint64ArrayMaxLength));
    exports.Set("supportBoolToMaybeBool", Napi::Function::New(env, SupportBoolToMaybeBool));
    exports.Set("supportMaybeBoolNothing", Napi::Function::New(env, SupportMaybeBoolNothing));
    exports.Set("supportMaybeBoolIsNothing", Napi::Function::New(env, SupportMaybeBoolIsNothing));
    exports.Set("supportMaybeBoolToBool", Napi::Function::New(env, SupportMaybeBoolToBool));
    exports.Set("supportTwoPointersSize", Napi::Function::New(env, SupportTwoPointersSize));
    exports.Set("supportThreePointersSize", Napi::Function::New(env, SupportThreePointersSize));
    exports.Set("supportHandleScopeSize", Napi::Function::New(env, SupportHandleScopeSize));
    exports.Set("supportEscapableHandleScopeSize", Napi::Function::New(env, SupportEscapableHandleScopeSize));
    exports.Set("supportPromiseRejectMessageSize", Napi::Function::New(env, SupportPromiseRejectMessageSize));
    exports.Set("supportLockerSize", Napi::Function::New(env, SupportLockerSize));
    exports.Set("supportTryCatchSize", Napi::Function::New(env, SupportTryCatchSize));
    exports.Set("supportAllowJavascriptExecutionScopeSize", Napi::Function::New(env, SupportAllowJavascriptExecutionScopeSize));
    exports.Set("supportDisallowJavascriptExecutionScopeSize", Napi::Function::New(env, SupportDisallowJavascriptExecutionScopeSize));
    exports.Set("supportCachedDataSize", Napi::Function::New(env, SupportCachedDataSize));
    exports.Set("v8GetVersion", Napi::Function::New(env, V8GetVersion));
    exports.Set("v8IsSandboxEnabled", Napi::Function::New(env, V8IsSandboxEnabled));
    exports.Set("v8SetFlagsFromString", Napi::Function::New(env, V8SetFlagsFromString));
    exports.Set("v8GetFlagsString", Napi::Function::New(env, V8GetFlagsString));
    exports.Set("v8SetFlagsFromCommandLineJoined", Napi::Function::New(env, V8SetFlagsFromCommandLineJoined));
    exports.Set("v8InitializePlatform", Napi::Function::New(env, V8InitializePlatform));
    exports.Set("v8Initialize", Napi::Function::New(env, V8Initialize));
    exports.Set("v8Dispose", Napi::Function::New(env, V8Dispose));
    exports.Set("v8DisposePlatform", Napi::Function::New(env, V8DisposePlatform));
    exports.Set("platformNewDefaultPlatform", Napi::Function::New(env, PlatformNewDefaultPlatform));
    exports.Set("platformNewUnprotectedDefaultPlatform", Napi::Function::New(env, PlatformNewUnprotectedDefaultPlatform));
    exports.Set("platformNewSingleThreadedDefaultPlatform", Napi::Function::New(env, PlatformNewSingleThreadedDefaultPlatform));
    exports.Set("platformDelete", Napi::Function::New(env, PlatformDelete));
    exports.Set("platformPumpMessageLoop", Napi::Function::New(env, PlatformPumpMessageLoop));
    exports.Set("platformRunIdleTasks", Napi::Function::New(env, PlatformRunIdleTasks));
    exports.Set("platformNotifyIsolateShutdown", Napi::Function::New(env, PlatformNotifyIsolateShutdown));
    exports.Set("platformGetThreadPoolSize", Napi::Function::New(env, PlatformGetThreadPoolSize));
    exports.Set("platformHasIdleTaskSupport", Napi::Function::New(env, PlatformHasIdleTaskSupport));
    exports.Set("platformIsUnprotected", Napi::Function::New(env, PlatformIsUnprotected));
    exports.Set("platformIsSingleThreaded", Napi::Function::New(env, PlatformIsSingleThreaded));
    exports.Set("isolateNewDefault", Napi::Function::New(env, IsolateNewDefault));
    exports.Set("isolateDispose", Napi::Function::New(env, IsolateDispose));
    exports.Set("isolateEnter", Napi::Function::New(env, IsolateEnter));
    exports.Set("isolateExit", Napi::Function::New(env, IsolateExit));
    exports.Set("isolateGetCurrent", Napi::Function::New(env, IsolateGetCurrent));
    exports.Set("isolateGetNumberOfDataSlots", Napi::Function::New(env, IsolateGetNumberOfDataSlots));
    exports.Set("isolateGetData", Napi::Function::New(env, IsolateGetData));
    exports.Set("isolateSetData", Napi::Function::New(env, IsolateSetData));
    exports.Set("isolateGetMicrotasksPolicy", Napi::Function::New(env, IsolateGetMicrotasksPolicy));
    exports.Set("isolateSetMicrotasksPolicy", Napi::Function::New(env, IsolateSetMicrotasksPolicy));
    exports.Set("isolatePerformMicrotaskCheckpoint", Napi::Function::New(env, IsolatePerformMicrotaskCheckpoint));
    exports.Set("isolateMemoryPressureNotification", Napi::Function::New(env, IsolateMemoryPressureNotification));
    exports.Set("isolateLowMemoryNotification", Napi::Function::New(env, IsolateLowMemoryNotification));
    exports.Set("isolateClearKeptObjects", Napi::Function::New(env, IsolateClearKeptObjects));
    exports.Set("isolateGetHeapStatisticsField", Napi::Function::New(env, IsolateGetHeapStatisticsField));
    exports.Set("isolateGetHeapSpaceName", Napi::Function::New(env, IsolateGetHeapSpaceName));
    exports.Set("isolateGetHeapSpaceSizeField", Napi::Function::New(env, IsolateGetHeapSpaceSizeField));
    exports.Set("isolateGetHeapCodeStatisticsField", Napi::Function::New(env, IsolateGetHeapCodeStatisticsField));
    return exports;
}

NODE_API_MODULE(v8_native, Init)
