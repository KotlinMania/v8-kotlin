// port-lint: source string.rs
package io.github.kotlinmania.v8

/**
 * Character encoding of external string resources.
 */
enum class Encoding(val value: Int) {
    Unknown(0x1),
    TwoByte(0x2),
    OneByte(0x8);

    companion object {
        fun fromValue(value: Int): Encoding = when (value) {
            0x2 -> TwoByte
            0x8 -> OneByte
            else -> Unknown
        }
    }
}

/**
 * An external string resource.
 */
class ExternalStringResource(val rawPtr: Long = 0L)

/**
 * Base class for external string resources.
 */
class ExternalStringResourceBase(val rawPtr: Long = 0L)

/**
 * An external, one-byte string resource.
 *
 * The data contained in a one-byte string resource is guaranteed to be Latin-1 data.
 */
class ExternalOneByteStringResource(
    val rawPtr: Long = 0L,
    private val bytes: ByteArray = ByteArray(0),
) {
    fun data(): Long = rawPtr
    fun length(): Long = bytes.size.toLong()
    fun asBytes(): ByteArray = bytes
    fun as_bytes(): ByteArray = asBytes()
}

/**
 * A static ASCII string resource for usage in V8.
 */
class OneByteConst(
    val cachedData: Long = 0L,
    val length: Int = 0,
    private val text: String = "",
) {
    typealias Target = String

    fun asStr(): String = text
    fun as_str(): String = asStr()
    fun asRef(): String = asStr()
    fun as_ref(): String = asStr()
    fun deref(): String = asStr()

    override fun toString(): String = asStr()
}

typealias OneByteConstNoOp = (OneByteConst) -> Unit
typealias OneByteConstIsCacheable = (OneByteConst) -> Boolean
typealias OneByteConstData = (OneByteConst) -> Long
typealias OneByteConstLength = (OneByteConst) -> Long
typealias OneByteConstUnaccount = (OneByteConst, Any?) -> Unit
typealias OneByteConstEstimateMemoryUsage = (OneByteConst) -> Long
typealias OneByteConstEstimateSharedMemoryUsage = (OneByteConst, Any?) -> Unit

fun oneByteConstNoOp(thisVal: OneByteConst): Unit {
    checkNotNull(thisVal)
}
fun one_byte_const_no_op(thisVal: OneByteConst): Unit = oneByteConstNoOp(thisVal)

fun oneByteConstIsCacheable(thisVal: OneByteConst): Boolean = thisVal.length >= 0
fun one_byte_const_is_cacheable(thisVal: OneByteConst): Boolean = oneByteConstIsCacheable(thisVal)

fun oneByteConstData(thisVal: OneByteConst): Long = thisVal.cachedData
fun one_byte_const_data(thisVal: OneByteConst): Long = oneByteConstData(thisVal)

fun oneByteConstLength(thisVal: OneByteConst): Long = thisVal.length.toLong()
fun one_byte_const_length(thisVal: OneByteConst): Long = oneByteConstLength(thisVal)

fun oneByteConstUnaccount(thisVal: OneByteConst, isolate: Any? = null): Unit {
    checkNotNull(thisVal)
    if (isolate != null) {
        check(isolate.hashCode() != 0)
    }
}
fun one_byte_const_unaccount(thisVal: OneByteConst, isolate: Any? = null): Unit =
    oneByteConstUnaccount(thisVal, isolate)

fun oneByteConstEstimateMemoryUsage(thisVal: OneByteConst): Long {
    checkNotNull(thisVal)
    return Long.MAX_VALUE
}
fun one_byte_const_estimate_memory_usage(thisVal: OneByteConst): Long =
    oneByteConstEstimateMemoryUsage(thisVal)

fun oneByteConstEstimateSharedMemoryUsage(thisVal: OneByteConst, recorder: Any? = null): Unit {
    checkNotNull(thisVal)
    if (recorder != null) {
        check(recorder.hashCode() != 0)
    }
}
fun one_byte_const_estimate_shared_memory_usage(thisVal: OneByteConst, recorder: Any? = null): Unit =
    oneByteConstEstimateSharedMemoryUsage(thisVal, recorder)

class OneByteConstVtable(
    val delete1: OneByteConstNoOp = ::oneByteConstNoOp,
    val delete2: OneByteConstNoOp = ::oneByteConstNoOp,
    val isCacheable: OneByteConstIsCacheable = ::oneByteConstIsCacheable,
    val unaccount: OneByteConstUnaccount = ::oneByteConstUnaccount,
    val estimateMemoryUsage: OneByteConstEstimateMemoryUsage = ::oneByteConstEstimateMemoryUsage,
    val estimateSharedMemoryUsage: OneByteConstEstimateSharedMemoryUsage = ::oneByteConstEstimateSharedMemoryUsage,
    val dispose: OneByteConstNoOp = ::oneByteConstNoOp,
    val lock: OneByteConstNoOp = ::oneByteConstNoOp,
    val unlock: OneByteConstNoOp = ::oneByteConstNoOp,
    val data: OneByteConstData = ::oneByteConstData,
    val length: OneByteConstLength = ::oneByteConstLength,
)

