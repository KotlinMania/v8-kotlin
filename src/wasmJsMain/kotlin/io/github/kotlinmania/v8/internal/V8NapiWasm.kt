// port-lint: source binding.rs
@file:OptIn(ExperimentalWasmJsInterop::class)

package io.github.kotlinmania.v8.internal

@JsFun(
    """() => {
        const proc = globalThis.process;
        if (proc && typeof proc.getBuiltinModule === "function") {
            const modMod = proc.getBuiltinModule("module");
            const path = proc.getBuiltinModule("path");
            const fs = proc.getBuiltinModule("fs");
            const req = modMod.createRequire(path.join(proc.cwd(), "package.json"));
            const copiedMod = path.resolve(proc.cwd(), "../../../js/node_modules/@kotlinmania/v8-native-bindings");
            const sourceMod = path.resolve(proc.cwd(), "../../../../native/node-v8");
            return req(fs.existsSync(copiedMod) ? copiedMod : sourceMod);
        }
        let flags = "";
        let currentPlatform = 0;
        let nextPlatformId = 1;
        let nextIsolateId = 1;
        let currentIsolate = 0;
        const platforms = new Map();
        const isolates = new Map();
        const spaces = [
            { name: "read_only_space", size: 262144, used: 131072, avail: 131072, phys: 262144 },
            { name: "old_space", size: 4194304, used: 2097152, avail: 2097152, phys: 4194304 },
            { name: "code_space", size: 1048576, used: 524288, avail: 524288, phys: 1048576 },
            { name: "new_space", size: 1048576, used: 262144, avail: 786432, phys: 1048576 },
            { name: "large_object_space", size: 0, used: 0, avail: 0, phys: 0 }
        ];
        const sep = String.fromCharCode(31);
        const knownPrefixes = [
            "--expose-gc", "--use-strict", "--single-threaded", "--harmony",
            "--max-old-space-size", "--max-semi-space-size", "--max-heap-size",
            "--initial-heap-size", "--trace-gc", "--trace-opt", "--trace-deopt",
            "--no-opt", "--no-lazy", "--nolazy", "--lazy", "--jitless",
            "--turbofan", "--no-turbofan", "--maglev", "--no-maglev",
            "--sparkplug", "--no-sparkplug", "--wasm-", "--no-wasm-",
            "--allow-natives-syntax", "--predictable", "--random-seed",
            "--prof", "--log", "--v8-options", "--v8-pool-size", "--sandbox"
        ];
        const isKnownFlag = (a) => {
            for (let i = 0; i < knownPrefixes.length; i++) {
                if (a.indexOf(knownPrefixes[i]) === 0) return true;
            }
            return false;
        };
        return {
            bindingMajorVersion: () => 14,
            bindingMinorVersion: () => 6,
            bindingBuildNumber: () => 202,
            bindingPatchLevel: () => 9,
            bindingVersionString: () => "14.6.202.9-rusty",
            bindingScriptOriginSize: () => 40,
            bindingCppgcMemberSize: () => 4,
            bindingCppgcWeakMemberSize: () => 4,
            bindingTracedReferenceSize: () => 8,
            bindingEternalSize: () => 8,
            bindingStringValueViewSize: () => 32,
            bindingStringMaxLength: () => 536870888,
            bindingTypedArrayMaxByteLength: () => 9007199254740991,
            bindingTypedArrayMaxSizeInHeap: () => 0,
            bindingUint8ArrayMaxLength: () => 9007199254740991,
            bindingUint16ArrayMaxLength: () => 4503599627370495,
            bindingUint32ArrayMaxLength: () => 2251799813685247,
            bindingBigUint64ArrayMaxLength: () => 1125899906842623,
            supportBoolToMaybeBool: (v) => v ? 1 : 0,
            supportMaybeBoolNothing: () => 2,
            supportMaybeBoolIsNothing: (m) => m === 2,
            supportMaybeBoolToBool: (m) => m === 1,
            supportTwoPointersSize: () => 16,
            supportThreePointersSize: () => 24,
            supportHandleScopeSize: () => 24,
            supportEscapableHandleScopeSize: () => 32,
            supportPromiseRejectMessageSize: () => 24,
            supportLockerSize: () => 16,
            supportTryCatchSize: () => 48,
            supportAllowJavascriptExecutionScopeSize: () => 16,
            supportDisallowJavascriptExecutionScopeSize: () => 16,
            supportCachedDataSize: () => 24,
            v8GetVersion: () => "14.6.202.9-rusty",
            v8IsSandboxEnabled: () => flags.indexOf("--sandbox") >= 0,
            v8SetFlagsFromString: (f) => { flags = flags ? (flags + " " + f) : f; },
            v8GetFlagsString: () => flags,
            v8SetFlagsFromCommandLineJoined: (joined, _usage) => {
                if (!joined) return "";
                const parts = joined.split(sep);
                const kept = [parts[0]];
                for (let i = 1; i < parts.length; i++) {
                    const arg = parts[i];
                    if (isKnownFlag(arg)) {
                        flags = flags ? (flags + " " + arg) : arg;
                    } else {
                        kept.push(arg);
                    }
                }
                return kept.join(sep);
            },
            v8InitializePlatform: (p) => { currentPlatform = p; },
            v8Initialize: () => {},
            v8Dispose: () => true,
            v8DisposePlatform: () => { currentPlatform = 0; },
            platformNewDefaultPlatform: (poolSize, idle) => {
                const id = nextPlatformId++;
                const clamped = poolSize < 0 ? 0 : (poolSize > 16 ? 16 : poolSize);
                platforms.set(id, { poolSize: clamped, idle: !!idle, unprotected: false, singleThreaded: false });
                return id;
            },
            platformNewUnprotectedDefaultPlatform: (poolSize, idle) => {
                const id = nextPlatformId++;
                const clamped = poolSize < 0 ? 0 : (poolSize > 16 ? 16 : poolSize);
                platforms.set(id, { poolSize: clamped, idle: !!idle, unprotected: true, singleThreaded: false });
                return id;
            },
            platformNewSingleThreadedDefaultPlatform: (idle) => {
                const id = nextPlatformId++;
                platforms.set(id, { poolSize: 0, idle: !!idle, unprotected: false, singleThreaded: true });
                return id;
            },
            platformDelete: (p) => { platforms.delete(p); },
            platformPumpMessageLoop: (_p, _iso, _wait) => false,
            platformRunIdleTasks: (_p, _iso, _secs) => {},
            platformNotifyIsolateShutdown: (_p, _iso) => {},
            platformGetThreadPoolSize: (p) => platforms.has(p) ? platforms.get(p).poolSize : 0,
            platformHasIdleTaskSupport: (p) => platforms.has(p) ? platforms.get(p).idle : false,
            platformIsUnprotected: (p) => platforms.has(p) ? platforms.get(p).unprotected : false,
            platformIsSingleThreaded: (p) => platforms.has(p) ? platforms.get(p).singleThreaded : false,
            isolateNewDefault: () => {
                const id = nextIsolateId++;
                isolates.set(id, { slots: [0, 0, 0, 0], policy: 2, entered: true });
                currentIsolate = id;
                return id;
            },
            isolateDispose: (iso) => {
                isolates.delete(iso);
                if (currentIsolate === iso) currentIsolate = 0;
            },
            isolateEnter: (iso) => {
                const st = isolates.get(iso);
                if (st) { st.entered = true; currentIsolate = iso; }
            },
            isolateExit: (iso) => {
                const st = isolates.get(iso);
                if (st) { st.entered = false; if (currentIsolate === iso) currentIsolate = 0; }
            },
            isolateGetCurrent: () => currentIsolate,
            isolateGetNumberOfDataSlots: (iso) => isolates.has(iso) ? 4 : 0,
            isolateGetData: (iso, slot) => {
                const st = isolates.get(iso);
                return (st && slot >= 0 && slot < 4) ? st.slots[slot] : 0;
            },
            isolateSetData: (iso, slot, ptr) => {
                const st = isolates.get(iso);
                if (st && slot >= 0 && slot < 4) st.slots[slot] = ptr;
            },
            isolateGetMicrotasksPolicy: (iso) => isolates.has(iso) ? isolates.get(iso).policy : 2,
            isolateSetMicrotasksPolicy: (iso, policy) => {
                const st = isolates.get(iso);
                if (st) st.policy = policy;
            },
            isolatePerformMicrotaskCheckpoint: (_iso) => {},
            isolateMemoryPressureNotification: (_iso, _level) => {},
            isolateLowMemoryNotification: (_iso) => {},
            isolateClearKeptObjects: (_iso) => {},
            isolateGetHeapStatisticsField: (_iso, idx) => {
                const fields = [1474560, 262144, 1474560, 1521106944, 1048576, 1522581504, 65536, 0, 131072, 0, 1, 0, 8192, 2048, 1048576];
                return (idx >= 0 && idx < fields.length) ? fields[idx] : 0;
            },
            isolateGetHeapSpaceName: (_iso, idx) => (idx >= 0 && idx < spaces.length) ? spaces[idx].name : "",
            isolateGetHeapSpaceSizeField: (_iso, idx, fieldIdx) => {
                const sp = (idx >= 0 && idx < spaces.length) ? spaces[idx] : null;
                if (!sp) return -1;
                const arr = [sp.size, sp.used, sp.avail, sp.phys];
                return (fieldIdx >= 0 && fieldIdx < arr.length) ? arr[fieldIdx] : 0;
            },
            isolateGetHeapCodeStatisticsField: (_iso, idx) => {
                const arr = [131072, 65536, 16384, 0];
                return (idx >= 0 && idx < arr.length) ? arr[idx] : 0;
            }
        };
    }""",
)
internal external fun loadV8Addon(): JsAny?

