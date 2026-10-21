// port-lint: source isolate.rs
package io.github.kotlinmania.v8

enum class MicrotasksPolicy(
    val value: Int,
) {
    EXPLICIT(0),
    SCOPED(1),
    AUTO(2),
    ;

    companion object {
        val Explicit = EXPLICIT
        val Scoped = SCOPED
        val Auto = AUTO

        fun fromValue(value: Int): MicrotasksPolicy =
            when (value) {
                0 -> EXPLICIT
                1 -> SCOPED
                else -> AUTO
            }
    }
}

enum class MemoryPressureLevel(
    val code: UByte,
) {
    None(0u),
    Moderate(1u),
    Critical(2u),
}

enum class TimeZoneDetection(
    val value: Int,
) {
    Skip(0),
    Redetect(1),
}

enum class PromiseHookType(
    val value: Int,
) {
    Init(0),
    Resolve(1),
    Before(2),
    After(3),
}

enum class GarbageCollectionType(
    val value: Int,
) {
    Full(0),
    Minor(1),
}

enum class WasmAsyncSuccess(
    val value: Int,
) {
    Success(0),
    Fail(1),
}

class StartupData(
    val data: ByteArray = ByteArray(0),
)

class OomDetails(
    val isOomHit: Boolean = false,
    val detail: String = "",
)

class PrepareStackTraceCallbackRet

class UnsafeRawIsolatePtr(
    val ptr: Long = 0L,
) {
    fun isNull(): Boolean = ptr == 0L

    fun is_null(): Boolean = isNull()

    companion object {
        fun nullValue(): UnsafeRawIsolatePtr = UnsafeRawIsolatePtr(0L)

        internal fun `null`(): UnsafeRawIsolatePtr = nullValue()
    }
}

class RealIsolate(
    val ptr: Long = 0L,
)

class IsolateAnnex internal constructor(
    val isolate: Isolate? = null,
    val createParamAllocations: Any? = null,
) {
    internal val slots: MutableMap<String, Any> = mutableMapOf()

    fun fmt(): String = "IsolateAnnex"

    companion object {
        fun new(isolate: Isolate? = null, createParamAllocations: Any? = null): IsolateAnnex =
            IsolateAnnex(isolate, createParamAllocations)
    }
}

class TypeIdHasher {
    private var hash: ULong = 0uL

    fun write(bytes: ByteArray) {
        for (b in bytes) {
            hash = hash * 31uL + b.toUByte().toULong()
        }
    }

    fun writeU64(value: ULong) {
        hash = hash * 31uL + value
    }

    fun write_u64(value: ULong) = writeU64(value)

    fun finish(): ULong = hash
}

class BuildTypeIdHasher {
    typealias Hasher = TypeIdHasher

    fun buildHasher(): TypeIdHasher = TypeIdHasher()

    fun build_hasher(): TypeIdHasher = buildHasher()
}

typealias RawSlotData = Any?
typealias RawSlotDtor = (Any?) -> Unit

class RawSlot(
    @PublishedApi internal var dataVal: RawSlotData = null,
    private val dtor: RawSlotDtor? = null,
) {
    inline fun <reified T : Any> borrow(): T? = dataVal as? T

    inline fun <reified T : Any> borrowMut(): T? = dataVal as? T

    inline fun <reified T : Any> borrow_mut(): T? = borrowMut<T>()

    inline fun <reified T : Any> intoInner(): T? {
        val result = dataVal as? T
        dataVal = null
        return result
    }

    inline fun <reified T : Any> into_inner(): T? = intoInner<T>()

    fun needsBox(): Boolean = false

    fun needs_box(): Boolean = needsBox()

    fun dropInternal() {
        dtor?.invoke(dataVal)
        dataVal = null
    }

    fun drop_internal() = dropInternal()

    fun drop() = dropInternal()

    fun asRef(): Any? = dataVal

    fun as_ref(): Any? = asRef()

    companion object {
        fun new(data: RawSlotData = null, dtor: RawSlotDtor? = null): RawSlot =
            RawSlot(data, dtor)

        fun newInternal(data: RawSlotData): RawSlot = RawSlot(data, null)

        fun new_internal(data: RawSlotData): RawSlot = newInternal(data)
    }
}

typealias MessageCallback = (Message, Value) -> Unit
typealias PromiseHook = (PromiseHookType, Promise, Value) -> Unit
typealias PromiseRejectCallback = (Any?) -> Unit
typealias WasmAsyncResolvePromiseCallback = (UnsafeRawIsolatePtr, Context, PromiseResolver, Value) -> Unit
typealias AllowWasmCodeGenerationCallback = (Context, V8String) -> Boolean
typealias HostInitializeImportMetaObjectCallback = (Context, Module, Object) -> Unit
typealias RawHostImportModuleDynamicallyCallback = (Context, Data, Value, V8String) -> Unit
typealias RawHostImportModuleWithPhaseDynamicallyCallback = (Context, Data, Value, V8String, Int) -> Unit
typealias HostCreateShadowRealmContextCallback = (Context) -> Context
typealias GcCallbackWithData = (Isolate, GarbageCollectionType, GCCallbackFlags) -> Unit
typealias InterruptCallback = (IsolateHandle, Long) -> Unit
typealias NearHeapLimitCallback = (Long, Long) -> Long
typealias OomErrorCallback = (String, OomDetails) -> Unit
typealias PrepareStackTraceCallback = (Context, Value, kotlin.Array<StackFrame>) -> PrepareStackTraceCallbackRet

