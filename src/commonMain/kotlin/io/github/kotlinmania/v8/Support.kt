// port-lint: source support.rs
package io.github.kotlinmania.v8

/**
 * C-ABI representation of `v8::Maybe<bool>` (`support::MaybeBool`).
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
 * Two-pointer C-ABI tuple (`support::two_pointers_t`).
 */
data class TwoPointers(
    val first: Long,
    val second: Long,
)

/**
 * Three-pointer C-ABI tuple (`support::three_pointers_t`).
 */
data class ThreePointers(
    val first: Long,
    val second: Long,
    val third: Long,
)

/**
 * Memory pressure levels for `v8::Isolate::MemoryPressureNotification` (`v8::MemoryPressureLevel`).
 */
enum class MemoryPressureLevel(
    val code: UByte,
) {
    None(0u),
    Moderate(1u),
    Critical(2u),
}

/**
 * Queries the C support layer (`support.h` / `v8_wrapper.c`) for ABI layout sizes.
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