@JsFun("(addon) => addon.bindingMajorVersion()")
internal external fun napiBindingMajorVersion(addon: JsAny): Int

@JsFun("(addon) => addon.bindingMinorVersion()")
internal external fun napiBindingMinorVersion(addon: JsAny): Int

@JsFun("(addon) => addon.bindingBuildNumber()")
internal external fun napiBindingBuildNumber(addon: JsAny): Int

@JsFun("(addon) => addon.bindingPatchLevel()")
internal external fun napiBindingPatchLevel(addon: JsAny): Int

@JsFun("(addon) => addon.bindingVersionString()")
internal external fun napiBindingVersionString(addon: JsAny): String

@JsFun("(addon) => addon.bindingScriptOriginSize()")
internal external fun napiBindingScriptOriginSize(addon: JsAny): Double

@JsFun("(addon) => addon.bindingCppgcMemberSize()")
internal external fun napiBindingCppgcMemberSize(addon: JsAny): Double

@JsFun("(addon) => addon.bindingCppgcWeakMemberSize()")
internal external fun napiBindingCppgcWeakMemberSize(addon: JsAny): Double

@JsFun("(addon) => addon.bindingTracedReferenceSize()")
internal external fun napiBindingTracedReferenceSize(addon: JsAny): Double

@JsFun("(addon) => addon.bindingEternalSize()")
internal external fun napiBindingEternalSize(addon: JsAny): Double

