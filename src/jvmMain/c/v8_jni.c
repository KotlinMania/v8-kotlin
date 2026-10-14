#include <jni.h>
#include <stdint.h>
#include <stdlib.h>
#include <string.h>

#include "../../nativeInterop/cinterop/v8_wrapper.c"

#define JNI_FN(name) JNICALL Java_io_github_kotlinmania_v8_internal_V8Jni_##name

JNIEXPORT jint JNI_FN(bindingMajorVersion)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jint)v8_binding_major_version();
}

JNIEXPORT jint JNI_FN(bindingMinorVersion)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jint)v8_binding_minor_version();
}

JNIEXPORT jint JNI_FN(bindingBuildNumber)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jint)v8_binding_build_number();
}

JNIEXPORT jint JNI_FN(bindingPatchLevel)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jint)v8_binding_patch_level();
}

JNIEXPORT jstring JNI_FN(bindingVersionString)(JNIEnv* env, jobject thiz) {
    (void)thiz;
    const char* s = v8_binding_version_string();
    return (*env)->NewStringUTF(env, s != NULL ? s : "");
}

JNIEXPORT jlong JNI_FN(bindingScriptOriginSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_script_origin_size();
}

JNIEXPORT jlong JNI_FN(bindingCppgcMemberSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_cppgc_member_size();
}

JNIEXPORT jlong JNI_FN(bindingCppgcWeakMemberSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_cppgc_weak_member_size();
}

JNIEXPORT jlong JNI_FN(bindingTracedReferenceSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_traced_reference_size();
}

JNIEXPORT jlong JNI_FN(bindingEternalSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_eternal_size();
}

JNIEXPORT jlong JNI_FN(bindingStringValueViewSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_string_value_view_size();
}

JNIEXPORT jint JNI_FN(bindingStringMaxLength)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jint)v8_binding_string_max_length();
}

JNIEXPORT jlong JNI_FN(bindingTypedArrayMaxByteLength)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_typed_array_max_byte_length();
}

JNIEXPORT jlong JNI_FN(bindingTypedArrayMaxSizeInHeap)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_typed_array_max_size_in_heap();
}

JNIEXPORT jlong JNI_FN(bindingUint8ArrayMaxLength)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_uint8_array_max_length();
}

JNIEXPORT jlong JNI_FN(bindingUint16ArrayMaxLength)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_uint16_array_max_length();
}

JNIEXPORT jlong JNI_FN(bindingUint32ArrayMaxLength)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_uint32_array_max_length();
}

JNIEXPORT jlong JNI_FN(bindingBigUint64ArrayMaxLength)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_binding_big_uint64_array_max_length();
}

JNIEXPORT jint JNI_FN(supportBoolToMaybeBool)(JNIEnv* env, jobject thiz, jboolean value) {
    (void)env;
    (void)thiz;
    return (jint)v8_support_bool_to_maybe_bool(value ? 1 : 0);
}

JNIEXPORT jint JNI_FN(supportMaybeBoolNothing)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jint)v8_support_maybe_bool_nothing();
}

