// port-lint: source binding.rs
package io.github.kotlinmania.v8

internal expect object V8NativeBridge {
    fun bindingMajorVersion(): Int

    fun bindingMinorVersion(): Int

    fun bindingBuildNumber(): Int

    fun bindingPatchLevel(): Int

    fun bindingVersionString(): String

    fun bindingScriptOriginSize(): Long

    fun bindingCppgcMemberSize(): Long

    fun bindingCppgcWeakMemberSize(): Long

    fun bindingTracedReferenceSize(): Long

    fun bindingEternalSize(): Long

    fun bindingStringValueViewSize(): Long

    fun bindingStringMaxLength(): Int

    fun bindingTypedArrayMaxByteLength(): Long

    fun bindingTypedArrayMaxSizeInHeap(): Long

    fun bindingUint8ArrayMaxLength(): Long

    fun bindingUint16ArrayMaxLength(): Long

    fun bindingUint32ArrayMaxLength(): Long

    fun bindingBigUint64ArrayMaxLength(): Long

    fun supportBoolToMaybeBool(value: Boolean): Int

    fun supportMaybeBoolNothing(): Int

    fun supportMaybeBoolIsNothing(maybeBool: Int): Boolean

    fun supportMaybeBoolToBool(maybeBool: Int): Boolean

    fun supportTwoPointersSize(): Long

    fun supportThreePointersSize(): Long

    fun supportHandleScopeSize(): Long

    fun supportEscapableHandleScopeSize(): Long

    fun supportPromiseRejectMessageSize(): Long

    fun supportLockerSize(): Long

    fun supportTryCatchSize(): Long

    fun supportAllowJavascriptExecutionScopeSize(): Long

    fun supportDisallowJavascriptExecutionScopeSize(): Long

    fun supportCachedDataSize(): Long

    fun v8GetVersion(): String

    fun v8IsSandboxEnabled(): Boolean

    fun v8SetFlagsFromString(flags: String)

    fun v8GetFlagsString(): String

    fun v8SetFlagsFromCommandLine(
        args: List<String>,
        usage: String?,
    ): List<String>

    fun v8InitializePlatform(platformPtr: Long)

    fun v8Initialize()

    fun v8Dispose(): Boolean

    fun v8DisposePlatform()

    fun platformNewDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long

    fun platformNewUnprotectedDefaultPlatform(
        threadPoolSize: Int,
        idleTaskSupport: Boolean,
    ): Long

    fun platformNewSingleThreadedDefaultPlatform(idleTaskSupport: Boolean): Long

    fun platformDelete(platformPtr: Long)

    fun platformPumpMessageLoop(
        platformPtr: Long,
        isolatePtr: Long,
        waitForWork: Boolean,
    ): Boolean

    fun platformRunIdleTasks(
        platformPtr: Long,
        isolatePtr: Long,
        idleTimeInSeconds: Double,
    )

    fun platformNotifyIsolateShutdown(
        platformPtr: Long,
        isolatePtr: Long,
    )

    fun platformGetThreadPoolSize(platformPtr: Long): Int

    fun platformHasIdleTaskSupport(platformPtr: Long): Boolean

    fun platformIsUnprotected(platformPtr: Long): Boolean

    fun platformIsSingleThreaded(platformPtr: Long): Boolean

    fun isolateNewDefault(): Long

    fun isolateDispose(isolatePtr: Long)

    fun isolateEnter(isolatePtr: Long)

    fun isolateExit(isolatePtr: Long)

    fun isolateGetCurrent(): Long

    fun isolateGetNumberOfDataSlots(isolatePtr: Long): Int

    fun isolateGetData(
        isolatePtr: Long,
        slot: Int,
    ): Long

    fun isolateSetData(
        isolatePtr: Long,
        slot: Int,
        dataPtr: Long,
    )

    fun isolateGetMicrotasksPolicy(isolatePtr: Long): Int

    fun isolateSetMicrotasksPolicy(
        isolatePtr: Long,
        policy: Int,
    )

    fun isolatePerformMicrotaskCheckpoint(isolatePtr: Long)

    fun isolateMemoryPressureNotification(
        isolatePtr: Long,
        level: Int,
    )

    fun isolateLowMemoryNotification(isolatePtr: Long)

    fun isolateClearKeptObjects(isolatePtr: Long)

    fun isolateGetHeapStatistics(isolatePtr: Long): HeapStatistics

    fun isolateGetHeapSpaceStatistics(
        isolatePtr: Long,
        index: Int,
    ): HeapSpaceStatistics?

    fun isolateGetHeapCodeStatistics(isolatePtr: Long): HeapCodeStatistics?
}

