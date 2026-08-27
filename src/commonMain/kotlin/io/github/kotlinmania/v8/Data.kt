// port-lint: source v8/src/data.rs
package io.github.kotlinmania.v8

/**
 * An error returned when attempting an invalid cast or downcast on V8 data.
 */
sealed class DataError : kotlin.Exception() {
    data class BadType(val expected: String, val actual: String) : DataError() {
        override val message: String
            get() = "cannot cast $actual to $expected"
    }

    data class NoData(val expected: String) : DataError() {
        override val message: String
            get() = "expected data of type $expected but found none"
    }

    companion object {
        fun badType(expected: String, actual: String): DataError = BadType(expected, actual)
        fun noData(expected: String): DataError = NoData(expected)
    }
}

/**
 * The superclass of objects and values in V8.
 */
interface Data {
    /** Returns true if this data is a `BigInt`. */
    fun isBigInt(): Boolean = false

    /** Returns true if this data is a `Boolean`. */
    fun isBoolean(): Boolean = false

    /** Returns true if this data is a `Context`. */
    fun isContext(): Boolean = false

    /** Returns true if this data is a `FixedArray`. */
    fun isFixedArray(): Boolean = false

    /** Returns true if this data is a `FunctionTemplate`. */
    fun isFunctionTemplate(): Boolean = false

    /** Returns true if this data is a `Module`. */
    fun isModule(): Boolean = false

    /** Returns true if this data is a `ModuleRequest`. */
    fun isModuleRequest(): Boolean = false

    /** Returns true if this data is a `Name`. */
    fun isName(): Boolean = false

    /** Returns true if this data is a `Number`. */
    fun isNumber(): Boolean = false

    /** Returns true if this data is an `ObjectTemplate`. */
    fun isObjectTemplate(): Boolean = false

    /** Returns true if this data is a `Primitive`. */
    fun isPrimitive(): Boolean = false

    /** Returns true if this data is a `Private`. */
    fun isPrivate(): Boolean = false

    /** Returns true if this data is a `String`. */
    fun isString(): Boolean = false

    /** Returns true if this data is a `Symbol`. */
    fun isSymbol(): Boolean = false

    /** Returns true if this data is a `Value`. */
    fun isValue(): Boolean = false
}
