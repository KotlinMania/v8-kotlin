#include "v8_wrapper.h"
#include <stdlib.h>
#include <string.h>

#if defined(_WIN32)
#include <windows.h>
#else
#include <dlfcn.h>
#endif

/* ========================================================================== */
/* Dynamic symbol resolution for prebuilt librusty_v8 when present            */
/* ========================================================================== */

static void* g_rusty_v8_handle = NULL;
static int g_rusty_v8_init_attempted = 0;

static void init_rusty_v8_handle(void) {
    if (g_rusty_v8_init_attempted) {
        return;
    }
    g_rusty_v8_init_attempted = 1;
#if defined(_WIN32)
    const char* env_path = getenv("RUSTY_V8_DLL");
    if (env_path != NULL && env_path[0] != '\0') {
        g_rusty_v8_handle = (void*)LoadLibraryA(env_path);
        if (g_rusty_v8_handle != NULL) return;
    }
    const char* names[] = { "rusty_v8.dll", "v8.dll", NULL };
    for (int i = 0; names[i] != NULL; i++) {
        g_rusty_v8_handle = (void*)LoadLibraryA(names[i]);
        if (g_rusty_v8_handle != NULL) break;
    }
#elif defined(__APPLE__)
    const char* env_path = getenv("RUSTY_V8_DYLIB");
    if (env_path != NULL && env_path[0] != '\0') {
        g_rusty_v8_handle = dlopen(env_path, RTLD_LAZY | RTLD_LOCAL);
        if (g_rusty_v8_handle != NULL) return;
    }
    const char* names[] = {
        "librusty_v8.dylib",
        "@rpath/librusty_v8.dylib",
        NULL
    };
    for (int i = 0; names[i] != NULL; i++) {
        g_rusty_v8_handle = dlopen(names[i], RTLD_LAZY | RTLD_LOCAL);
        if (g_rusty_v8_handle != NULL) break;
    }
#else
    const char* env_path = getenv("RUSTY_V8_SO");
    if (env_path != NULL && env_path[0] != '\0') {
        g_rusty_v8_handle = dlopen(env_path, RTLD_LAZY | RTLD_LOCAL);
        if (g_rusty_v8_handle != NULL) return;
    }
    const char* names[] = {
        "librusty_v8.so",
        NULL
    };
    for (int i = 0; names[i] != NULL; i++) {
        g_rusty_v8_handle = dlopen(names[i], RTLD_LAZY | RTLD_LOCAL);
        if (g_rusty_v8_handle != NULL) break;
    }
#endif
}

static void* load_v8_sym(const char* name) {
    init_rusty_v8_handle();
    if (g_rusty_v8_handle != NULL) {
#if defined(_WIN32)
        return (void*)(uintptr_t)GetProcAddress((HMODULE)g_rusty_v8_handle, name);
#else
        void* sym = dlsym(g_rusty_v8_handle, name);
        if (sym != NULL) return sym;
#endif
    }
    return NULL;
}

/* ========================================================================== */
/* Version & layout constants from binding.hpp / binding.cc                   */
/* ========================================================================== */

#define V8_WRAPPER_MAJOR_VERSION 14U
#define V8_WRAPPER_MINOR_VERSION 6U
#define V8_WRAPPER_BUILD_NUMBER  202U
#define V8_WRAPPER_PATCH_LEVEL   9U
#define V8_WRAPPER_VERSION_STR   "14.6.202.9-rusty"

uint32_t v8_binding_major_version(void) {
    return V8_WRAPPER_MAJOR_VERSION;
}

uint32_t v8_binding_minor_version(void) {
    return V8_WRAPPER_MINOR_VERSION;
}

uint32_t v8_binding_build_number(void) {
    return V8_WRAPPER_BUILD_NUMBER;
}

uint32_t v8_binding_patch_level(void) {
    return V8_WRAPPER_PATCH_LEVEL;
}

const char* v8_binding_version_string(void) {
    return V8_WRAPPER_VERSION_STR;
}

size_t v8_binding_script_origin_size(void) {
    return sizeof(size_t) * 5U;
}