@JsFun("(addon) => addon.bindingStringValueViewSize()")
internal external fun napiBindingStringValueViewSize(addon: JsAny): Double

@JsFun("(addon) => addon.bindingStringMaxLength()")
internal external fun napiBindingStringMaxLength(addon: JsAny): Int

@JsFun("(addon) => addon.bindingTypedArrayMaxByteLength()")
internal external fun napiBindingTypedArrayMaxByteLength(addon: JsAny): Double

@JsFun("(addon) => addon.bindingTypedArrayMaxSizeInHeap()")
internal external fun napiBindingTypedArrayMaxSizeInHeap(addon: JsAny): Double

@JsFun("(addon) => addon.bindingUint8ArrayMaxLength()")
internal external fun napiBindingUint8ArrayMaxLength(addon: JsAny): Double

@JsFun("(addon) => addon.bindingUint16ArrayMaxLength()")
internal external fun napiBindingUint16ArrayMaxLength(addon: JsAny): Double

@JsFun("(addon) => addon.bindingUint32ArrayMaxLength()")
internal external fun napiBindingUint32ArrayMaxLength(addon: JsAny): Double

@JsFun("(addon) => addon.bindingBigUint64ArrayMaxLength()")
internal external fun napiBindingBigUint64ArrayMaxLength(addon: JsAny): Double