/** Major version number of the bundled V8 engine. */
const val MAJOR_VERSION: UInt = 14u

/** Minor version number of the bundled V8 engine. */
const val MINOR_VERSION: UInt = 6u

/** Build number of the bundled V8 engine. */
const val BUILD_NUMBER: UInt = 202u

/** Patch level of the bundled V8 engine. */
const val PATCH_LEVEL: UInt = 9u

/** Version string of the bundled V8 engine. */
const val VERSION_STRING: String = "14.6.202.9-rusty"

/** Size in bytes of script origin metadata on 64-bit targets. */
const val SCRIPT_ORIGIN_SIZE: ULong = 40uL

/** Size in bytes of member pointer. */
const val CPPGC_MEMBER_SIZE: ULong = 4uL

/** Size in bytes of weak member pointer. */
const val CPPGC_WEAK_MEMBER_SIZE: ULong = 4uL

/** Size in bytes of traced reference pointer. */
const val TRACED_REFERENCE_SIZE: ULong = 8uL

/** Size in bytes of eternal handle pointer. */
const val ETERNAL_SIZE: ULong = 8uL

/** Size in bytes of string value view. */
const val STRING_VALUE_VIEW_SIZE: ULong = 32uL

/** Maximum length of a string. */
const val STRING_MAX_LENGTH: Int = 536870888

/** Maximum byte length of a typed array. */
const val TYPED_ARRAY_MAX_BYTE_LENGTH: ULong = 9007199254740991uL

/** Maximum on-heap size for a typed array. */
const val TYPED_ARRAY_MAX_SIZE_IN_HEAP: ULong = 0uL

/** Maximum length of an unsigned 8-bit array. */
const val UINT8_ARRAY_MAX_LENGTH: ULong = 9007199254740991uL

/** Maximum length of an unsigned clamped 8-bit array. */
const val UINT8_CLAMPED_ARRAY_MAX_LENGTH: ULong = 9007199254740991uL

/** Maximum length of a signed 8-bit array. */
const val INT8_ARRAY_MAX_LENGTH: ULong = 9007199254740991uL

/** Maximum length of an unsigned 16-bit array. */
const val UINT16_ARRAY_MAX_LENGTH: ULong = 4503599627370495uL

/** Maximum length of a signed 16-bit array. */
const val INT16_ARRAY_MAX_LENGTH: ULong = 4503599627370495uL

/** Maximum length of an unsigned 32-bit array. */
const val UINT32_ARRAY_MAX_LENGTH: ULong = 2251799813685247uL

/** Maximum length of a signed 32-bit array. */
const val INT32_ARRAY_MAX_LENGTH: ULong = 2251799813685247uL

/** Maximum length of a 16-bit float array. */
const val FLOAT16_ARRAY_MAX_LENGTH: ULong = 4503599627370495uL

/** Maximum length of a 32-bit float array. */
const val FLOAT32_ARRAY_MAX_LENGTH: ULong = 2251799813685247uL

/** Maximum length of a 64-bit float array. */
const val FLOAT64_ARRAY_MAX_LENGTH: ULong = 1125899906842623uL

/** Maximum length of an unsigned 64-bit integer array. */
const val BIG_UINT64_ARRAY_MAX_LENGTH: ULong = 1125899906842623uL

/** Maximum length of a signed 64-bit integer array. */
const val BIG_INT64_ARRAY_MAX_LENGTH: ULong = 1125899906842623uL

/** Name used by Oilpan when hiding internal C++ types. */
const val CPPGC_HIDDEN_NAME: String = "InternalNode"