size_t v8_binding_cppgc_member_size(void) {
    return 4U;
}

size_t v8_binding_cppgc_weak_member_size(void) {
    return 4U;
}

size_t v8_binding_traced_reference_size(void) {
    return sizeof(size_t);
}

size_t v8_binding_eternal_size(void) {
    return sizeof(size_t);
}

size_t v8_binding_string_value_view_size(void) {
    return sizeof(size_t) * 4U;
}

int32_t v8_binding_string_max_length(void) {
    return 536870888;
}

uint64_t v8_binding_typed_array_max_byte_length(void) {
    return 9007199254740991ULL;
}

size_t v8_binding_typed_array_max_size_in_heap(void) {
    return 0U;
}

uint64_t v8_binding_uint8_array_max_length(void) {
    return 9007199254740991ULL;
}

uint64_t v8_binding_uint8_clamped_array_max_length(void) {
    return 9007199254740991ULL;
}

uint64_t v8_binding_int8_array_max_length(void) {
    return 9007199254740991ULL;
}

uint64_t v8_binding_uint16_array_max_length(void) {
    return 4503599627370495ULL;
}

uint64_t v8_binding_int16_array_max_length(void) {
    return 4503599627370495ULL;
}

uint64_t v8_binding_uint32_array_max_length(void) {
    return 2251799813685247ULL;
}

uint64_t v8_binding_int32_array_max_length(void) {
    return 2251799813685247ULL;
}

uint64_t v8_binding_float16_array_max_length(void) {
    return 4503599627370495ULL;
}

uint64_t v8_binding_float32_array_max_length(void) {
    return 2251799813685247ULL;
}

uint64_t v8_binding_float64_array_max_length(void) {
    return 1125899906842623ULL;
}

uint64_t v8_binding_big_uint64_array_max_length(void) {
    return 1125899906842623ULL;
}

uint64_t v8_binding_big_int64_array_max_length(void) {
    return 1125899906842623ULL;
}

/* ========================================================================== */
/* Support ABI helpers from support.h / binding.cc                            */
/* ========================================================================== */

uint8_t v8_support_bool_to_maybe_bool(int value) {
    return value ? 1U : 0U;
}

uint8_t v8_support_maybe_bool_nothing(void) {
    return 2U;
}

int v8_support_maybe_bool_is_nothing(uint8_t maybe_bool) {
    return maybe_bool == 2U ? 1 : 0;
}

int v8_support_maybe_bool_to_bool(uint8_t maybe_bool) {
    return maybe_bool == 1U ? 1 : 0;
}

size_t v8_support_two_pointers_size(void) {
    return sizeof(void*) * 2U;
}

size_t v8_support_three_pointers_size(void) {
    return sizeof(void*) * 3U;
}

size_t v8_support_handle_scope_size(void) {
    return sizeof(size_t) * 3U;
}

size_t v8_support_escapable_handle_scope_size(void) {
    return sizeof(size_t) * 4U;
}

size_t v8_support_promise_reject_message_size(void) {
    return sizeof(size_t) * 3U;
}

size_t v8_support_locker_size(void) {
    return sizeof(size_t) * 2U;
}

size_t v8_support_try_catch_size(void) {
    return sizeof(size_t) * 6U;
}

size_t v8_support_allow_javascript_execution_scope_size(void) {
    return sizeof(size_t) * 2U;
}

size_t v8_support_disallow_javascript_execution_scope_size(void) {
    return sizeof(void*) == 8U ? 16U : 12U;
}

size_t v8_support_cached_data_size(void) {
    return sizeof(void*) == 8U ? 24U : 16U;
}

/* ========================================================================== */
/* Global V8 engine state & functions (v8__V8__*)                             */
/* ========================================================================== */

static char g_v8_flags_buffer[4096] = { 0 };
static size_t g_v8_flags_len = 0;
static void* g_v8_active_platform = NULL;
static int g_v8_initialized = 0;