JNIEXPORT jboolean JNI_FN(supportMaybeBoolIsNothing)(JNIEnv* env, jobject thiz, jint maybeBool) {
    (void)env;
    (void)thiz;
    return v8_support_maybe_bool_is_nothing((uint8_t)maybeBool) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jboolean JNI_FN(supportMaybeBoolToBool)(JNIEnv* env, jobject thiz, jint maybeBool) {
    (void)env;
    (void)thiz;
    return v8_support_maybe_bool_to_bool((uint8_t)maybeBool) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jlong JNI_FN(supportTwoPointersSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_support_two_pointers_size();
}

JNIEXPORT jlong JNI_FN(supportThreePointersSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_support_three_pointers_size();
}

JNIEXPORT jlong JNI_FN(supportHandleScopeSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_support_handle_scope_size();
}

JNIEXPORT jlong JNI_FN(supportEscapableHandleScopeSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_support_escapable_handle_scope_size();
}

JNIEXPORT jlong JNI_FN(supportPromiseRejectMessageSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_support_promise_reject_message_size();
}

JNIEXPORT jlong JNI_FN(supportLockerSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_support_locker_size();
}

JNIEXPORT jlong JNI_FN(supportTryCatchSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_support_try_catch_size();
}

JNIEXPORT jlong JNI_FN(supportAllowJavascriptExecutionScopeSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_support_allow_javascript_execution_scope_size();
}

JNIEXPORT jlong JNI_FN(supportDisallowJavascriptExecutionScopeSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_support_disallow_javascript_execution_scope_size();
}

JNIEXPORT jlong JNI_FN(supportCachedDataSize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)v8_support_cached_data_size();
}

JNIEXPORT jstring JNI_FN(v8GetVersion)(JNIEnv* env, jobject thiz) {
    (void)thiz;
    const char* s = v8__V8__GetVersion();
    return (*env)->NewStringUTF(env, s != NULL ? s : "");
}

JNIEXPORT jboolean JNI_FN(v8IsSandboxEnabled)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return v8__V8__IsSandboxEnabled() ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNI_FN(v8SetFlagsFromString)(JNIEnv* env, jobject thiz, jstring flags) {
    (void)thiz;
    if (flags == NULL) return;
    const char* utf = (*env)->GetStringUTFChars(env, flags, NULL);
    if (utf != NULL) {
        v8__V8__SetFlagsFromString(utf, strlen(utf));
        (*env)->ReleaseStringUTFChars(env, flags, utf);
    }
}

JNIEXPORT jstring JNI_FN(v8GetFlagsString)(JNIEnv* env, jobject thiz) {
    (void)thiz;
    const char* s = v8__V8__GetFlagsString();
    return (*env)->NewStringUTF(env, s != NULL ? s : "");
}

JNIEXPORT jobjectArray JNI_FN(v8SetFlagsFromCommandLine)(
    JNIEnv* env,
    jobject thiz,
    jobjectArray args,
    jstring usage
) {
    (void)thiz;
    jclass strCls = (*env)->FindClass(env, "java/lang/String");
    if (args == NULL) {
        return (*env)->NewObjectArray(env, 0, strCls, NULL);
    }
    jsize len = (*env)->GetArrayLength(env, args);
    int argc = (int)len;
    char** argv = (char**)calloc((size_t)(argc > 0 ? argc : 1), sizeof(char*));
    char** allocated = (char**)calloc((size_t)(argc > 0 ? argc : 1), sizeof(char*));
    for (jsize i = 0; i < len; i++) {
        jstring jstr = (jstring)(*env)->GetObjectArrayElement(env, args, i);
        const char* utf = jstr != NULL ? (*env)->GetStringUTFChars(env, jstr, NULL) : "";
        size_t slen = strlen(utf);
        char* copy = (char*)malloc(slen + 1);
        memcpy(copy, utf, slen + 1);
        argv[i] = copy;
        allocated[i] = copy;
        if (jstr != NULL) {
            (*env)->ReleaseStringUTFChars(env, jstr, utf);
            (*env)->DeleteLocalRef(env, jstr);
        }
    }
    const char* c_usage = usage != NULL ? (*env)->GetStringUTFChars(env, usage, NULL) : NULL;
    v8__V8__SetFlagsFromCommandLine(&argc, argv, c_usage);
    if (usage != NULL && c_usage != NULL) {
        (*env)->ReleaseStringUTFChars(env, usage, c_usage);
    }
    jobjectArray result = (*env)->NewObjectArray(env, (jsize)argc, strCls, NULL);
    for (int i = 0; i < argc; i++) {
        jstring elem = (*env)->NewStringUTF(env, argv[i] != NULL ? argv[i] : "");
        (*env)->SetObjectArrayElement(env, result, (jsize)i, elem);
        (*env)->DeleteLocalRef(env, elem);
    }
    for (jsize i = 0; i < len; i++) {
        free(allocated[i]);
    }
    free(allocated);
    free(argv);
    return result;
}

JNIEXPORT void JNI_FN(v8InitializePlatform)(JNIEnv* env, jobject thiz, jlong platformPtr) {
    (void)env;
    (void)thiz;
    v8__V8__InitializePlatform((void*)(uintptr_t)platformPtr);
}

JNIEXPORT void JNI_FN(v8Initialize)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    v8__V8__Initialize();
}