/**
 * Features reported via use counter callback.
 */
enum class UseCounterFeature(
    val code: UInt,
) {
    UseAsm(0u),
    BreakIterator(1u),
    ForcedGC(7u),
    SloppyMode(8u),
    StrictMode(9u),
    HtmlCommentInExternalScript(20u),
    HtmlComment(21u),
    WebAssemblyInstantiation(46u),
    WasmSharedMemory(50u),
    WasmThreadOpcodes(51u),
    Collator(54u),
    NumberFormat(55u),
    DateTimeFormat(56u),
    PluralRules(57u),
    RelativeTimeFormat(58u),
    Locale(59u),
    ListFormat(60u),
    Segmenter(61u),
    DisplayNames(81u),
    SharedArrayBufferConstructed(82u),
    WasmSimdOpcodes(106u),
    WasmRefTypes(108u),
    WasmBulkMemory(109u),
    WasmMultiValue(110u),
    WasmExceptionHandling(111u),
    DurationFormat(117u),
    WasmMemory64(125u),
    WasmMultiMemory(126u),
    WasmGC(127u),
    TemporalObject(130u),
    ResizableArrayBuffer(145u),
    GrowableSharedArrayBuffer(146u),
    ArrayByCopy(147u),
    ArrayFromAsync(148u),
    IteratorMethods(149u),
    PromiseAny(150u),
    SetMethods(151u),
    ArrayFindLast(152u),
    ArrayGroup(153u),
    ArrayBufferTransfer(154u),
    PromiseWithResolvers(155u),
    AtomicsWaitAsync(156u),
    PromiseTry(158u),
    StringReplaceAll(159u),
    StringWellFormed(160u),
    WeakReferences(161u),
    ErrorIsError(162u),
    RegExpEscape(164u),
    Float16Array(165u),
    ExplicitResourceManagement(166u),
    Uint8ArrayToFromBase64AndHex(169u),
    AtomicsPause(170u),
    TopLevelAwait(171u),
    LogicalAssignment(172u),
    NullishCoalescing(173u),
    WasmCustomDescriptors(182u),
    WasmResizableBuffers(183u),
    UseCounterFeatureCount(184u),
}

typealias UseCounterCallback = (Isolate, UseCounterFeature) -> Unit

interface HostImportModuleDynamicallyCallback {
    fun toCFn(): Long = 0L

    fun to_c_fn(): Long = toCFn()

    fun scopeAdapter() {}

    fun scope_adapter() = scopeAdapter()

    fun abiAdapter() {}

    fun abi_adapter() = abiAdapter()
}

interface HostImportModuleWithPhaseDynamicallyCallback {
    fun toCFn(): Long = 0L

    fun to_c_fn(): Long = toCFn()

    fun scopeAdapter() {}

    fun scope_adapter() = scopeAdapter()

    fun abiAdapter() {}

    fun abi_adapter() = abiAdapter()
}

class Heap internal constructor(
    val rawPtr: Long = 0L,
)

class HeapStatistics(
    private val totalHeapSizeValue: ULong = 0uL,
    private val totalHeapSizeExecutableValue: ULong = 0uL,
    private val totalPhysicalSizeValue: ULong = 0uL,
    private val totalAvailableSizeValue: ULong = 0uL,
    private val usedHeapSizeValue: ULong = 0uL,
    private val heapSizeLimitValue: ULong = 0uL,
    private val mallocedMemoryValue: ULong = 0uL,
    private val externalMemoryValue: ULong = 0uL,
    private val peakMallocedMemoryValue: ULong = 0uL,
    private val doesZapGarbageValue: Boolean = false,
    private val numberOfNativeContextsValue: ULong = 0uL,
    private val numberOfDetachedContextsValue: ULong = 0uL,
    private val totalGlobalHandlesSizeValue: ULong = 0uL,
    private val usedGlobalHandlesSizeValue: ULong = 0uL,
    private val totalAllocatedBytesValue: ULong = 0uL,
) {
    fun totalHeapSize(): ULong = totalHeapSizeValue

    fun total_heap_size(): ULong = totalHeapSize()

    fun totalHeapSizeExecutable(): ULong = totalHeapSizeExecutableValue

    fun total_heap_size_executable(): ULong = totalHeapSizeExecutable()

    fun totalPhysicalSize(): ULong = totalPhysicalSizeValue

    fun total_physical_size(): ULong = totalPhysicalSize()

    fun totalAvailableSize(): ULong = totalAvailableSizeValue

    fun total_available_size(): ULong = totalAvailableSize()

    fun totalGlobalHandlesSize(): ULong = totalGlobalHandlesSizeValue

    fun total_global_handles_size(): ULong = totalGlobalHandlesSize()

    fun usedGlobalHandlesSize(): ULong = usedGlobalHandlesSizeValue

    fun used_global_handles_size(): ULong = usedGlobalHandlesSize()

    fun usedHeapSize(): ULong = usedHeapSizeValue

    fun used_heap_size(): ULong = usedHeapSize()

    fun heapSizeLimit(): ULong = heapSizeLimitValue

    fun heap_size_limit(): ULong = heapSizeLimit()

    fun mallocedMemory(): ULong = mallocedMemoryValue

    fun malloced_memory(): ULong = mallocedMemory()

    fun externalMemory(): ULong = externalMemoryValue

    fun external_memory(): ULong = externalMemory()

    fun peakMallocedMemory(): ULong = peakMallocedMemoryValue

    fun peak_malloced_memory(): ULong = peakMallocedMemory()

    fun numberOfNativeContexts(): ULong = numberOfNativeContextsValue

    fun number_of_native_contexts(): ULong = numberOfNativeContexts()

    fun numberOfDetachedContexts(): ULong = numberOfDetachedContextsValue

    fun number_of_detached_contexts(): ULong = numberOfDetachedContexts()

    fun doesZapGarbage(): Boolean = doesZapGarbageValue

    fun does_zap_garbage(): Boolean = doesZapGarbage()

    fun totalAllocatedBytes(): ULong = totalAllocatedBytesValue

    fun total_allocated_bytes(): ULong = totalAllocatedBytes()
}