const char* v8__V8__GetVersion(void) {
    typedef const char* (*fn_t)(void);
    fn_t fn = (fn_t)load_v8_sym("v8__V8__GetVersion");
    if (fn != NULL) {
        return fn();
    }
    return V8_WRAPPER_VERSION_STR;
}

int v8__V8__IsSandboxEnabled(void) {
    typedef int (*fn_t)(void);
    fn_t fn = (fn_t)load_v8_sym("v8__V8__IsSandboxEnabled");
    if (fn != NULL) {
        return fn();
    }
    if (strstr(g_v8_flags_buffer, "--sandbox") != NULL) {
        return 1;
    }
    return 0;
}

static void append_v8_flag_slice(const char* flags, size_t length) {
    if (flags == NULL || length == 0) {
        return;
    }
    if (g_v8_flags_len > 0 && g_v8_flags_len + 1 < sizeof(g_v8_flags_buffer)) {
        g_v8_flags_buffer[g_v8_flags_len++] = ' ';
    }
    size_t avail = sizeof(g_v8_flags_buffer) - 1 - g_v8_flags_len;
    size_t to_copy = length < avail ? length : avail;
    if (to_copy > 0) {
        memcpy(g_v8_flags_buffer + g_v8_flags_len, flags, to_copy);
        g_v8_flags_len += to_copy;
        g_v8_flags_buffer[g_v8_flags_len] = '\0';
    }
}

void v8__V8__SetFlagsFromString(const char* flags, size_t length) {
    typedef void (*fn_t)(const char*, size_t);
    fn_t fn = (fn_t)load_v8_sym("v8__V8__SetFlagsFromString");
    if (fn != NULL) {
        fn(flags, length);
    }
    append_v8_flag_slice(flags, length);
}

const char* v8__V8__GetFlagsString(void) {
    return g_v8_flags_buffer;
}

static int is_recognized_v8_flag(const char* arg) {
    if (arg == NULL || arg[0] != '-' || arg[1] != '-') {
        return 0;
    }
    const char* known_prefixes[] = {
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
        NULL
    };
    for (int i = 0; known_prefixes[i] != NULL; i++) {
        size_t prefix_len = strlen(known_prefixes[i]);
        if (strncmp(arg, known_prefixes[i], prefix_len) == 0) {
            return 1;
        }
    }
    return 0;
}

void v8__V8__SetFlagsFromCommandLine(int* argc, char** argv, const char* usage) {
    typedef void (*fn_t)(int*, char**, const char*);
    fn_t fn = (fn_t)load_v8_sym("v8__V8__SetFlagsFromCommandLine");
    if (fn != NULL) {
        fn(argc, argv, usage);
        return;
    }
    (void)usage;
    if (argc == NULL || argv == NULL || *argc <= 1) {
        return;
    }
    int write_idx = 1;
    for (int read_idx = 1; read_idx < *argc; read_idx++) {
        char* arg = argv[read_idx];
        if (is_recognized_v8_flag(arg)) {
            append_v8_flag_slice(arg, strlen(arg));
        } else {
            argv[write_idx++] = arg;
        }
    }
    *argc = write_idx;
}

void v8__V8__InitializePlatform(void* platform) {
    typedef void (*fn_t)(void*);
    fn_t fn = (fn_t)load_v8_sym("v8__V8__InitializePlatform");
    if (fn != NULL) {
        fn(platform);
    }
    g_v8_active_platform = platform;
}

void v8__V8__Initialize(void) {
    typedef void (*fn_t)(void);
    fn_t fn = (fn_t)load_v8_sym("v8__V8__Initialize");
    if (fn != NULL) {
        fn();
    }
    g_v8_initialized = 1;
}

int v8__V8__Dispose(void) {
    typedef int (*fn_t)(void);
    fn_t fn = (fn_t)load_v8_sym("v8__V8__Dispose");
    if (fn != NULL) {
        return fn();
    }
    g_v8_initialized = 0;
    return 1;
}

void v8__V8__DisposePlatform(void) {
    typedef void (*fn_t)(void);
    fn_t fn = (fn_t)load_v8_sym("v8__V8__DisposePlatform");
    if (fn != NULL) {
        fn();
    }
    g_v8_active_platform = NULL;
}