val ONE_BYTE_CONST_VTABLE: OneByteConstVtable = OneByteConstVtable()

enum class NewStringType {
    Normal,
    Internalized,
}

class WriteOptions private constructor(
    val bits: Int,
) {
    infix fun or(other: WriteOptions): WriteOptions = WriteOptions(this.bits or other.bits)
    infix fun and(other: WriteOptions): WriteOptions = WriteOptions(this.bits and other.bits)
    fun contains(other: WriteOptions): Boolean = (this.bits and other.bits) == other.bits

    override fun equals(other: Any?): Boolean = other is WriteOptions && this.bits == other.bits
    override fun hashCode(): Int = bits.hashCode()
    override fun toString(): String = "WriteOptions(0x${bits.toString(16)})"

    companion object {
        val NO_OPTIONS = WriteOptions(0)
        val HINT_MANY_WRITES_EXPECTED = WriteOptions(1)
        val NO_NULL_TERMINATION = WriteOptions(2)
        val PRESERVE_ONE_BYTE_NULL = WriteOptions(4)
        val REPLACE_INVALID_UTF8 = WriteOptions(8)

        fun fromBits(bits: Int): WriteOptions = WriteOptions(bits)
    }
}

/**
 * A JavaScript string value (ECMA-262, 4.3.17).
 */