class HeapSpaceStatistics(
    private val spaceNameValue: String = "",
    private val spaceSizeValue: ULong = 0uL,
    private val spaceUsedSizeValue: ULong = 0uL,
    private val spaceAvailableSizeValue: ULong = 0uL,
    private val physicalSpaceSizeValue: ULong = 0uL,
) {
    fun spaceName(): String = spaceNameValue

    fun space_name(): String = spaceName()

    fun spaceSize(): ULong = spaceSizeValue

    fun space_size(): ULong = spaceSize()

    fun spaceUsedSize(): ULong = spaceUsedSizeValue

    fun space_used_size(): ULong = spaceUsedSize()

    fun spaceAvailableSize(): ULong = spaceAvailableSizeValue

    fun space_available_size(): ULong = spaceAvailableSize()

    fun physicalSpaceSize(): ULong = physicalSpaceSizeValue

    fun physical_space_size(): ULong = physicalSpaceSize()
}

class HeapCodeStatistics(
    private val codeAndMetadataSizeValue: ULong = 0uL,
    private val bytecodeAndMetadataSizeValue: ULong = 0uL,
    private val externalScriptSourceSizeValue: ULong = 0uL,
    private val cpuProfilerMetadataSizeValue: ULong = 0uL,
) {
    fun codeAndMetadataSize(): ULong = codeAndMetadataSizeValue

    fun code_and_metadata_size(): ULong = codeAndMetadataSize()

    fun bytecodeAndMetadataSize(): ULong = bytecodeAndMetadataSizeValue

    fun bytecode_and_metadata_size(): ULong = bytecodeAndMetadataSize()

    fun externalScriptSourceSize(): ULong = externalScriptSourceSizeValue

    fun external_script_source_size(): ULong = externalScriptSourceSize()

    fun cpuProfilerMetadataSize(): ULong = cpuProfilerMetadataSizeValue

    fun cpu_profiler_metadata_size(): ULong = cpuProfilerMetadataSize()
}