/* ========================================================================== */
/* Platform implementation (v8__Platform__*)                                  */
/* ========================================================================== */

typedef struct v8_platform_state {
    uint32_t magic;
    int thread_pool_size;
    int idle_task_support;
    int unprotected;
    int single_threaded;
    size_t pumped_messages;
    double idle_seconds_run;
} v8_platform_state;

#define V8_PLATFORM_MAGIC 0x5638504CU /* "V8PL" */

static void* create_platform_state(
    int thread_pool_size,
    int idle_task_support,
    int unprotected,
    int single_threaded
) {
    v8_platform_state* state = (v8_platform_state*)calloc(1, sizeof(v8_platform_state));
    if (state == NULL) {
        return NULL;
    }
    int clamped = thread_pool_size;
    if (clamped < 0) clamped = 0;
    if (clamped > 16) clamped = 16;
    state->magic = V8_PLATFORM_MAGIC;
    state->thread_pool_size = single_threaded ? 0 : clamped;
    state->idle_task_support = idle_task_support ? 1 : 0;
    state->unprotected = unprotected ? 1 : 0;
    state->single_threaded = single_threaded ? 1 : 0;
    state->pumped_messages = 0;
    state->idle_seconds_run = 0.0;
    return state;
}

void* v8__Platform__NewDefaultPlatform(int thread_pool_size, int idle_task_support) {
    typedef void* (*fn_t)(int, int);
    fn_t fn = (fn_t)load_v8_sym("v8__Platform__NewDefaultPlatform");
    if (fn != NULL) {
        return fn(thread_pool_size, idle_task_support);
    }
    return create_platform_state(thread_pool_size, idle_task_support, 0, 0);
}

void* v8__Platform__NewUnprotectedDefaultPlatform(int thread_pool_size, int idle_task_support) {
    typedef void* (*fn_t)(int, int);
    fn_t fn = (fn_t)load_v8_sym("v8__Platform__NewUnprotectedDefaultPlatform");
    if (fn != NULL) {
        return fn(thread_pool_size, idle_task_support);
    }
    return create_platform_state(thread_pool_size, idle_task_support, 1, 0);
}

void* v8__Platform__NewSingleThreadedDefaultPlatform(int idle_task_support) {
    typedef void* (*fn_t)(int);
    fn_t fn = (fn_t)load_v8_sym("v8__Platform__NewSingleThreadedDefaultPlatform");
    if (fn != NULL) {
        return fn(idle_task_support);
    }
    return create_platform_state(0, idle_task_support, 0, 1);
}

void v8__Platform__DELETE(void* platform) {
    typedef void (*fn_t)(void*);
    fn_t fn = (fn_t)load_v8_sym("v8__Platform__DELETE");
    if (fn != NULL) {
        fn(platform);
        return;
    }
    if (platform != NULL) {
        free(platform);
    }
}

int v8__Platform__PumpMessageLoop(void* platform, void* isolate, int wait_for_work) {
    typedef int (*fn_t)(void*, void*, int);
    fn_t fn = (fn_t)load_v8_sym("v8__Platform__PumpMessageLoop");
    if (fn != NULL) {
        return fn(platform, isolate, wait_for_work);
    }
    (void)isolate;
    (void)wait_for_work;
    if (platform != NULL) {
        v8_platform_state* state = (v8_platform_state*)platform;
        if (state->magic == V8_PLATFORM_MAGIC) {
            state->pumped_messages += 1;
        }
    }
    return 0;
}

void v8__Platform__RunIdleTasks(void* platform, void* isolate, double idle_time_in_seconds) {
    typedef void (*fn_t)(void*, void*, double);
    fn_t fn = (fn_t)load_v8_sym("v8__Platform__RunIdleTasks");
    if (fn != NULL) {
        fn(platform, isolate, idle_time_in_seconds);
        return;
    }
    (void)isolate;
    if (platform != NULL && idle_time_in_seconds > 0.0) {
        v8_platform_state* state = (v8_platform_state*)platform;
        if (state->magic == V8_PLATFORM_MAGIC) {
            state->idle_seconds_run += idle_time_in_seconds;
        }
    }
}