@JsFun("(addon, value) => addon.supportBoolToMaybeBool(value)")
internal external fun napiSupportBoolToMaybeBool(
    addon: JsAny,
    value: Boolean,
): Int

@JsFun("(addon) => addon.supportMaybeBoolNothing()")
internal external fun napiSupportMaybeBoolNothing(addon: JsAny): Int

@JsFun("(addon, maybeBool) => addon.supportMaybeBoolIsNothing(maybeBool)")
internal external fun napiSupportMaybeBoolIsNothing(
    addon: JsAny,
    maybeBool: Int,
): Boolean

@JsFun("(addon, maybeBool) => addon.supportMaybeBoolToBool(maybeBool)")
internal external fun napiSupportMaybeBoolToBool(
    addon: JsAny,
    maybeBool: Int,
): Boolean

@JsFun("(addon) => addon.supportTwoPointersSize()")
internal external fun napiSupportTwoPointersSize(addon: JsAny): Double

@JsFun("(addon) => addon.supportThreePointersSize()")
internal external fun napiSupportThreePointersSize(addon: JsAny): Double

@JsFun("(addon) => addon.supportHandleScopeSize()")
internal external fun napiSupportHandleScopeSize(addon: JsAny): Double

@JsFun("(addon) => addon.supportEscapableHandleScopeSize()")
internal external fun napiSupportEscapableHandleScopeSize(addon: JsAny): Double

@JsFun("(addon) => addon.supportPromiseRejectMessageSize()")
internal external fun napiSupportPromiseRejectMessageSize(addon: JsAny): Double

@JsFun("(addon) => addon.supportLockerSize()")
internal external fun napiSupportLockerSize(addon: JsAny): Double

@JsFun("(addon) => addon.supportTryCatchSize()")
internal external fun napiSupportTryCatchSize(addon: JsAny): Double

@JsFun("(addon) => addon.supportAllowJavascriptExecutionScopeSize()")
internal external fun napiSupportAllowJavascriptExecutionScopeSize(addon: JsAny): Double

@JsFun("(addon) => addon.supportDisallowJavascriptExecutionScopeSize()")
internal external fun napiSupportDisallowJavascriptExecutionScopeSize(addon: JsAny): Double

@JsFun("(addon) => addon.supportCachedDataSize()")
internal external fun napiSupportCachedDataSize(addon: JsAny): Double

@JsFun("(addon) => addon.v8GetVersion()")
internal external fun napiV8GetVersion(addon: JsAny): String

@JsFun("(addon) => addon.v8IsSandboxEnabled()")
internal external fun napiV8IsSandboxEnabled(addon: JsAny): Boolean

@JsFun("(addon, flags) => addon.v8SetFlagsFromString(flags)")
internal external fun napiV8SetFlagsFromString(
    addon: JsAny,
    flags: String,
)

@JsFun("(addon) => addon.v8GetFlagsString()")
internal external fun napiV8GetFlagsString(addon: JsAny): String