JNIEXPORT jboolean JNI_FN(v8Dispose)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return v8__V8__Dispose() ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNI_FN(v8DisposePlatform)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    v8__V8__DisposePlatform();
}

JNIEXPORT jlong JNI_FN(platformNewDefaultPlatform)(
    JNIEnv* env,
    jobject thiz,
    jint threadPoolSize,
    jboolean idleTaskSupport
) {
    (void)env;
    (void)thiz;
    void* p = v8__Platform__NewDefaultPlatform((int)threadPoolSize, idleTaskSupport ? 1 : 0);
    return (jlong)(uintptr_t)p;
}

JNIEXPORT jlong JNI_FN(platformNewUnprotectedDefaultPlatform)(
    JNIEnv* env,
    jobject thiz,
    jint threadPoolSize,
    jboolean idleTaskSupport
) {
    (void)env;
    (void)thiz;
    void* p = v8__Platform__NewUnprotectedDefaultPlatform((int)threadPoolSize, idleTaskSupport ? 1 : 0);
    return (jlong)(uintptr_t)p;
}

JNIEXPORT jlong JNI_FN(platformNewSingleThreadedDefaultPlatform)(
    JNIEnv* env,
    jobject thiz,
    jboolean idleTaskSupport
) {
    (void)env;
    (void)thiz;
    void* p = v8__Platform__NewSingleThreadedDefaultPlatform(idleTaskSupport ? 1 : 0);
    return (jlong)(uintptr_t)p;
}

JNIEXPORT void JNI_FN(platformDelete)(JNIEnv* env, jobject thiz, jlong platformPtr) {
    (void)env;
    (void)thiz;
    v8__Platform__DELETE((void*)(uintptr_t)platformPtr);
}

