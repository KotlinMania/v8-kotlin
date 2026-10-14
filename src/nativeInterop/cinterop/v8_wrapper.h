#ifndef V8_WRAPPER_H
#define V8_WRAPPER_H

#include <stddef.h>
#include <stdint.h>

#ifdef __cplusplus
extern "C" {
#endif

/* ========================================================================== */
/* Heap & CFunction structures from binding.hpp / v8-statistics.h             */
/* ========================================================================== */

typedef struct v8_HeapStatistics {
    size_t total_heap_size_;
    size_t total_heap_size_executable_;
    size_t total_physical_size_;
    size_t total_available_size_;
    size_t used_heap_size_;
    size_t heap_size_limit_;
    size_t malloced_memory_;
    size_t external_memory_;
    size_t peak_malloced_memory_;
    uint8_t does_zap_garbage_;
    size_t number_of_native_contexts_;
    size_t number_of_detached_contexts_;
    size_t total_global_handles_size_;
    size_t used_global_handles_size_;
    uint64_t total_allocated_bytes_;
} v8_HeapStatistics;

typedef struct v8_HeapSpaceStatistics {
    const char* space_name_;
    size_t space_size_;
    size_t space_used_size_;
    size_t space_available_size_;
    size_t physical_space_size_;
} v8_HeapSpaceStatistics;

typedef struct v8_HeapCodeStatistics {
    size_t code_and_metadata_size_;
    size_t bytecode_and_metadata_size_;
    size_t external_script_source_size_;
    size_t cpu_profiler_metadata_size_;
} v8_HeapCodeStatistics;

typedef struct v8_CTypeInfo {
    uint8_t type_;
    uint8_t flags_;
} v8_CTypeInfo;

typedef struct v8_FastOneByteString {
    const char* data;
    uint32_t length;
} v8_FastOneByteString;

typedef struct v8_CFunctionInfo {
    v8_CTypeInfo return_info_;
    uint8_t repr_;
    uint32_t arg_count_;
    const v8_CTypeInfo* arg_info_;
} v8_CFunctionInfo;

typedef struct v8_CFunction {
    const void* address_;
    const v8_CFunctionInfo* type_info_;
} v8_CFunction;

typedef struct v8_memory_span_t {
    uint8_t* data;
    size_t size;
} v8_memory_span_t;

typedef struct v8_const_memory_span_t {
    const uint8_t* data;
    size_t size;
} v8_const_memory_span_t;

/* ========================================================================== */
/* Version & layout constants from binding.hpp / binding.cc                   */
/* ========================================================================== */

uint32_t v8_binding_major_version(void);
uint32_t v8_binding_minor_version(void);
uint32_t v8_binding_build_number(void);
uint32_t v8_binding_patch_level(void);
const char* v8_binding_version_string(void);

size_t v8_binding_script_origin_size(void);
size_t v8_binding_cppgc_member_size(void);
size_t v8_binding_cppgc_weak_member_size(void);
size_t v8_binding_traced_reference_size(void);
size_t v8_binding_eternal_size(void);
size_t v8_binding_string_value_view_size(void);
int32_t v8_binding_string_max_length(void);
uint64_t v8_binding_typed_array_max_byte_length(void);
size_t v8_binding_typed_array_max_size_in_heap(void);
uint64_t v8_binding_uint8_array_max_length(void);
uint64_t v8_binding_uint8_clamped_array_max_length(void);
uint64_t v8_binding_int8_array_max_length(void);
uint64_t v8_binding_uint16_array_max_length(void);
uint64_t v8_binding_int16_array_max_length(void);
uint64_t v8_binding_uint32_array_max_length(void);
uint64_t v8_binding_int32_array_max_length(void);
uint64_t v8_binding_float16_array_max_length(void);
uint64_t v8_binding_float32_array_max_length(void);
uint64_t v8_binding_float64_array_max_length(void);
uint64_t v8_binding_big_uint64_array_max_length(void);
uint64_t v8_binding_big_int64_array_max_length(void);

/* ========================================================================== */
/* Support ABI helpers from support.h / binding.cc                            */
/* ========================================================================== */

uint8_t v8_support_bool_to_maybe_bool(int value);
uint8_t v8_support_maybe_bool_nothing(void);
int v8_support_maybe_bool_is_nothing(uint8_t maybe_bool);
int v8_support_maybe_bool_to_bool(uint8_t maybe_bool);

size_t v8_support_two_pointers_size(void);
size_t v8_support_three_pointers_size(void);
size_t v8_support_handle_scope_size(void);
size_t v8_support_escapable_handle_scope_size(void);
size_t v8_support_promise_reject_message_size(void);
size_t v8_support_locker_size(void);
size_t v8_support_try_catch_size(void);
size_t v8_support_allow_javascript_execution_scope_size(void);
size_t v8_support_disallow_javascript_execution_scope_size(void);
size_t v8_support_cached_data_size(void);

/* ========================================================================== */
/* Global V8 engine functions (v8__V8__*)                                     */
/* ========================================================================== */

const char* v8__V8__GetVersion(void);
int v8__V8__IsSandboxEnabled(void);
void v8__V8__SetFlagsFromString(const char* flags, size_t length);
const char* v8__V8__GetFlagsString(void);
void v8__V8__SetFlagsFromCommandLine(int* argc, char** argv, const char* usage);
void v8__V8__InitializePlatform(void* platform);
void v8__V8__Initialize(void);
int v8__V8__Dispose(void);
void v8__V8__DisposePlatform(void);

/* ========================================================================== */
/* Platform functions (v8__Platform__*)                                       */
/* ========================================================================== */

void* v8__Platform__NewDefaultPlatform(int thread_pool_size, int idle_task_support);
void* v8__Platform__NewUnprotectedDefaultPlatform(int thread_pool_size, int idle_task_support);
void* v8__Platform__NewSingleThreadedDefaultPlatform(int idle_task_support);
void v8__Platform__DELETE(void* platform);
int v8__Platform__PumpMessageLoop(void* platform, void* isolate, int wait_for_work);
void v8__Platform__RunIdleTasks(void* platform, void* isolate, double idle_time_in_seconds);
void v8__Platform__NotifyIsolateShutdown(void* platform, void* isolate);
int v8__Platform__GetThreadPoolSize(const void* platform);
int v8__Platform__HasIdleTaskSupport(const void* platform);
int v8__Platform__IsUnprotected(const void* platform);
int v8__Platform__IsSingleThreaded(const void* platform);

/* ========================================================================== */
/* Isolate & HeapStatistics functions (v8__Isolate__*)                        */
/* ========================================================================== */

void v8_HeapStatistics_Init(v8_HeapStatistics* stats);
void v8_HeapSpaceStatistics_Init(v8_HeapSpaceStatistics* stats);
void v8_HeapCodeStatistics_Init(v8_HeapCodeStatistics* stats);

void* v8__Isolate__NewDefault(void);
void v8__Isolate__Dispose(void* isolate);
void v8__Isolate__Enter(void* isolate);
void v8__Isolate__Exit(void* isolate);
void* v8__Isolate__GetCurrent(void);
uint32_t v8__Isolate__GetNumberOfDataSlots(const void* isolate);
void* v8__Isolate__GetData(const void* isolate, uint32_t slot);
void v8__Isolate__SetData(void* isolate, uint32_t slot, void* data);
uint32_t v8__Isolate__GetMicrotasksPolicy(const void* isolate);
void v8__Isolate__SetMicrotasksPolicy(void* isolate, uint32_t policy);
void v8__Isolate__PerformMicrotaskCheckpoint(void* isolate);
void v8__Isolate__MemoryPressureNotification(void* isolate, uint8_t level);
void v8__Isolate__LowMemoryNotification(void* isolate);
void v8__Isolate__ClearKeptObjects(void* isolate);
void v8__Isolate__GetHeapStatistics(void* isolate, v8_HeapStatistics* stats);
int v8__Isolate__GetHeapSpaceStatistics(
    void* isolate,
    v8_HeapSpaceStatistics* space_statistics,
    size_t index
);
int v8__Isolate__GetHeapCodeStatistics(
    void* isolate,
    v8_HeapCodeStatistics* code_statistics
);

#ifdef __cplusplus
}
#endif

#endif /* V8_WRAPPER_H */