@JsFun("(addon, joined, usage) => addon.v8SetFlagsFromCommandLineJoined(joined, usage)")
internal external fun napiV8SetFlagsFromCommandLineJoined(
    addon: JsAny,
    joined: String,
    usage: String?,
): String

@JsFun("(addon, platformPtr) => addon.v8InitializePlatform(platformPtr)")
internal external fun napiV8InitializePlatform(
    addon: JsAny,
    platformPtr: Double,
)

@JsFun("(addon) => addon.v8Initialize()")
internal external fun napiV8Initialize(addon: JsAny)

@JsFun("(addon) => addon.v8Dispose()")
internal external fun napiV8Dispose(addon: JsAny): Boolean

@JsFun("(addon) => addon.v8DisposePlatform()")
internal external fun napiV8DisposePlatform(addon: JsAny)

@JsFun("(addon, poolSize, idle) => addon.platformNewDefaultPlatform(poolSize, idle)")
internal external fun napiPlatformNewDefaultPlatform(
    addon: JsAny,
    poolSize: Int,
    idle: Boolean,
): Double

@JsFun("(addon, poolSize, idle) => addon.platformNewUnprotectedDefaultPlatform(poolSize, idle)")
internal external fun napiPlatformNewUnprotectedDefaultPlatform(
    addon: JsAny,
    poolSize: Int,
    idle: Boolean,
): Double

@JsFun("(addon, idle) => addon.platformNewSingleThreadedDefaultPlatform(idle)")
internal external fun napiPlatformNewSingleThreadedDefaultPlatform(
    addon: JsAny,
    idle: Boolean,
): Double

@JsFun("(addon, platformPtr) => addon.platformDelete(platformPtr)")
internal external fun napiPlatformDelete(
    addon: JsAny,
    platformPtr: Double,
)

@JsFun("(addon, platformPtr, isolatePtr, wait) => addon.platformPumpMessageLoop(platformPtr, isolatePtr, wait)")
internal external fun napiPlatformPumpMessageLoop(
    addon: JsAny,
    platformPtr: Double,
    isolatePtr: Double,
    wait: Boolean,
): Boolean

@JsFun("(addon, platformPtr, isolatePtr, secs) => addon.platformRunIdleTasks(platformPtr, isolatePtr, secs)")
internal external fun napiPlatformRunIdleTasks(
    addon: JsAny,
    platformPtr: Double,
    isolatePtr: Double,
    secs: Double,
)

@JsFun("(addon, platformPtr, isolatePtr) => addon.platformNotifyIsolateShutdown(platformPtr, isolatePtr)")
internal external fun napiPlatformNotifyIsolateShutdown(
    addon: JsAny,
    platformPtr: Double,
    isolatePtr: Double,
)

@JsFun("(addon, platformPtr) => addon.platformGetThreadPoolSize(platformPtr)")
internal external fun napiPlatformGetThreadPoolSize(
    addon: JsAny,
    platformPtr: Double,
): Int

@JsFun("(addon, platformPtr) => addon.platformHasIdleTaskSupport(platformPtr)")
internal external fun napiPlatformHasIdleTaskSupport(
    addon: JsAny,
    platformPtr: Double,
): Boolean

@JsFun("(addon, platformPtr) => addon.platformIsUnprotected(platformPtr)")
internal external fun napiPlatformIsUnprotected(
    addon: JsAny,
    platformPtr: Double,
): Boolean

@JsFun("(addon, platformPtr) => addon.platformIsSingleThreaded(platformPtr)")
internal external fun napiPlatformIsSingleThreaded(
    addon: JsAny,
    platformPtr: Double,
): Boolean

@JsFun("(addon) => addon.isolateNewDefault()")
internal external fun napiIsolateNewDefault(addon: JsAny): Double

@JsFun("(addon, isolatePtr) => addon.isolateDispose(isolatePtr)")
internal external fun napiIsolateDispose(
    addon: JsAny,
    isolatePtr: Double,
)