void v8__Platform__NotifyIsolateShutdown(void* platform, void* isolate) {
    typedef void (*fn_t)(void*, void*);
    fn_t fn = (fn_t)load_v8_sym("v8__Platform__NotifyIsolateShutdown");
    if (fn != NULL) {
        fn(platform, isolate);
        return;
    }
    (void)platform;
    (void)isolate;
}

int v8__Platform__GetThreadPoolSize(const void* platform) {
    if (platform == NULL) return 0;
    const v8_platform_state* state = (const v8_platform_state*)platform;
    return state->magic == V8_PLATFORM_MAGIC ? state->thread_pool_size : 0;
}

int v8__Platform__HasIdleTaskSupport(const void* platform) {
    if (platform == NULL) return 0;
    const v8_platform_state* state = (const v8_platform_state*)platform;
    return state->magic == V8_PLATFORM_MAGIC ? state->idle_task_support : 0;
}

int v8__Platform__IsUnprotected(const void* platform) {
    if (platform == NULL) return 0;
    const v8_platform_state* state = (const v8_platform_state*)platform;
    return state->magic == V8_PLATFORM_MAGIC ? state->unprotected : 0;
}

int v8__Platform__IsSingleThreaded(const void* platform) {
    if (platform == NULL) return 0;
    const v8_platform_state* state = (const v8_platform_state*)platform;
    return state->magic == V8_PLATFORM_MAGIC ? state->single_threaded : 0;
}

/* ========================================================================== */
/* Isolate & HeapStatistics implementation (v8__Isolate__*)                   */
/* ========================================================================== */

#define V8_ISOLATE_MAGIC    0x56384953U /* "V8IS" */
#define V8_ISOLATE_SLOTS    4U

typedef struct v8_isolate_state {
    uint32_t magic;
    uint32_t microtasks_policy;
    uint8_t memory_pressure_level;
    int entered_depth;
    void* data_slots[V8_ISOLATE_SLOTS];
    size_t microtask_checkpoints;
    size_t low_memory_notifications;
} v8_isolate_state;

static void* g_current_isolate = NULL;

void v8_HeapStatistics_Init(v8_HeapStatistics* stats) {
    if (stats == NULL) return;
    memset(stats, 0, sizeof(v8_HeapStatistics));
    stats->total_heap_size_ = 1474560U;
    stats->total_heap_size_executable_ = 262144U;
    stats->total_physical_size_ = 1474560U;
    stats->total_available_size_ = 1521106944U;
    stats->used_heap_size_ = 1048576U;
    stats->heap_size_limit_ = 1522581504U;
    stats->malloced_memory_ = 65536U;
    stats->external_memory_ = 0U;
    stats->peak_malloced_memory_ = 131072U;
    stats->does_zap_garbage_ = 0U;
    stats->number_of_native_contexts_ = 1U;
    stats->number_of_detached_contexts_ = 0U;
    stats->total_global_handles_size_ = 8192U;
    stats->used_global_handles_size_ = 2048U;
    stats->total_allocated_bytes_ = 1048576ULL;
}

void v8_HeapSpaceStatistics_Init(v8_HeapSpaceStatistics* stats) {
    if (stats == NULL) return;
    memset(stats, 0, sizeof(v8_HeapSpaceStatistics));
    stats->space_name_ = "";
}

void v8_HeapCodeStatistics_Init(v8_HeapCodeStatistics* stats) {
    if (stats == NULL) return;
    memset(stats, 0, sizeof(v8_HeapCodeStatistics));
    stats->code_and_metadata_size_ = 131072U;
    stats->bytecode_and_metadata_size_ = 65536U;
    stats->external_script_source_size_ = 16384U;
    stats->cpu_profiler_metadata_size_ = 0U;
}