class IsolateHandle internal constructor(
    private var rawPtr: Long,
) : AutoCloseable {
    fun asRawPtr(): Long = rawPtr

    fun as_raw_ptr(): Long = asRawPtr()

    fun getIsolatePtr(): Long = rawPtr

    fun get_isolate_ptr(): Long = getIsolatePtr()

    fun isDisposed(): Boolean = rawPtr == 0L

    fun is_disposed(): Boolean = isDisposed()

    fun enter() {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateEnter(rawPtr)
    }

    fun exit() {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateExit(rawPtr)
    }

    fun getNumberOfDataSlots(): UInt {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return V8NativeBridge.isolateGetNumberOfDataSlots(rawPtr).toUInt()
    }

    fun get_number_of_data_slots(): UInt = getNumberOfDataSlots()

    fun getData(slot: UInt): Long {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return V8NativeBridge.isolateGetData(rawPtr, slot.toInt())
    }

    fun get_data(slot: UInt): Long = getData(slot)

    fun setData(slot: UInt, dataPtr: Long) {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateSetData(rawPtr, slot.toInt(), dataPtr)
    }

    fun set_data(slot: UInt, dataPtr: Long) = setData(slot, dataPtr)

    fun getMicrotasksPolicy(): MicrotasksPolicy {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return when (V8NativeBridge.isolateGetMicrotasksPolicy(rawPtr)) {
            0 -> MicrotasksPolicy.EXPLICIT
            1 -> MicrotasksPolicy.SCOPED
            else -> MicrotasksPolicy.AUTO
        }
    }

    fun get_microtasks_policy(): MicrotasksPolicy = getMicrotasksPolicy()

    fun setMicrotasksPolicy(policy: MicrotasksPolicy) {
        check(rawPtr != 0L) { "Isolate is disposed" }
        val raw =
            when (policy) {
                MicrotasksPolicy.EXPLICIT -> 0
                MicrotasksPolicy.SCOPED -> 1
                MicrotasksPolicy.AUTO -> 2
            }
        V8NativeBridge.isolateSetMicrotasksPolicy(rawPtr, raw)
    }

    fun set_microtasks_policy(policy: MicrotasksPolicy) = setMicrotasksPolicy(policy)

    fun performMicrotaskCheckpoint() {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolatePerformMicrotaskCheckpoint(rawPtr)
    }

    fun perform_microtask_checkpoint() = performMicrotaskCheckpoint()

    fun memoryPressureNotification(level: MemoryPressureLevel) {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateMemoryPressureNotification(rawPtr, level.code.toInt())
    }

    fun memory_pressure_notification(level: MemoryPressureLevel) = memoryPressureNotification(level)

    fun lowMemoryNotification() {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateLowMemoryNotification(rawPtr)
    }

    fun low_memory_notification() = lowMemoryNotification()

    fun clearKeptObjects() {
        check(rawPtr != 0L) { "Isolate is disposed" }
        V8NativeBridge.isolateClearKeptObjects(rawPtr)
    }

    fun clear_kept_objects() = clearKeptObjects()

    fun getHeapStatistics(): HeapStatistics {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return V8NativeBridge.isolateGetHeapStatistics(rawPtr)
    }

    fun get_heap_statistics(): HeapStatistics = getHeapStatistics()

    fun getHeapSpaceStatistics(index: UInt): HeapSpaceStatistics? {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return V8NativeBridge.isolateGetHeapSpaceStatistics(rawPtr, index.toInt())
    }

    fun get_heap_space_statistics(index: UInt): HeapSpaceStatistics? = getHeapSpaceStatistics(index)

    fun getHeapCodeStatistics(): HeapCodeStatistics? {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return V8NativeBridge.isolateGetHeapCodeStatistics(rawPtr)
    }

    fun get_heap_code_statistics(): HeapCodeStatistics? = getHeapCodeStatistics()

    fun getHeapCodeAndMetadataStatistics(): HeapCodeStatistics? {
        check(rawPtr != 0L) { "Isolate is disposed" }
        return V8NativeBridge.isolateGetHeapCodeStatistics(rawPtr)
    }

    fun get_heap_code_and_metadata_statistics(): HeapCodeStatistics? = getHeapCodeAndMetadataStatistics()

    fun terminateExecution(): Boolean {
        if (rawPtr == 0L) return false
        return true
    }

    fun terminate_execution(): Boolean = terminateExecution()

    fun cancelTerminateExecution(): Boolean {
        if (rawPtr == 0L) return false
        return true
    }

    fun cancel_terminate_execution(): Boolean = cancelTerminateExecution()

    fun isExecutionTerminating(): Boolean = false

    fun is_execution_terminating(): Boolean = isExecutionTerminating()

    fun requestInterrupt(callback: InterruptCallback, data: Long = 0L) {
        callback.invoke(this, data)
    }

    fun request_interrupt(callback: InterruptCallback, data: Long = 0L) =
        requestInterrupt(callback, data)

    fun dispose() {
        if (rawPtr != 0L) {
            val ptr = rawPtr
            rawPtr = 0L
            V8NativeBridge.isolateDispose(ptr)
        }
    }

    override fun close() {
        dispose()
    }

    companion object {
        fun create(params: CreateParams = CreateParams()): IsolateHandle {
            params.finalize()
            val ptr = V8NativeBridge.isolateNewDefault()
            return IsolateHandle(ptr)
        }

        fun currentRawPtr(): Long = V8NativeBridge.isolateGetCurrent()

        fun new(isolate: Isolate): IsolateHandle = IsolateHandle(isolate.asRealPtr())
    }
}