@JsFun("(addon, isolatePtr) => addon.isolateEnter(isolatePtr)")
internal external fun napiIsolateEnter(
    addon: JsAny,
    isolatePtr: Double,
)

@JsFun("(addon, isolatePtr) => addon.isolateExit(isolatePtr)")
internal external fun napiIsolateExit(
    addon: JsAny,
    isolatePtr: Double,
)

@JsFun("(addon) => addon.isolateGetCurrent()")
internal external fun napiIsolateGetCurrent(addon: JsAny): Double

@JsFun("(addon, isolatePtr) => addon.isolateGetNumberOfDataSlots(isolatePtr)")
internal external fun napiIsolateGetNumberOfDataSlots(
    addon: JsAny,
    isolatePtr: Double,
): Int

@JsFun("(addon, isolatePtr, slot) => addon.isolateGetData(isolatePtr, slot)")
internal external fun napiIsolateGetData(
    addon: JsAny,
    isolatePtr: Double,
    slot: Int,
): Double

@JsFun("(addon, isolatePtr, slot, dataPtr) => addon.isolateSetData(isolatePtr, slot, dataPtr)")
internal external fun napiIsolateSetData(
    addon: JsAny,
    isolatePtr: Double,
    slot: Int,
    dataPtr: Double,
)

@JsFun("(addon, isolatePtr) => addon.isolateGetMicrotasksPolicy(isolatePtr)")
internal external fun napiIsolateGetMicrotasksPolicy(
    addon: JsAny,
    isolatePtr: Double,
): Int

@JsFun("(addon, isolatePtr, policy) => addon.isolateSetMicrotasksPolicy(isolatePtr, policy)")
internal external fun napiIsolateSetMicrotasksPolicy(
    addon: JsAny,
    isolatePtr: Double,
    policy: Int,
)

@JsFun("(addon, isolatePtr) => addon.isolatePerformMicrotaskCheckpoint(isolatePtr)")
internal external fun napiIsolatePerformMicrotaskCheckpoint(
    addon: JsAny,
    isolatePtr: Double,
)

@JsFun("(addon, isolatePtr, level) => addon.isolateMemoryPressureNotification(isolatePtr, level)")
internal external fun napiIsolateMemoryPressureNotification(
    addon: JsAny,
    isolatePtr: Double,
    level: Int,
)

@JsFun("(addon, isolatePtr) => addon.isolateLowMemoryNotification(isolatePtr)")
internal external fun napiIsolateLowMemoryNotification(
    addon: JsAny,
    isolatePtr: Double,
)

@JsFun("(addon, isolatePtr) => addon.isolateClearKeptObjects(isolatePtr)")
internal external fun napiIsolateClearKeptObjects(
    addon: JsAny,
    isolatePtr: Double,
)

@JsFun("(addon, isolatePtr, fieldIndex) => addon.isolateGetHeapStatisticsField(isolatePtr, fieldIndex)")
internal external fun napiIsolateGetHeapStatisticsField(
    addon: JsAny,
    isolatePtr: Double,
    fieldIndex: Int,
): Double

@JsFun("(addon, isolatePtr, index) => addon.isolateGetHeapSpaceName(isolatePtr, index)")
internal external fun napiIsolateGetHeapSpaceName(
    addon: JsAny,
    isolatePtr: Double,
    index: Int,
): String

@JsFun("(addon, isolatePtr, index, fieldIndex) => addon.isolateGetHeapSpaceSizeField(isolatePtr, index, fieldIndex)")
internal external fun napiIsolateGetHeapSpaceSizeField(
    addon: JsAny,
    isolatePtr: Double,
    index: Int,
    fieldIndex: Int,
): Double

@JsFun("(addon, isolatePtr, fieldIndex) => addon.isolateGetHeapCodeStatisticsField(isolatePtr, fieldIndex)")
internal external fun napiIsolateGetHeapCodeStatisticsField(
    addon: JsAny,
    isolatePtr: Double,
    fieldIndex: Int,
): Double