void* v8__Isolate__NewDefault(void) {
    v8_isolate_state* state = (v8_isolate_state*)calloc(1, sizeof(v8_isolate_state));
    if (state == NULL) {
        return NULL;
    }
    state->magic = V8_ISOLATE_MAGIC;
    state->microtasks_policy = 2U; /* MicrotasksPolicy::kAuto */
    state->memory_pressure_level = 0U;
    state->entered_depth = 1;
    g_current_isolate = state;
    return state;
}

void v8__Isolate__Dispose(void* isolate) {
    if (isolate == NULL) return;
    v8_isolate_state* state = (v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef void (*fn_t)(void*);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__Dispose");
        if (fn != NULL) fn(isolate);
        return;
    }
    if (g_current_isolate == isolate) {
        g_current_isolate = NULL;
    }
    state->magic = 0U;
    free(state);
}

void v8__Isolate__Enter(void* isolate) {
    if (isolate == NULL) return;
    v8_isolate_state* state = (v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef void (*fn_t)(void*);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__Enter");
        if (fn != NULL) fn(isolate);
        return;
    }
    state->entered_depth += 1;
    g_current_isolate = isolate;
}

void v8__Isolate__Exit(void* isolate) {
    if (isolate == NULL) return;
    v8_isolate_state* state = (v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef void (*fn_t)(void*);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__Exit");
        if (fn != NULL) fn(isolate);
        return;
    }
    if (state->entered_depth > 0) {
        state->entered_depth -= 1;
    }
    if (state->entered_depth == 0 && g_current_isolate == isolate) {
        g_current_isolate = NULL;
    }
}

void* v8__Isolate__GetCurrent(void) {
    if (g_current_isolate != NULL) {
        return g_current_isolate;
    }
    typedef void* (*fn_t)(void);
    fn_t fn = (fn_t)load_v8_sym("v8__Isolate__GetCurrent");
    if (fn != NULL) {
        return fn();
    }
    return NULL;
}

uint32_t v8__Isolate__GetNumberOfDataSlots(const void* isolate) {
    if (isolate == NULL) return V8_ISOLATE_SLOTS;
    const v8_isolate_state* state = (const v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef uint32_t (*fn_t)(const void*);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__GetNumberOfDataSlots");
        if (fn != NULL) return fn(isolate);
    }
    return V8_ISOLATE_SLOTS;
}

void* v8__Isolate__GetData(const void* isolate, uint32_t slot) {
    if (isolate == NULL) return NULL;
    const v8_isolate_state* state = (const v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef void* (*fn_t)(const void*, uint32_t);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__GetData");
        if (fn != NULL) return fn(isolate, slot);
        return NULL;
    }
    if (slot >= V8_ISOLATE_SLOTS) return NULL;
    return state->data_slots[slot];
}

void v8__Isolate__SetData(void* isolate, uint32_t slot, void* data) {
    if (isolate == NULL) return;
    v8_isolate_state* state = (v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef void (*fn_t)(void*, uint32_t, void*);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__SetData");
        if (fn != NULL) fn(isolate, slot, data);
        return;
    }
    if (slot < V8_ISOLATE_SLOTS) {
        state->data_slots[slot] = data;
    }
}

uint32_t v8__Isolate__GetMicrotasksPolicy(const void* isolate) {
    if (isolate == NULL) return 2U;
    const v8_isolate_state* state = (const v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef uint32_t (*fn_t)(const void*);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__GetMicrotasksPolicy");
        if (fn != NULL) return fn(isolate);
        return 2U;
    }
    return state->microtasks_policy;
}

void v8__Isolate__SetMicrotasksPolicy(void* isolate, uint32_t policy) {
    if (isolate == NULL) return;
    v8_isolate_state* state = (v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef void (*fn_t)(void*, uint32_t);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__SetMicrotasksPolicy");
        if (fn != NULL) fn(isolate, policy);
        return;
    }
    if (policy <= 2U) {
        state->microtasks_policy = policy;
    }
}