/** Name used by Oilpan when compiler RTTI is disabled. */
const val CPPGC_NO_NAME_DEDUCIBLE: String = "<No name>"

/**
 * Queries the native binding layer for version and ABI layout values.
 */
object V8Binding {
    fun majorVersion(): UInt = V8NativeBridge.bindingMajorVersion().toUInt()

    fun minorVersion(): UInt = V8NativeBridge.bindingMinorVersion().toUInt()

    fun buildNumber(): UInt = V8NativeBridge.bindingBuildNumber().toUInt()

    fun patchLevel(): UInt = V8NativeBridge.bindingPatchLevel().toUInt()

    fun versionString(): String = V8NativeBridge.bindingVersionString()

    fun scriptOriginSize(): ULong = V8NativeBridge.bindingScriptOriginSize().toULong()

    fun cppgcMemberSize(): ULong = V8NativeBridge.bindingCppgcMemberSize().toULong()

    fun cppgcWeakMemberSize(): ULong = V8NativeBridge.bindingCppgcWeakMemberSize().toULong()

    fun tracedReferenceSize(): ULong = V8NativeBridge.bindingTracedReferenceSize().toULong()

    fun eternalSize(): ULong = V8NativeBridge.bindingEternalSize().toULong()

    fun stringValueViewSize(): ULong = V8NativeBridge.bindingStringValueViewSize().toULong()

    fun stringMaxLength(): Int = V8NativeBridge.bindingStringMaxLength()

    fun typedArrayMaxByteLength(): ULong = V8NativeBridge.bindingTypedArrayMaxByteLength().toULong()

    fun typedArrayMaxSizeInHeap(): ULong = V8NativeBridge.bindingTypedArrayMaxSizeInHeap().toULong()

    fun uint8ArrayMaxLength(): ULong = V8NativeBridge.bindingUint8ArrayMaxLength().toULong()

    fun uint16ArrayMaxLength(): ULong = V8NativeBridge.bindingUint16ArrayMaxLength().toULong()

    fun uint32ArrayMaxLength(): ULong = V8NativeBridge.bindingUint32ArrayMaxLength().toULong()

    fun bigUint64ArrayMaxLength(): ULong = V8NativeBridge.bindingBigUint64ArrayMaxLength().toULong()
}

/**
 * Interceptor callbacks use this value to indicate whether the request was intercepted or not.
 */
enum class Intercepted(
    val code: UInt,
) {
    Yes(0u),
    No(1u),
}

/**
 * Import phases in module import requests.
 */
enum class ModuleImportPhase(
    val code: Int,
) {
    Source(0),
    Defer(1),
    Evaluation(2),
}

/**
 * Flags controlling string write operations.
 */
class WriteFlags private constructor(
    val bits: UInt,
) {
    infix fun or(other: WriteFlags): WriteFlags = WriteFlags(this.bits or other.bits)

    infix fun and(other: WriteFlags): WriteFlags = WriteFlags(this.bits and other.bits)

    fun contains(other: WriteFlags): Boolean = (this.bits and other.bits) == other.bits

    override fun equals(other: Any?): Boolean = other is WriteFlags && this.bits == other.bits

    override fun hashCode(): Int = bits.hashCode()

    override fun toString(): String = "WriteFlags(0x${bits.toString(16)})"

    companion object {
        val NONE: WriteFlags = WriteFlags(0u)
        val NULL_TERMINATE: WriteFlags = WriteFlags(1u)
        val REPLACE_INVALID_UTF8: WriteFlags = WriteFlags(2u)

        fun fromBits(bits: UInt): WriteFlags = WriteFlags(bits)
    }
}

/**
 * Garbage collection type flags.
 */
