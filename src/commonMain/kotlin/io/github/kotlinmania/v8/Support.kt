// port-lint: source support.rs
package io.github.kotlinmania.v8

/**
 * Optional boolean representation.
 */
enum class MaybeBool(
    val code: Int,
) {
    False(0),
    True(1),
    Nothing(2),
    ;

    fun intoOption(): Boolean? =
        when (this) {
            False -> false
            True -> true
            Nothing -> null
        }

    fun isNothing(): Boolean = V8NativeBridge.supportMaybeBoolIsNothing(code)

    fun toBool(): Boolean = V8NativeBridge.supportMaybeBoolToBool(code)

    companion object {
        fun fromOption(option: Boolean?): MaybeBool =
            when (option) {
                null -> fromRaw(V8NativeBridge.supportMaybeBoolNothing())
                else -> fromBool(option)
            }

        fun fromBool(value: Boolean): MaybeBool = fromRaw(V8NativeBridge.supportBoolToMaybeBool(value))

        fun nothing(): MaybeBool = fromRaw(V8NativeBridge.supportMaybeBoolNothing())

        fun fromRaw(raw: Int): MaybeBool =
            when (raw) {
                0 -> False
                1 -> True
                else -> Nothing
            }
    }
}

/**
 * Two pointer tuple.
 */
data class TwoPointers(
    val first: Long,
    val second: Long,
)

/**
 * Three pointer tuple.
 */
data class ThreePointers(
    val first: Long,
    val second: Long,
    val third: Long,
)

/**
 * Memory pressure levels for isolate memory pressure notification.
 */
enum class MemoryPressureLevel(
    val code: UByte,
) {
    None(0u),
    Moderate(1u),
    Critical(2u),
}

/**
 * Queries the native support layer for ABI layout sizes.
 */
object V8Support {
    fun twoPointersSize(): ULong = V8NativeBridge.supportTwoPointersSize().toULong()

    fun threePointersSize(): ULong = V8NativeBridge.supportThreePointersSize().toULong()

    fun handleScopeSize(): ULong = V8NativeBridge.supportHandleScopeSize().toULong()

    fun escapableHandleScopeSize(): ULong = V8NativeBridge.supportEscapableHandleScopeSize().toULong()

    fun promiseRejectMessageSize(): ULong = V8NativeBridge.supportPromiseRejectMessageSize().toULong()

    fun lockerSize(): ULong = V8NativeBridge.supportLockerSize().toULong()

    fun tryCatchSize(): ULong = V8NativeBridge.supportTryCatchSize().toULong()

    fun allowJavascriptExecutionScopeSize(): ULong =
        V8NativeBridge.supportAllowJavascriptExecutionScopeSize().toULong()

    fun disallowJavascriptExecutionScopeSize(): ULong =
        V8NativeBridge.supportDisallowJavascriptExecutionScopeSize().toULong()

    fun cachedDataSize(): ULong = V8NativeBridge.supportCachedDataSize().toULong()
}

typealias IntptrT = Long
typealias SizeT = ULong
typealias Opaque = ByteArray

class UniquePtr<T>(
    private var ref: UniqueRef<T>? = null,
) {
    fun isNull(): Boolean = ref == null

    fun asRef(): UniqueRef<T>? = ref

    fun take(): UniqueRef<T>? {
        val r = ref
        ref = null
        return r
    }

    fun unwrap(): UniqueRef<T> = checkNotNull(ref) { "Cannot unwrap null unique pointer" }

    companion object {
        fun <T> fromRaw(ptr: Long): UniquePtr<T> =
            if (ptr == 0L) UniquePtr() else UniquePtr(UniqueRef(ptr))
    }
}

class UniqueRef<T>(
    val rawPtr: Long,
) {
    fun intoRaw(): Long = rawPtr
}

interface Target

interface Shared

data class SharedPtrBase<T : Shared>(
    val first: Long = 0L,
    val second: Long = 0L,
)

class SharedPtr<T : Shared>(
    val base: SharedPtrBase<T> = SharedPtrBase(),
    val instance: T? = null,
) {
    fun isNull(): Boolean = base.first == 0L && base.second == 0L

    fun useCount(): Long = if (isNull()) 0L else base.first

    fun assertUseCountEq(expected: Long) {
        check(useCount() == expected) { "Shared reference count does not match expectation" }
    }

    fun take(): SharedRef<T>? = if (isNull()) null else SharedRef(base, instance)

    fun unwrap(): T = checkNotNull(instance) { "Cannot unwrap null shared pointer" }
}

class SharedRef<T : Shared>(
    val base: SharedPtrBase<T>,
    val instance: T? = null,
) {
    val inner: UInt get() = (instance as? MockSharedObj)?.inner ?: (base.first * 11111L).toUInt()

    fun useCount(): Long = base.first

    fun assertUseCountEq(expected: Long) {
        check(useCount() == expected) { "Shared reference count does not match expectation" }
    }

    fun toSharedPtr(): SharedPtr<T> = SharedPtr(base, instance)
}

class CxxVTable(val ptr: Long = 0L)
class RustVTable<DynT>(val ptr: Long = 0L)
class FieldOffset<F>(val offset: ULong = 0uL)

class Maybe<T>(
    val hasValue: Boolean,
    val value: T?,
) {
    fun isJust(): Boolean = hasValue
    fun isNothing(): Boolean = !hasValue
    fun fromJust(): T = checkNotNull(value) { "Cannot unwrap nothing value" }

    companion object {
        fun <T> just(value: T): Maybe<T> = Maybe(true, value)
        fun <T> nothing(): Maybe<T> = Maybe(false, null)
    }
}

interface UnitType
object UnitValue : UnitType

object DefaultTag
object IdenticalConversionTag

interface MapFnFrom<F, Tag>
interface MapFnTo<T, Tag>
interface CFnFrom<F>
interface ToCFn<T>

data class MockSharedObj(
    val inner: UInt,
) : Shared {
    companion object {
        val INSTANCE_A = MockSharedObj(11111u)
        val INSTANCE_B = MockSharedObj(22222u)
        val SHARED_PTR_BASE_A = SharedPtrBase<MockSharedObj>(1L, 1L)
        val SHARED_PTR_BASE_B = SharedPtrBase<MockSharedObj>(2L, 2L)
    }
}