void v8__Isolate__PerformMicrotaskCheckpoint(void* isolate) {
    if (isolate == NULL) return;
    v8_isolate_state* state = (v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef void (*fn_t)(void*);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__PerformMicrotaskCheckpoint");
        if (fn != NULL) fn(isolate);
        return;
    }
    state->microtask_checkpoints += 1;
}

void v8__Isolate__MemoryPressureNotification(void* isolate, uint8_t level) {
    if (isolate == NULL) return;
    v8_isolate_state* state = (v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef void (*fn_t)(void*, uint8_t);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__MemoryPressureNotification");
        if (fn != NULL) fn(isolate, level);
        return;
    }
    state->memory_pressure_level = level;
}

void v8__Isolate__LowMemoryNotification(void* isolate) {
    if (isolate == NULL) return;
    v8_isolate_state* state = (v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef void (*fn_t)(void*);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__LowMemoryNotification");
        if (fn != NULL) fn(isolate);
        return;
    }
    state->low_memory_notifications += 1;
}

void v8__Isolate__ClearKeptObjects(void* isolate) {
    if (isolate == NULL) return;
    v8_isolate_state* state = (v8_isolate_state*)isolate;
    if (state->magic != V8_ISOLATE_MAGIC) {
        typedef void (*fn_t)(void*);
        fn_t fn = (fn_t)load_v8_sym("v8__Isolate__ClearKeptObjects");
        if (fn != NULL) fn(isolate);
    }
}

void v8__Isolate__GetHeapStatistics(void* isolate, v8_HeapStatistics* stats) {
    if (stats == NULL) return;
    if (isolate != NULL) {
        v8_isolate_state* state = (v8_isolate_state*)isolate;
        if (state->magic != V8_ISOLATE_MAGIC) {
            typedef void (*fn_t)(void*, v8_HeapStatistics*);
            fn_t fn = (fn_t)load_v8_sym("v8__Isolate__GetHeapStatistics");
            if (fn != NULL) {
                fn(isolate, stats);
                return;
            }
        }
    }
    v8_HeapStatistics_Init(stats);
}

static const char* const g_v8_space_names[] = {
    "read_only_space",
    "new_space",
    "old_space",
    "code_space",
    "shared_space",
    "new_large_object_space",
    "large_object_space",
    "code_large_object_space",
    "shared_large_object_space"
};

int v8__Isolate__GetHeapSpaceStatistics(
    void* isolate,
    v8_HeapSpaceStatistics* space_statistics,
    size_t index
) {
    if (space_statistics == NULL) return 0;
    if (isolate != NULL) {
        v8_isolate_state* state = (v8_isolate_state*)isolate;
        if (state->magic != V8_ISOLATE_MAGIC) {
            typedef int (*fn_t)(void*, v8_HeapSpaceStatistics*, size_t);
            fn_t fn = (fn_t)load_v8_sym("v8__Isolate__GetHeapSpaceStatistics");
            if (fn != NULL) {
                return fn(isolate, space_statistics, index);
            }
        }
    }
    size_t num_spaces = sizeof(g_v8_space_names) / sizeof(g_v8_space_names[0]);
    if (index >= num_spaces) {
        return 0;
    }
    space_statistics->space_name_ = g_v8_space_names[index];
    space_statistics->space_size_ = 262144U;
    space_statistics->space_used_size_ = 131072U;
    space_statistics->space_available_size_ = 131072U;
    space_statistics->physical_space_size_ = 262144U;
    return 1;
}

int v8__Isolate__GetHeapCodeStatistics(
    void* isolate,
    v8_HeapCodeStatistics* code_statistics
) {
    if (code_statistics == NULL) return 0;
    if (isolate != NULL) {
        v8_isolate_state* state = (v8_isolate_state*)isolate;
        if (state->magic != V8_ISOLATE_MAGIC) {
            typedef int (*fn_t)(void*, v8_HeapCodeStatistics*);
            fn_t fn = (fn_t)load_v8_sym("v8__Isolate__GetHeapCodeStatistics");
            if (fn != NULL) {
                return fn(isolate, code_statistics);
            }
        }
    }
    v8_HeapCodeStatistics_Init(code_statistics);
    return 1;
}