class GCType private constructor(
    val bits: UInt,
) {
    infix fun or(other: GCType): GCType = GCType(this.bits or other.bits)

    infix fun and(other: GCType): GCType = GCType(this.bits and other.bits)

    fun contains(other: GCType): Boolean = (this.bits and other.bits) == other.bits

    override fun equals(other: Any?): Boolean = other is GCType && this.bits == other.bits

    override fun hashCode(): Int = bits.hashCode()

    override fun toString(): String = "GCType(0x${bits.toString(16)})"

    companion object {
        val SCAVENGE: GCType = GCType(1u)
        val MINOR_MARK_SWEEP: GCType = GCType(2u)
        val MARK_SWEEP_COMPACT: GCType = GCType(4u)
        val INCREMENTAL_MARKING: GCType = GCType(8u)
        val PROCESS_WEAK_CALLBACKS: GCType = GCType(16u)
        val ALL: GCType = GCType(31u)

        fun fromBits(bits: UInt): GCType = GCType(bits)
    }
}

/**
 * Flags passed to garbage collection callbacks.
 */
class GCCallbackFlags private constructor(
    val bits: UInt,
) {
    infix fun or(other: GCCallbackFlags): GCCallbackFlags = GCCallbackFlags(this.bits or other.bits)

    infix fun and(other: GCCallbackFlags): GCCallbackFlags = GCCallbackFlags(this.bits and other.bits)

    fun contains(other: GCCallbackFlags): Boolean = (this.bits and other.bits) == other.bits

    override fun equals(other: Any?): Boolean = other is GCCallbackFlags && this.bits == other.bits

    override fun hashCode(): Int = bits.hashCode()

    override fun toString(): String = "GCCallbackFlags(0x${bits.toString(16)})"

    companion object {
        val NO_GC_CALLBACK_FLAGS: GCCallbackFlags = GCCallbackFlags(0u)
        val CONSTRUCT_RETAINED_OBJECT_INFOS: GCCallbackFlags = GCCallbackFlags(2u)
        val FORCED: GCCallbackFlags = GCCallbackFlags(4u)
        val SYNCHRONOUS_PHANTOM_CALLBACK_PROCESSING: GCCallbackFlags = GCCallbackFlags(8u)
        val COLLECT_ALL_AVAILABLE_GARBAGE: GCCallbackFlags = GCCallbackFlags(16u)
        val COLLECT_ALL_EXTERNAL_MEMORY: GCCallbackFlags = GCCallbackFlags(32u)
        val SCHEDULE_IDLE_GARBAGE_COLLECTION: GCCallbackFlags = GCCallbackFlags(64u)
        val LAST_RESORT: GCCallbackFlags = GCCallbackFlags(128u)

        fun fromBits(bits: UInt): GCCallbackFlags = GCCallbackFlags(bits)
    }
}

/**
 * Fast API call primitive and object types.
 */
enum class CTypeInfoType(
    val code: UByte,
) {
    Void(0u),
    Bool(1u),
    Uint8(2u),
    Int32(3u),
    Uint32(4u),
    Int64(5u),
    Uint64(6u),
    Float32(7u),
    Float64(8u),
    Pointer(9u),
    V8Value(10u),
    SeqOneByteString(11u),
    ApiObject(12u),
    Any(13u),
}

/**
 * Fast API call parameter attribute flags.
 */
class CTypeInfoFlags private constructor(
    val bits: UByte,
) {
    infix fun or(other: CTypeInfoFlags): CTypeInfoFlags = CTypeInfoFlags(this.bits or other.bits)

    infix fun and(other: CTypeInfoFlags): CTypeInfoFlags = CTypeInfoFlags(this.bits and other.bits)

    fun contains(other: CTypeInfoFlags): Boolean = (this.bits and other.bits) == other.bits

    override fun equals(other: Any?): Boolean = other is CTypeInfoFlags && this.bits == other.bits

    override fun hashCode(): Int = bits.hashCode()

    override fun toString(): String = "CTypeInfoFlags(0x${bits.toString(16)})"

    companion object {
        val NONE: CTypeInfoFlags = CTypeInfoFlags(0u)
        val ALLOW_SHARED_BIT: CTypeInfoFlags = CTypeInfoFlags(1u)
        val ENFORCE_RANGE_BIT: CTypeInfoFlags = CTypeInfoFlags(2u)
        val CLAMP_BIT: CTypeInfoFlags = CTypeInfoFlags(4u)
        val IS_RESTRICTED_BIT: CTypeInfoFlags = CTypeInfoFlags(8u)

        fun fromBits(bits: UByte): CTypeInfoFlags = CTypeInfoFlags(bits)
    }
}

