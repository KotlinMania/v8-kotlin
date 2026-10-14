// port-lint: source binding.rs
package io.github.kotlinmania.v8

internal external interface V8NativeAddon {
    fun bindingMajorVersion(): Int

    fun bindingMinorVersion(): Int

    fun bindingBuildNumber(): Int

    fun bindingPatchLevel(): Int

    fun bindingVersionString(): String

    fun bindingScriptOriginSize(): Double

    fun bindingCppgcMemberSize(): Double

    fun bindingCppgcWeakMemberSize(): Double

    fun bindingTracedReferenceSize(): Double

    fun bindingEternalSize(): Double

    fun bindingStringValueViewSize(): Double

    fun bindingStringMaxLength(): Int

    fun bindingTypedArrayMaxByteLength(): Double

    fun bindingTypedArrayMaxSizeInHeap(): Double

    fun bindingUint8ArrayMaxLength(): Double

    fun bindingUint16ArrayMaxLength(): Double

    fun bindingUint32ArrayMaxLength(): Double

    fun bindingBigUint64ArrayMaxLength(): Double

    fun supportBoolToMaybeBool(value: Boolean): Int

    fun supportMaybeBoolNothing(): Int

    fun supportMaybeBoolIsNothing(maybeBool: Int): Boolean

    fun supportMaybeBoolToBool(maybeBool: Int): Boolean

    fun supportTwoPointersSize(): Double

    fun supportThreePointersSize(): Double

    fun supportHandleScopeSize(): Double

    fun supportEscapableHandleScopeSize(): Double

    fun supportPromiseRejectMessageSize(): Double

    fun supportLockerSize(): Double

    fun supportTryCatchSize(): Double

    fun supportAllowJavascriptExecutionScopeSize(): Double

    fun supportDisallowJavascriptExecutionScopeSize(): Double

    fun supportCachedDataSize(): Double

    fun v8GetVersion(): String

    fun v8IsSandboxEnabled(): Boolean

    fun v8SetFlagsFromString(flags: String)

    fun v8GetFlagsString(): String

    fun v8SetFlagsFromCommandLineJoined(
        joined: String,
        usage: String?,
    ): String

    fun v8InitializePlatform(platformPtr: Double)

    fun v8Initialize()

    fun v8Dispose(): Boolean

    fun v8DisposePlatform()

    fun platformNewDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Double

    fun platformNewUnprotectedDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Double

    fun platformNewSingleThreadedDefaultPlatform(idleTaskSupport: Boolean): Double

    fun platformDelete(platformPtr: Double)

    fun platformPumpMessageLoop(
        platformPtr: Double,
        isolatePtr: Double,
        waitForWork: Boolean,
    ): Boolean

    fun platformRunIdleTasks(
        platformPtr: Double,
        isolatePtr: Double,
        idleTimeInSeconds: Double,
    )

    fun platformNotifyIsolateShutdown(
        platformPtr: Double,
        isolatePtr: Double,
    )

    fun platformGetThreadPoolSize(platformPtr: Double): Int

    fun platformHasIdleTaskSupport(platformPtr: Double): Boolean

    fun platformIsUnprotected(platformPtr: Double): Boolean

    fun platformIsSingleThreaded(platformPtr: Double): Boolean

    fun isolateNewDefault(): Double

    fun isolateDispose(isolatePtr: Double)

    fun isolateEnter(isolatePtr: Double)

    fun isolateExit(isolatePtr: Double)

    fun isolateGetCurrent(): Double

    fun isolateGetNumberOfDataSlots(isolatePtr: Double): Int

    fun isolateGetData(
        isolatePtr: Double,
        slot: Int,
    ): Double

    fun isolateSetData(
        isolatePtr: Double,
        slot: Int,
        dataPtr: Double,
    )

    fun isolateGetMicrotasksPolicy(isolatePtr: Double): Int

    fun isolateSetMicrotasksPolicy(
        isolatePtr: Double,
        policy: Int,
    )

    fun isolatePerformMicrotaskCheckpoint(isolatePtr: Double)

    fun isolateMemoryPressureNotification(
        isolatePtr: Double,
        level: Int,
    )

    fun isolateLowMemoryNotification(isolatePtr: Double)

    fun isolateClearKeptObjects(isolatePtr: Double)

    fun isolateGetHeapStatisticsField(
        isolatePtr: Double,
        fieldIndex: Int,
    ): Double

    fun isolateGetHeapSpaceName(
        isolatePtr: Double,
        index: Int,
    ): String

    fun isolateGetHeapSpaceSizeField(
        isolatePtr: Double,
        index: Int,
        fieldIndex: Int,
    ): Double

    fun isolateGetHeapCodeStatisticsField(
        isolatePtr: Double,
        fieldIndex: Int,
    ): Double
}

internal val V8NativeBindings: V8NativeAddon =
    checkNotNull(
        js(
            """(() => {
                const proc = globalThis.process;
                if (proc && typeof proc.getBuiltinModule === "function") {
                    const modMod = proc.getBuiltinModule("module");
                    const path = proc.getBuiltinModule("path");
                    const fs = proc.getBuiltinModule("fs");
                    const req = modMod.createRequire(path.join(proc.cwd(), "package.json"));
                    const copiedMod = path.resolve(proc.cwd(), "../../node_modules/@kotlinmania/v8-native-bindings");
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
            })()""",
        ).unsafeCast<V8NativeAddon?>(),
    ) { "Failed to load @kotlinmania/v8-native-bindings" }