JNIEXPORT jboolean JNI_FN(platformPumpMessageLoop)(
    JNIEnv* env,
    jobject thiz,
    jlong platformPtr,
    jlong isolatePtr,
    jboolean waitForWork
) {
    (void)env;
    (void)thiz;
    int r = v8__Platform__PumpMessageLoop(
        (void*)(uintptr_t)platformPtr,
        (void*)(uintptr_t)isolatePtr,
        waitForWork ? 1 : 0
    );
    return r ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNI_FN(platformRunIdleTasks)(
    JNIEnv* env,
    jobject thiz,
    jlong platformPtr,
    jlong isolatePtr,
    jdouble idleTimeInSeconds
) {
    (void)env;
    (void)thiz;
    v8__Platform__RunIdleTasks(
        (void*)(uintptr_t)platformPtr,
        (void*)(uintptr_t)isolatePtr,
        (double)idleTimeInSeconds
    );
}

JNIEXPORT void JNI_FN(platformNotifyIsolateShutdown)(
    JNIEnv* env,
    jobject thiz,
    jlong platformPtr,
    jlong isolatePtr
) {
    (void)env;
    (void)thiz;
    v8__Platform__NotifyIsolateShutdown(
        (void*)(uintptr_t)platformPtr,
        (void*)(uintptr_t)isolatePtr
    );
}

JNIEXPORT jint JNI_FN(platformGetThreadPoolSize)(JNIEnv* env, jobject thiz, jlong platformPtr) {
    (void)env;
    (void)thiz;
    return (jint)v8__Platform__GetThreadPoolSize((const void*)(uintptr_t)platformPtr);
}

JNIEXPORT jboolean JNI_FN(platformHasIdleTaskSupport)(JNIEnv* env, jobject thiz, jlong platformPtr) {
    (void)env;
    (void)thiz;
    return v8__Platform__HasIdleTaskSupport((const void*)(uintptr_t)platformPtr) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jboolean JNI_FN(platformIsUnprotected)(JNIEnv* env, jobject thiz, jlong platformPtr) {
    (void)env;
    (void)thiz;
    return v8__Platform__IsUnprotected((const void*)(uintptr_t)platformPtr) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jboolean JNI_FN(platformIsSingleThreaded)(JNIEnv* env, jobject thiz, jlong platformPtr) {
    (void)env;
    (void)thiz;
    return v8__Platform__IsSingleThreaded((const void*)(uintptr_t)platformPtr) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jlong JNI_FN(isolateNewDefault)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)(uintptr_t)v8__Isolate__NewDefault();
}

JNIEXPORT void JNI_FN(isolateDispose)(JNIEnv* env, jobject thiz, jlong isolatePtr) {
    (void)env;
    (void)thiz;
    v8__Isolate__Dispose((void*)(uintptr_t)isolatePtr);
}

JNIEXPORT void JNI_FN(isolateEnter)(JNIEnv* env, jobject thiz, jlong isolatePtr) {
    (void)env;
    (void)thiz;
    v8__Isolate__Enter((void*)(uintptr_t)isolatePtr);
}

JNIEXPORT void JNI_FN(isolateExit)(JNIEnv* env, jobject thiz, jlong isolatePtr) {
    (void)env;
    (void)thiz;
    v8__Isolate__Exit((void*)(uintptr_t)isolatePtr);
}

JNIEXPORT jlong JNI_FN(isolateGetCurrent)(JNIEnv* env, jobject thiz) {
    (void)env;
    (void)thiz;
    return (jlong)(uintptr_t)v8__Isolate__GetCurrent();
}

JNIEXPORT jint JNI_FN(isolateGetNumberOfDataSlots)(JNIEnv* env, jobject thiz, jlong isolatePtr) {
    (void)env;
    (void)thiz;
    return (jint)v8__Isolate__GetNumberOfDataSlots((const void*)(uintptr_t)isolatePtr);
}

JNIEXPORT jlong JNI_FN(isolateGetData)(JNIEnv* env, jobject thiz, jlong isolatePtr, jint slot) {
    (void)env;
    (void)thiz;
    return (jlong)(uintptr_t)v8__Isolate__GetData((const void*)(uintptr_t)isolatePtr, (uint32_t)slot);
}

JNIEXPORT void JNI_FN(isolateSetData)(
    JNIEnv* env,
    jobject thiz,
    jlong isolatePtr,
    jint slot,
    jlong dataPtr
) {
    (void)env;
    (void)thiz;
    v8__Isolate__SetData((void*)(uintptr_t)isolatePtr, (uint32_t)slot, (void*)(uintptr_t)dataPtr);
}

JNIEXPORT jint JNI_FN(isolateGetMicrotasksPolicy)(JNIEnv* env, jobject thiz, jlong isolatePtr) {
    (void)env;
    (void)thiz;
    return (jint)v8__Isolate__GetMicrotasksPolicy((const void*)(uintptr_t)isolatePtr);
}

JNIEXPORT void JNI_FN(isolateSetMicrotasksPolicy)(
    JNIEnv* env,
    jobject thiz,
    jlong isolatePtr,
    jint policy
) {
    (void)env;
    (void)thiz;
    v8__Isolate__SetMicrotasksPolicy((void*)(uintptr_t)isolatePtr, (uint32_t)policy);
}

JNIEXPORT void JNI_FN(isolatePerformMicrotaskCheckpoint)(JNIEnv* env, jobject thiz, jlong isolatePtr) {
    (void)env;
    (void)thiz;
    v8__Isolate__PerformMicrotaskCheckpoint((void*)(uintptr_t)isolatePtr);
}

JNIEXPORT void JNI_FN(isolateMemoryPressureNotification)(
    JNIEnv* env,
    jobject thiz,
    jlong isolatePtr,
    jint level
) {
    (void)env;
    (void)thiz;
    v8__Isolate__MemoryPressureNotification((void*)(uintptr_t)isolatePtr, (uint8_t)level);
}

JNIEXPORT void JNI_FN(isolateLowMemoryNotification)(JNIEnv* env, jobject thiz, jlong isolatePtr) {
    (void)env;
    (void)thiz;
    v8__Isolate__LowMemoryNotification((void*)(uintptr_t)isolatePtr);
}

JNIEXPORT void JNI_FN(isolateClearKeptObjects)(JNIEnv* env, jobject thiz, jlong isolatePtr) {
    (void)env;
    (void)thiz;
    v8__Isolate__ClearKeptObjects((void*)(uintptr_t)isolatePtr);
}

JNIEXPORT jlongArray JNI_FN(isolateGetHeapStatistics)(JNIEnv* env, jobject thiz, jlong isolatePtr) {
    (void)thiz;
    v8_HeapStatistics stats;
    v8__Isolate__GetHeapStatistics((void*)(uintptr_t)isolatePtr, &stats);
    jlong values[15];
    values[0] = (jlong)stats.total_heap_size_;
    values[1] = (jlong)stats.total_heap_size_executable_;
    values[2] = (jlong)stats.total_physical_size_;
    values[3] = (jlong)stats.total_available_size_;
    values[4] = (jlong)stats.used_heap_size_;
    values[5] = (jlong)stats.heap_size_limit_;
    values[6] = (jlong)stats.malloced_memory_;
    values[7] = (jlong)stats.external_memory_;
    values[8] = (jlong)stats.peak_malloced_memory_;
    values[9] = (jlong)stats.does_zap_garbage_;
    values[10] = (jlong)stats.number_of_native_contexts_;
    values[11] = (jlong)stats.number_of_detached_contexts_;
    values[12] = (jlong)stats.total_global_handles_size_;
    values[13] = (jlong)stats.used_global_handles_size_;
    values[14] = (jlong)stats.total_allocated_bytes_;
    jlongArray out = (*env)->NewLongArray(env, 15);
    (*env)->SetLongArrayRegion(env, out, 0, 15, values);
    return out;
}

JNIEXPORT jstring JNI_FN(isolateGetHeapSpaceName)(
    JNIEnv* env,
    jobject thiz,
    jlong isolatePtr,
    jint index
) {
    (void)thiz;
    if (index < 0) return NULL;
    v8_HeapSpaceStatistics stats;
    v8_HeapSpaceStatistics_Init(&stats);
    if (!v8__Isolate__GetHeapSpaceStatistics((void*)(uintptr_t)isolatePtr, &stats, (size_t)index)) {
        return NULL;
    }
    return (*env)->NewStringUTF(env, stats.space_name_ != NULL ? stats.space_name_ : "");
}

JNIEXPORT jlongArray JNI_FN(isolateGetHeapSpaceSizes)(
    JNIEnv* env,
    jobject thiz,
    jlong isolatePtr,
    jint index
) {
    (void)thiz;
    if (index < 0) return NULL;
    v8_HeapSpaceStatistics stats;
    v8_HeapSpaceStatistics_Init(&stats);
    if (!v8__Isolate__GetHeapSpaceStatistics((void*)(uintptr_t)isolatePtr, &stats, (size_t)index)) {
        return NULL;
    }
    jlong values[4];
    values[0] = (jlong)stats.space_size_;
    values[1] = (jlong)stats.space_used_size_;
    values[2] = (jlong)stats.space_available_size_;
    values[3] = (jlong)stats.physical_space_size_;
    jlongArray out = (*env)->NewLongArray(env, 4);
    (*env)->SetLongArrayRegion(env, out, 0, 4, values);
    return out;
}

JNIEXPORT jlongArray JNI_FN(isolateGetHeapCodeStatistics)(JNIEnv* env, jobject thiz, jlong isolatePtr) {
    (void)thiz;
    v8_HeapCodeStatistics stats;
    v8_HeapCodeStatistics_Init(&stats);
    if (!v8__Isolate__GetHeapCodeStatistics((void*)(uintptr_t)isolatePtr, &stats)) {
        return NULL;
    }
    jlong values[4];
    values[0] = (jlong)stats.code_and_metadata_size_;
    values[1] = (jlong)stats.bytecode_and_metadata_size_;
    values[2] = (jlong)stats.external_script_source_size_;
    values[3] = (jlong)stats.cpu_profiler_metadata_size_;
    jlongArray out = (*env)->NewLongArray(env, 4);
    (*env)->SetLongArrayRegion(env, out, 0, 4, values);
    return out;
}