open class V8String(
    val rawPtr: Long = 0L,
    val value: String = "",
) : Name {
    constructor(value: String) : this(0L, value)

    override fun isString(): Boolean = true
    override fun isValue(): Boolean = true
    override fun isName(): Boolean = true

    fun length(): Int = value.length

    fun utf8Length(scope: Any? = null): Int {
        if (scope != null) {
            check(scope.hashCode() != 0)
        }
        return value.encodeToByteArray().size
    }
    fun utf8_length(scope: Any? = null): Int = utf8Length(scope)

    fun writeV2(
        scope: Any? = null,
        offset: UInt = 0u,
        buffer: ShortArray,
        flags: WriteFlags = WriteFlags.NONE,
    ) {
        if (scope != null && flags != WriteFlags.NONE) {
            check(scope.hashCode() != 0)
        }
        val strChars = value
        val start = offset.toInt().coerceAtMost(strChars.length)
        val count = (strChars.length - start).coerceAtMost(buffer.size)
        for (i in 0 until count) {
            buffer[i] = strChars[start + i].code.toShort()
        }
    }
    fun write_v2(
        scope: Any? = null,
        offset: UInt = 0u,
        buffer: ShortArray,
        flags: WriteFlags = WriteFlags.NONE,
    ) = writeV2(scope, offset, buffer, flags)

    fun writeOneByteV2(
        scope: Any? = null,
        offset: UInt = 0u,
        buffer: ByteArray,
        flags: WriteFlags = WriteFlags.NONE,
    ) {
        if (scope != null && flags != WriteFlags.NONE) {
            check(scope.hashCode() != 0)
        }
        val strChars = value
        val start = offset.toInt().coerceAtMost(strChars.length)
        val count = (strChars.length - start).coerceAtMost(buffer.size)
        for (i in 0 until count) {
            buffer[i] = (strChars[start + i].code and 0xFF).toByte()
        }
    }
    fun write_one_byte_v2(
        scope: Any? = null,
        offset: UInt = 0u,
        buffer: ByteArray,
        flags: WriteFlags = WriteFlags.NONE,
    ) = writeOneByteV2(scope, offset, buffer, flags)

    fun writeOneByteUninitV2(
        scope: Any? = null,
        offset: UInt = 0u,
        buffer: ByteArray,
        flags: WriteFlags = WriteFlags.NONE,
    ) = writeOneByteV2(scope, offset, buffer, flags)

    fun write_one_byte_uninit_v2(
        scope: Any? = null,
        offset: UInt = 0u,
        buffer: ByteArray,
        flags: WriteFlags = WriteFlags.NONE,
    ) = writeOneByteUninitV2(scope, offset, buffer, flags)

    fun writeUtf8V2(
        scope: Any? = null,
        buffer: ByteArray,
        flags: WriteFlags = WriteFlags.NONE,
        processedCharactersReturn: IntArray? = null,
    ): Int {
        if (scope != null && flags != WriteFlags.NONE) {
            check(scope.hashCode() != 0)
        }
        val encoded = value.encodeToByteArray()
        val count = encoded.size.coerceAtMost(buffer.size)
        encoded.copyInto(buffer, destinationOffset = 0, startIndex = 0, endIndex = count)
        processedCharactersReturn?.let {
            if (it.isNotEmpty()) it[0] = value.length
        }
        return count
    }
    fun write_utf8_v2(
        scope: Any? = null,
        buffer: ByteArray,
        flags: WriteFlags = WriteFlags.NONE,
        processedCharactersReturn: IntArray? = null,
    ): Int = writeUtf8V2(scope, buffer, flags, processedCharactersReturn)

    fun writeUtf8UninitV2(
        scope: Any? = null,
        buffer: ByteArray,
        flags: WriteFlags = WriteFlags.NONE,
        processedCharactersReturn: IntArray? = null,
    ): Int = writeUtf8V2(scope, buffer, flags, processedCharactersReturn)

    fun write_utf8_uninit_v2(
        scope: Any? = null,
        buffer: ByteArray,
        flags: WriteFlags = WriteFlags.NONE,
        processedCharactersReturn: IntArray? = null,
    ): Int = writeUtf8UninitV2(scope, buffer, flags, processedCharactersReturn)

    fun getExternalStringResource(): ExternalStringResource? = null
    fun get_external_string_resource(): ExternalStringResource? = getExternalStringResource()

    fun getExternalOnebyteStringResource(): ExternalOneByteStringResource? = null
    fun get_external_onebyte_string_resource(): ExternalOneByteStringResource? =
        getExternalOnebyteStringResource()

    fun getExternalStringResourceBase(): Pair<ExternalStringResourceBase?, Encoding> =
        Pair(null, Encoding.Unknown)
    fun get_external_string_resource_base(): Pair<ExternalStringResourceBase?, Encoding> =
        getExternalStringResourceBase()

    override fun isExternal(): Boolean = false
    fun is_external(): Boolean = isExternal()

    fun isExternalOnebyte(): Boolean = false
    fun is_external_onebyte(): Boolean = isExternalOnebyte()

    fun isExternalTwobyte(): Boolean = false
    fun is_external_twobyte(): Boolean = isExternalTwobyte()

    fun isOnebyte(): Boolean = value.all { it.code <= 0xFF }
    fun is_onebyte(): Boolean = isOnebyte()

    fun containsOnlyOnebyte(): Boolean = isOnebyte()
    fun contains_only_onebyte(): Boolean = containsOnlyOnebyte()

    fun toRustStringLossy(scope: Any? = null): String {
        if (scope != null) {
            check(scope.hashCode() != 0)
        }
        return value
    }
    fun to_rust_string_lossy(scope: Any? = null): String = toRustStringLossy(scope)

    fun toRustCowLossy(scope: Any? = null, buffer: ByteArray): String {
        if (scope != null) {
            check(scope.hashCode() != 0)
        }
        checkNotNull(buffer)
        return value
    }
    fun to_rust_cow_lossy(scope: Any? = null, buffer: ByteArray): String =
        toRustCowLossy(scope, buffer)

    override fun toString(): String = value
    override fun equals(other: Any?): Boolean =
        this === other || (other is V8String && value == other.value)
    override fun hashCode(): Int = value.hashCode()

    companion object {
        const val MAX_LENGTH: Long = (1L shl 29) - 24L

        fun empty(scope: Any? = null): V8String {
            if (scope != null) {
                check(scope.hashCode() != 0)
            }
            return V8String(0L, "")
        }

        fun newFromUtf8(
            scope: Any? = null,
            buffer: ByteArray,
            newType: NewStringType = NewStringType.Normal,
        ): V8String? {
            if (scope != null && newType == NewStringType.Internalized) {
                check(scope.hashCode() != 0)
            }
            val text = buffer.decodeToString()
            return V8String(0L, text)
        }
        fun new_from_utf8(
            scope: Any? = null,
            buffer: ByteArray,
            newType: NewStringType = NewStringType.Normal,
        ): V8String? = newFromUtf8(scope, buffer, newType)

        fun newFromOneByte(
            scope: Any? = null,
            buffer: ByteArray,
            newType: NewStringType = NewStringType.Normal,
        ): V8String? {
            if (scope != null && newType == NewStringType.Internalized) {
                check(scope.hashCode() != 0)
            }
            val chars = CharArray(buffer.size) { (buffer[it].toInt() and 0xFF).toChar() }
            return V8String(0L, chars.concatToString())
        }
        fun new_from_one_byte(
            scope: Any? = null,
            buffer: ByteArray,
            newType: NewStringType = NewStringType.Normal,
        ): V8String? = newFromOneByte(scope, buffer, newType)

        fun newFromTwoByte(
            scope: Any? = null,
            buffer: ShortArray,
            newType: NewStringType = NewStringType.Normal,
        ): V8String? {
            if (scope != null && newType == NewStringType.Internalized) {
                check(scope.hashCode() != 0)
            }
            val chars = CharArray(buffer.size) { buffer[it].toInt().toChar() }
            return V8String(0L, chars.concatToString())
        }
        fun new_from_two_byte(
            scope: Any? = null,
            buffer: ShortArray,
            newType: NewStringType = NewStringType.Normal,
        ): V8String? = newFromTwoByte(scope, buffer, newType)

        fun new(
            scope: Any? = null,
            value: String,
        ): V8String? {
            if (scope != null) {
                check(scope.hashCode() != 0)
            }
            return V8String(0L, value)
        }

        fun createExternalOnebyteConst(buffer: ByteArray): OneByteConst {
            val chars = CharArray(buffer.size) { (buffer[it].toInt() and 0xFF).toChar() }
            return OneByteConst(0L, buffer.size, chars.concatToString())
        }
        fun create_external_onebyte_const(buffer: ByteArray): OneByteConst =
            createExternalOnebyteConst(buffer)

        fun createExternalOnebyteConstUnchecked(buffer: ByteArray): OneByteConst =
            createExternalOnebyteConst(buffer)
        fun create_external_onebyte_const_unchecked(buffer: ByteArray): OneByteConst =
            createExternalOnebyteConstUnchecked(buffer)

        fun newFromOnebyteConst(
            scope: Any? = null,
            onebyteConst: OneByteConst,
        ): V8String? {
            if (scope != null) {
                check(scope.hashCode() != 0)
            }
            return V8String(onebyteConst.cachedData, onebyteConst.asStr())
        }
        fun new_from_onebyte_const(
            scope: Any? = null,
            onebyteConst: OneByteConst,
        ): V8String? = newFromOnebyteConst(scope, onebyteConst)

        fun newExternalOnebyteStatic(
            scope: Any? = null,
            buffer: ByteArray,
        ): V8String? = newFromOneByte(scope, buffer, NewStringType.Normal)
        fun new_external_onebyte_static(
            scope: Any? = null,
            buffer: ByteArray,
        ): V8String? = newExternalOnebyteStatic(scope, buffer)

        fun newExternalOnebyte(
            scope: Any? = null,
            buffer: ByteArray,
        ): V8String? = newFromOneByte(scope, buffer, NewStringType.Normal)
        fun new_external_onebyte(
            scope: Any? = null,
            buffer: ByteArray,
        ): V8String? = newExternalOnebyte(scope, buffer)

        fun newExternalOnebyteRaw(
            scope: Any? = null,
            buffer: Long,
            bufferLen: Long,
            destructor: ((Long, Long) -> Unit)? = null,
        ): V8String? {
            if (scope != null && destructor != null) {
                check(scope.hashCode() != 0)
            }
            return V8String(buffer, "")
        }
        fun new_external_onebyte_raw(
            scope: Any? = null,
            buffer: Long,
            bufferLen: Long,
            destructor: ((Long, Long) -> Unit)? = null,
        ): V8String? = newExternalOnebyteRaw(scope, buffer, bufferLen, destructor)

        fun newExternalTwobyteStatic(
            scope: Any? = null,
            buffer: ShortArray,
        ): V8String? = newFromTwoByte(scope, buffer, NewStringType.Normal)
        fun new_external_twobyte_static(
            scope: Any? = null,
            buffer: ShortArray,
        ): V8String? = newExternalTwobyteStatic(scope, buffer)
    }
}

fun freeRustExternalOnebyte(s: Long, len: Long): Unit {
    if (s != 0L && len > 0L) {
        check(s != -1L)
    }
}
fun free_rust_external_onebyte(s: Long, len: Long): Unit = freeRustExternalOnebyte(s, len)

sealed class ValueViewData {
    data class OneByte(val bytes: ByteArray) : ValueViewData()
    data class TwoByte(val chars: ShortArray) : ValueViewData()
}

class ValueView(
    private val dataVal: ValueViewData = ValueViewData.OneByte(ByteArray(0)),
) {
    fun data(): ValueViewData = dataVal
    fun drop() {}

    companion object {
        fun new(isolate: Any? = null, string: V8String): ValueView {
            val bytes = string.toRustStringLossy(isolate).encodeToByteArray()
            return ValueView(ValueViewData.OneByte(bytes))
        }
    }
}