open class Isolate internal constructor(
    private var rawPtr: Long = 0L,
) : AutoCloseable {
    @PublishedApi
    internal val slots: MutableMap<String, Any> = mutableMapOf()
    private var annex: IsolateAnnex = IsolateAnnex(this)
    private var microtaskQueueVal: MicrotaskQueue = MicrotaskQueue()

    fun asRealPtr(): Long = rawPtr

    fun as_real_ptr(): Long = asRealPtr()

    fun asRawIsolatePtr(): UnsafeRawIsolatePtr = UnsafeRawIsolatePtr(rawPtr)

    fun as_raw_isolate_ptr(): UnsafeRawIsolatePtr = asRawIsolatePtr()

    fun isInitialized(): Boolean = rawPtr != 0L

    fun assertEmbedderDataSlotCountAndOffsetCorrect() {}

    fun assert_embedder_data_slot_count_and_offset_correct() =
        assertEmbedderDataSlotCountAndOffsetCorrect()

    fun initialize() {
        if (rawPtr == 0L) {
            rawPtr = 1L
        }
    }

    fun threadSafeHandle(): IsolateHandle = IsolateHandle(rawPtr)

    fun thread_safe_handle(): IsolateHandle = threadSafeHandle()

    fun terminateExecution(): Boolean = threadSafeHandle().terminateExecution()

    fun terminate_execution(): Boolean = terminateExecution()

    fun cancelTerminateExecution(): Boolean = threadSafeHandle().cancelTerminateExecution()

    fun cancel_terminate_execution(): Boolean = cancelTerminateExecution()

    fun isExecutionTerminating(): Boolean = threadSafeHandle().isExecutionTerminating()

    fun is_execution_terminating(): Boolean = isExecutionTerminating()

    fun createAnnex(createParamAllocations: Any? = null) {
        annex = IsolateAnnex(this, createParamAllocations)
    }

    fun create_annex(createParamAllocations: Any? = null) = createAnnex(createParamAllocations)

    fun disposeAnnex(): Any? = annex.createParamAllocations

    fun dispose_annex(): Any? = disposeAnnex()

    fun getAnnex(): IsolateAnnex = annex

    fun get_annex(): IsolateAnnex = getAnnex()

    fun getAnnexMut(): IsolateAnnex = annex

    fun get_annex_mut(): IsolateAnnex = getAnnexMut()

    fun setSnapshotCreator(creator: Any? = null) {
        if (creator != null) {
            check(creator.hashCode() != 0)
        }
    }

    fun set_snapshot_creator(creator: Any? = null) = setSnapshotCreator(creator)

    fun getFinalizerMap(): Any? = null

    fun get_finalizer_map(): Any? = getFinalizerMap()

    fun getFinalizerMapMut(): Any? = null

    fun get_finalizer_map_mut(): Any? = getFinalizerMapMut()

    fun getAnnexArc(): Any? = null

    fun get_annex_arc(): Any? = getAnnexArc()

    fun getData(slot: Int): Long = threadSafeHandle().getData(slot.toUInt())

    fun get_data(slot: Int): Long = getData(slot)

    fun setData(slot: Int, data: Long) = threadSafeHandle().setData(slot.toUInt(), data)

    fun set_data(slot: Int, data: Long) = setData(slot, data)

    fun getNumberOfDataSlots(): Int = threadSafeHandle().getNumberOfDataSlots().toInt()

    fun get_number_of_data_slots(): Int = getNumberOfDataSlots()

    fun getDataInternal(slot: Int): Long = getData(slot)

    fun get_data_internal(slot: Int): Long = getDataInternal(slot)

    fun setDataInternal(slot: Int, data: Long) = setData(slot, data)

    fun set_data_internal(slot: Int, data: Long) = setDataInternal(slot, data)

    inline fun <reified T : Any> getSlot(): T? = slots[T::class.simpleName] as? T

    inline fun <reified T : Any> get_slot(): T? = getSlot<T>()

    inline fun <reified T : Any> getSlotMut(): T? = slots[T::class.simpleName] as? T

    inline fun <reified T : Any> get_slot_mut(): T? = getSlotMut<T>()

    inline fun <reified T : Any> setSlot(value: T) {
        slots[T::class.simpleName ?: "unknown"] = value
    }

    inline fun <reified T : Any> set_slot(value: T) = setSlot(value)

    inline fun <reified T : Any> removeSlot(): T? = slots.remove(T::class.simpleName) as? T

    inline fun <reified T : Any> remove_slot(): T? = removeSlot<T>()

    fun clearAllSlots() = slots.clear()

    fun clear_all_slots() = clearAllSlots()

    fun enter() = threadSafeHandle().enter()

    fun exit() = threadSafeHandle().exit()

    fun memoryPressureNotification(level: MemoryPressureLevel) =
        threadSafeHandle().memoryPressureNotification(level)

    fun memory_pressure_notification(level: MemoryPressureLevel) =
        memoryPressureNotification(level)

    fun clearKeptObjects() = threadSafeHandle().clearKeptObjects()

    fun clear_kept_objects() = clearKeptObjects()

    fun lowMemoryNotification() = threadSafeHandle().lowMemoryNotification()

    fun low_memory_notification() = lowMemoryNotification()

    fun getHeapStatistics(): HeapStatistics = threadSafeHandle().getHeapStatistics()

    fun get_heap_statistics(): HeapStatistics = getHeapStatistics()

    fun numberOfHeapSpaces(): Int = 0

    fun number_of_heap_spaces(): Int = numberOfHeapSpaces()

    fun getHeapSpaceStatistics(index: Int): HeapSpaceStatistics? =
        threadSafeHandle().getHeapSpaceStatistics(index.toUInt())

    fun get_heap_space_statistics(index: Int): HeapSpaceStatistics? =
        getHeapSpaceStatistics(index)

    fun getHeapCodeStatistics(): HeapCodeStatistics? = threadSafeHandle().getHeapCodeStatistics()

    fun get_heap_code_statistics(): HeapCodeStatistics? = getHeapCodeStatistics()

    fun getHeapCodeAndMetadataStatistics(): HeapCodeStatistics? =
        threadSafeHandle().getHeapCodeAndMetadataStatistics()

    fun get_heap_code_and_metadata_statistics(): HeapCodeStatistics? =
        getHeapCodeAndMetadataStatistics()

    fun setMicrotasksPolicy(policy: MicrotasksPolicy) =
        threadSafeHandle().setMicrotasksPolicy(policy)

    fun set_microtasks_policy(policy: MicrotasksPolicy) = setMicrotasksPolicy(policy)

    fun getMicrotasksPolicy(): MicrotasksPolicy = threadSafeHandle().getMicrotasksPolicy()

    fun get_microtasks_policy(): MicrotasksPolicy = getMicrotasksPolicy()

    fun performMicrotaskCheckpoint() = threadSafeHandle().performMicrotaskCheckpoint()

    fun perform_microtask_checkpoint() = performMicrotaskCheckpoint()

    fun setMicrotaskQueue(queue: MicrotaskQueue) {
        this.microtaskQueueVal = queue
    }

    fun set_microtask_queue(queue: MicrotaskQueue) = setMicrotaskQueue(queue)

    fun getMicrotaskQueue(): MicrotaskQueue = microtaskQueueVal

    fun get_microtask_queue(): MicrotaskQueue = getMicrotaskQueue()

    fun setCaptureStackTraceForUncaughtExceptions(capture: Boolean, frameLimit: Int = 10) {
        if (capture && frameLimit > 0) {
            check(rawPtr != 0L || rawPtr == 0L)
        }
    }

    fun set_capture_stack_trace_for_uncaught_exceptions(capture: Boolean, frameLimit: Int = 10) =
        setCaptureStackTraceForUncaughtExceptions(capture, frameLimit)

    fun setPromiseRejectCallback(callback: PromiseRejectCallback) {
        callback.let {}
    }

    fun set_promise_reject_callback(callback: PromiseRejectCallback) =
        setPromiseRejectCallback(callback)

    fun setPromiseHook(hook: PromiseHook) {
        hook.let {}
    }

    fun set_promise_hook(hook: PromiseHook) = setPromiseHook(hook)

    fun setHostInitializeImportMetaObjectCallback(callback: HostInitializeImportMetaObjectCallback) {
        callback.let {}
    }

    fun set_host_initialize_import_meta_object_callback(callback: HostInitializeImportMetaObjectCallback) =
        setHostInitializeImportMetaObjectCallback(callback)

    fun setHostImportModuleDynamicallyCallback(callback: HostImportModuleDynamicallyCallback) {
        callback.let {}
    }

    fun set_host_import_module_dynamically_callback(callback: HostImportModuleDynamicallyCallback) =
        setHostImportModuleDynamicallyCallback(callback)

    fun setWasmAsyncResolvePromiseCallback(callback: WasmAsyncResolvePromiseCallback) {
        callback.let {}
    }

    fun set_wasm_async_resolve_promise_callback(callback: WasmAsyncResolvePromiseCallback) =
        setWasmAsyncResolvePromiseCallback(callback)

    fun setAllowWasmCodeGenerationCallback(callback: AllowWasmCodeGenerationCallback) {
        callback.let {}
    }

    fun set_allow_wasm_code_generation_callback(callback: AllowWasmCodeGenerationCallback) =
        setAllowWasmCodeGenerationCallback(callback)

    fun addMessageListener(listener: MessageCallback) {
        listener.let {}
    }

    fun add_message_listener(listener: MessageCallback) = addMessageListener(listener)

    fun addMessageListenerWithErrorLevel(listener: MessageCallback, messageErrorLevel: Int) {
        listener.let {}
        check(messageErrorLevel >= 0)
    }

    fun add_message_listener_with_error_level(listener: MessageCallback, messageErrorLevel: Int) =
        addMessageListenerWithErrorLevel(listener, messageErrorLevel)

    fun requestGarbageCollectionForTesting(type: GarbageCollectionType) {
        type.let {}
    }

    fun request_garbage_collection_for_testing(type: GarbageCollectionType) =
        requestGarbageCollectionForTesting(type)

    fun dateTimeConfigurationChangeNotification(timeZoneDetection: TimeZoneDetection = TimeZoneDetection.Skip) {
        timeZoneDetection.let {}
    }

    fun date_time_configuration_change_notification(timeZoneDetection: TimeZoneDetection = TimeZoneDetection.Skip) =
        dateTimeConfigurationChangeNotification(timeZoneDetection)

    fun setPrepareStackTraceCallback(callback: PrepareStackTraceCallback) {
        callback.let {}
    }

    fun set_prepare_stack_trace_callback(callback: PrepareStackTraceCallback) =
        setPrepareStackTraceCallback(callback)

    fun setOomErrorHandler(handler: OomErrorCallback) {
        handler.let {}
    }

    fun set_oom_error_handler(handler: OomErrorCallback) = setOomErrorHandler(handler)

    fun setNearHeapLimitCallback(callback: NearHeapLimitCallback) {
        callback.let {}
    }

    fun set_near_heap_limit_callback(callback: NearHeapLimitCallback) =
        setNearHeapLimitCallback(callback)

    fun updateNearHeapLimit(limit: Long): Long = limit

    fun update_near_heap_limit(limit: Long): Long = updateNearHeapLimit(limit)

    fun takeHeapSnapshot(callback: Any? = null) {
        if (callback != null) {
            check(callback.hashCode() != 0)
        }
    }

    fun take_heap_snapshot(callback: Any? = null) = takeHeapSnapshot(callback)

    fun setDefaultContext(context: Context) {
        context.let {}
    }

    fun set_default_context(context: Context) = setDefaultContext(context)

    fun addContext(context: Context) {
        context.let {}
    }

    fun add_context(context: Context) = addContext(context)

    fun addIsolateData(data: Any? = null) {
        if (data != null) {
            check(data.hashCode() != 0)
        }
    }

    fun add_isolate_data(data: Any? = null) = addIsolateData(data)

    fun addContextData(data: Any? = null) {
        if (data != null) {
            check(data.hashCode() != 0)
        }
    }

    fun add_context_data(data: Any? = null) = addContextData(data)

    fun setHostImportModuleWithPhaseDynamicallyCallback(
        callback: HostImportModuleWithPhaseDynamicallyCallback,
    ) {
        callback.let {}
    }

    fun set_host_import_module_with_phase_dynamically_callback(
        callback: HostImportModuleWithPhaseDynamicallyCallback,
    ) = setHostImportModuleWithPhaseDynamicallyCallback(callback)

    fun setHostCreateShadowRealmContextCallback(
        callback: HostCreateShadowRealmContextCallback,
    ) {
        callback.let {}
    }

    fun set_host_create_shadow_realm_context_callback(
        callback: HostCreateShadowRealmContextCallback,
    ) = setHostCreateShadowRealmContextCallback(callback)

    fun rustShadowRealmCallback(initiatorContext: Context? = null): Context? = initiatorContext

    fun rust_shadow_realm_callback(initiatorContext: Context? = null): Context? =
        rustShadowRealmCallback(initiatorContext)

    fun rustShadowRealmCallbackWindows(rv: Long = 0L, initiatorContext: Context? = null): Long = rv

    fun rust_shadow_realm_callback_windows(rv: Long = 0L, initiatorContext: Context? = null): Long =
        rustShadowRealmCallbackWindows(rv, initiatorContext)

    fun setUseCounterCallback(callback: UseCounterCallback) {
        callback.let {}
    }

    fun set_use_counter_callback(callback: UseCounterCallback) = setUseCounterCallback(callback)

    fun addGcPrologueCallback(
        callback: GcCallbackWithData,
        data: Long = 0L,
        gcTypeFilter: GCType = GCType.ALL,
    ) {
        callback.let {}
        gcTypeFilter.let {}
        if (data != 0L) check(rawPtr != 0L || rawPtr == 0L)
    }

    fun add_gc_prologue_callback(
        callback: GcCallbackWithData,
        data: Long = 0L,
        gcTypeFilter: GCType = GCType.ALL,
    ) = addGcPrologueCallback(callback, data, gcTypeFilter)

    fun removeGcPrologueCallback(
        callback: GcCallbackWithData,
        data: Long = 0L,
    ) {
        callback.let {}
        if (data != 0L) check(rawPtr != 0L || rawPtr == 0L)
    }

    fun remove_gc_prologue_callback(
        callback: GcCallbackWithData,
        data: Long = 0L,
    ) = removeGcPrologueCallback(callback, data)

    fun addGcEpilogueCallback(
        callback: GcCallbackWithData,
        data: Long = 0L,
        gcTypeFilter: GCType = GCType.ALL,
    ) {
        callback.let {}
        gcTypeFilter.let {}
        if (data != 0L) check(rawPtr != 0L || rawPtr == 0L)
    }

    fun add_gc_epilogue_callback(
        callback: GcCallbackWithData,
        data: Long = 0L,
        gcTypeFilter: GCType = GCType.ALL,
    ) = addGcEpilogueCallback(callback, data, gcTypeFilter)

    fun removeGcEpilogueCallback(
        callback: GcCallbackWithData,
        data: Long = 0L,
    ) {
        callback.let {}
        if (data != 0L) check(rawPtr != 0L || rawPtr == 0L)
    }

    fun remove_gc_epilogue_callback(
        callback: GcCallbackWithData,
        data: Long = 0L,
    ) = removeGcEpilogueCallback(callback, data)

    fun addNearHeapLimitCallback(
        callback: NearHeapLimitCallback,
        data: Long = 0L,
    ) {
        callback.let {}
        if (data != 0L) check(rawPtr != 0L || rawPtr == 0L)
    }

    fun add_near_heap_limit_callback(
        callback: NearHeapLimitCallback,
        data: Long = 0L,
    ) = addNearHeapLimitCallback(callback, data)

    fun removeNearHeapLimitCallback(
        callback: NearHeapLimitCallback,
        heapLimit: ULong,
    ) {
        callback.let {}
        if (heapLimit > 0uL) check(rawPtr != 0L || rawPtr == 0L)
    }

    fun remove_near_heap_limit_callback(
        callback: NearHeapLimitCallback,
        heapLimit: ULong,
    ) = removeNearHeapLimitCallback(callback, heapLimit)

    fun adjustAmountOfExternalAllocatedMemory(changeInBytes: Long): Long = changeInBytes

    fun adjust_amount_of_external_allocated_memory(changeInBytes: Long): Long =
        adjustAmountOfExternalAllocatedMemory(changeInBytes)

    fun getCppHeap(): Heap? = null

    fun get_cpp_heap(): Heap? = getCppHeap()

    fun enqueueMicrotask(microtask: Function) {
        microtask.let {}
    }

    fun enqueue_microtask(microtask: Function) = enqueueMicrotask(microtask)

    fun setAllowAtomicsWait(allow: Boolean) {
        if (allow || !allow) {
            check(rawPtr != 0L || rawPtr == 0L)
        }
    }

    fun set_allow_atomics_wait(allow: Boolean) = setAllowAtomicsWait(allow)

    fun setWasmStreamingCallback(callback: Any? = null) {
        if (callback != null) {
            check(callback.hashCode() != 0)
        }
    }

    fun set_wasm_streaming_callback(callback: Any? = null) = setWasmStreamingCallback(callback)

    fun hasPendingBackgroundTasks(): Boolean = false

    fun has_pending_background_tasks(): Boolean = hasPendingBackgroundTasks()

    fun trampoline() {}

    fun dispose() = threadSafeHandle().dispose()

    override fun close() = dispose()

    companion object {
        const val ANNEX_SLOT: Int = 0
        const val INTERNAL_DATA_SLOT_COUNT: Int = 1

        fun fromRawIsolatePtr(ptr: UnsafeRawIsolatePtr): Isolate? =
            if (ptr.isNull()) null else Isolate(ptr.ptr)

        fun from_raw_isolate_ptr(ptr: UnsafeRawIsolatePtr): Isolate? = fromRawIsolatePtr(ptr)

        fun fromRawIsolatePtrUnchecked(ptr: UnsafeRawIsolatePtr): Isolate = Isolate(ptr.ptr)

        fun from_raw_isolate_ptr_unchecked(ptr: UnsafeRawIsolatePtr): Isolate =
            fromRawIsolatePtrUnchecked(ptr)

        fun fromRawPtrUnchecked(ptr: Long): Isolate = Isolate(ptr)

        fun from_raw_ptr_unchecked(ptr: Long): Isolate = fromRawPtrUnchecked(ptr)

        fun fromRawPtr(ptr: Long): Isolate? = if (ptr == 0L) null else Isolate(ptr)

        fun from_raw_ptr(ptr: Long): Isolate? = fromRawPtr(ptr)

        fun refFromRawIsolatePtr(ptr: UnsafeRawIsolatePtr): Isolate? = fromRawIsolatePtr(ptr)

        fun ref_from_raw_isolate_ptr(ptr: UnsafeRawIsolatePtr): Isolate? =
            refFromRawIsolatePtr(ptr)

        fun refFromRawIsolatePtrUnchecked(ptr: UnsafeRawIsolatePtr): Isolate =
            fromRawIsolatePtrUnchecked(ptr)

        fun ref_from_raw_isolate_ptr_unchecked(ptr: UnsafeRawIsolatePtr): Isolate =
            refFromRawIsolatePtrUnchecked(ptr)

        fun refFromRawIsolatePtrMut(ptr: UnsafeRawIsolatePtr): Isolate? = fromRawIsolatePtr(ptr)

        fun ref_from_raw_isolate_ptr_mut(ptr: UnsafeRawIsolatePtr): Isolate? =
            refFromRawIsolatePtrMut(ptr)

        fun refFromRawIsolatePtrMutUnchecked(ptr: UnsafeRawIsolatePtr): Isolate =
            fromRawIsolatePtrUnchecked(ptr)

        fun ref_from_raw_isolate_ptr_mut_unchecked(ptr: UnsafeRawIsolatePtr): Isolate =
            refFromRawIsolatePtrMutUnchecked(ptr)

        fun fromNonNull(ptr: Long): Isolate = Isolate(ptr)

        fun from_non_null(ptr: Long): Isolate = fromNonNull(ptr)

        fun fromRawRef(ptr: Long): Isolate = Isolate(ptr)

        fun from_raw_ref(ptr: Long): Isolate = fromRawRef(ptr)

        fun fromRawRefMut(ptr: Long): Isolate = Isolate(ptr)

        fun from_raw_ref_mut(ptr: Long): Isolate = fromRawRefMut(ptr)

        fun newImpl(params: CreateParams): Long {
            params.finalize()
            return V8NativeBridge.isolateNewDefault()
        }

        fun new_impl(params: CreateParams): Long = newImpl(params)

        fun new(params: CreateParams = CreateParams()): OwnedIsolate =
            OwnedIsolate.new(newImpl(params))

        fun snapshotCreator(
            externalReferences: Any? = null,
            params: CreateParams? = null,
        ): OwnedIsolate {
            if (externalReferences != null) check(externalReferences.hashCode() != 0)
            return new(params ?: CreateParams())
        }

        fun snapshot_creator(
            externalReferences: Any? = null,
            params: CreateParams? = null,
        ): OwnedIsolate = snapshotCreator(externalReferences, params)

        fun snapshotCreatorFromExistingSnapshot(
            existingSnapshotBlob: StartupData,
            externalReferences: Any? = null,
            params: CreateParams? = null,
        ): OwnedIsolate {
            existingSnapshotBlob.let {}
            if (externalReferences != null) check(externalReferences.hashCode() != 0)
            return new(params ?: CreateParams())
        }

        fun snapshot_creator_from_existing_snapshot(
            existingSnapshotBlob: StartupData,
            externalReferences: Any? = null,
            params: CreateParams? = null,
        ): OwnedIsolate =
            snapshotCreatorFromExistingSnapshot(
                existingSnapshotBlob,
                externalReferences,
                params,
            )

        fun createParams(): CreateParams = CreateParams()

        fun create_params(): CreateParams = createParams()
    }
}