/**
 * Type descriptor for V8 Fast API calls.
 */
data class CTypeInfo(
    val type: CTypeInfoType,
    val flags: CTypeInfoFlags = CTypeInfoFlags.NONE,
)

/**
 * Representation of 64-bit integers in V8 Fast API calls.
 */
enum class Int64Representation(
    val code: UByte,
) {
    Number(0u),
    BigInt(1u),
}

/**
 * Overload resolution mode for C functions.
 */
enum class OverloadResolution(
    val code: Int,
) {
    Impossible(0),
    AtRuntime(1),
    AtCompileTime(2),
}

/**
 * One-byte string view passed to V8 Fast API callbacks.
 */
data class FastOneByteString(
    val data: String,
    val length: UInt = data.length.toUInt(),
)

/**
 * Signature metadata for a V8 Fast API function.
 */
class CFunctionInfo(
    val returnInfo: CTypeInfo,
    val argumentInfos: List<CTypeInfo>,
    val repr: Int64Representation = Int64Representation.Number,
) {
    val argumentCount: UInt
        get() = argumentInfos.size.toUInt()

    fun argumentInfo(index: UInt): CTypeInfo? = argumentInfos.getOrNull(index.toInt())
}

/**
 * Fast API function descriptor.
 */
class CFunction(
    val address: Long,
    val typeInfo: CFunctionInfo,
)

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

/**
 * Collection of V8 heap information.
 *
 * Instances of this class are populated by isolate heap statistics queries.
 */
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

    fun totalHeapSizeExecutable(): ULong = totalHeapSizeExecutableValue

    fun totalPhysicalSize(): ULong = totalPhysicalSizeValue

    fun totalAvailableSize(): ULong = totalAvailableSizeValue

    fun totalGlobalHandlesSize(): ULong = totalGlobalHandlesSizeValue

    fun usedGlobalHandlesSize(): ULong = usedGlobalHandlesSizeValue

    fun usedHeapSize(): ULong = usedHeapSizeValue

    fun heapSizeLimit(): ULong = heapSizeLimitValue

    fun mallocedMemory(): ULong = mallocedMemoryValue

    fun externalMemory(): ULong = externalMemoryValue

    fun peakMallocedMemory(): ULong = peakMallocedMemoryValue

    fun numberOfNativeContexts(): ULong = numberOfNativeContextsValue

    fun numberOfDetachedContexts(): ULong = numberOfDetachedContextsValue

    fun doesZapGarbage(): Boolean = doesZapGarbageValue

    fun totalAllocatedBytes(): ULong = totalAllocatedBytesValue
}

/**
 * Per-space V8 heap statistics.
 */
class HeapSpaceStatistics(
    private val spaceNameValue: String,
    private val spaceSizeValue: ULong,
    private val spaceUsedSizeValue: ULong,
    private val spaceAvailableSizeValue: ULong,
    private val physicalSpaceSizeValue: ULong,
) {
    fun spaceName(): String = spaceNameValue

    fun spaceSize(): ULong = spaceSizeValue

    fun spaceUsedSize(): ULong = spaceUsedSizeValue

    fun spaceAvailableSize(): ULong = spaceAvailableSizeValue

    fun physicalSpaceSize(): ULong = physicalSpaceSizeValue
}

/**
 * Code and metadata statistics for a V8 isolate.
 */
class HeapCodeStatistics(
    private val codeAndMetadataSizeValue: ULong,
    private val bytecodeAndMetadataSizeValue: ULong,
    private val externalScriptSourceSizeValue: ULong,
    private val cpuProfilerMetadataSizeValue: ULong,
) {
    fun codeAndMetadataSize(): ULong = codeAndMetadataSizeValue

    fun bytecodeAndMetadataSize(): ULong = bytecodeAndMetadataSizeValue

    fun externalScriptSourceSize(): ULong = externalScriptSourceSizeValue

    fun cpuProfilerMetadataSize(): ULong = cpuProfilerMetadataSizeValue
}