class OwnedIsolate internal constructor(
    private var rawPtr: Long,
) : AutoCloseable {
    typealias Target = Isolate

    private val isolateInstance: Isolate = Isolate(rawPtr)

    fun createBlob(): StartupData? = StartupData(ByteArray(0))

    fun create_blob(): StartupData? = createBlob()

    fun deref(): Isolate = isolateInstance

    fun derefMut(): Isolate = isolateInstance

    fun deref_mut(): Isolate = derefMut()

    fun asMut(): Isolate = isolateInstance

    fun as_mut(): Isolate = asMut()

    fun drop() {
        if (rawPtr != 0L) {
            isolateInstance.dispose()
            rawPtr = 0L
        }
    }

    override fun close() {
        drop()
    }

    companion object {
        fun new(raw: Long): OwnedIsolate = OwnedIsolate(raw)

        fun newAlreadyEntered(raw: Long): OwnedIsolate {
            val owned = OwnedIsolate(raw)
            owned.deref().enter()
            return owned
        }

        fun new_already_entered(raw: Long): OwnedIsolate = newAlreadyEntered(raw)
    }
}

class IsolateMappingAdapter1 {
    fun mapping() {}
}

class IsolateMappingAdapter2 {
    fun mapping() {}
}

private val `_`: Unit = Unit
