// port-lint: source data.rs
package io.github.kotlinmania.v8

/**
 * An error returned when attempting an invalid cast or downcast on V8 data.
 */
sealed class DataError : kotlin.Exception() {
    data class BadType(
        val expected: String,
        val actual: String,
    ) : DataError() {
        override val message: String
            get() = "cannot cast $actual to $expected"
    }

    data class NoData(
        val expected: String,
    ) : DataError() {
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

    /** Formats a debug representation of this Data. */
    fun fmt(): String = toString()
}

/** An AccessorSignature specifies which receivers are valid parameters to an accessor callback. */
interface AccessorSignature : Data

/** A fixed-sized array with elements of type Data. */
interface FixedArray : Data

/** A request to load a module. */
interface ModuleRequest : Data

/** An array to hold Primitive values. */
interface PrimitiveArray : Data

/** A compiled JavaScript script. */
interface Script

/** A container type that holds relevant metadata for module loading. */
interface ScriptOrModule

/** A Signature specifies which receiver is valid for a function. */
interface Signature : Data

/** The superclass of templates and functions. */
interface Template : Data

/** A FunctionTemplate is used to create functions at runtime. */
interface FunctionTemplate : Template

/** An ObjectTemplate is used to create objects at runtime. */
interface ObjectTemplate : Template

/** A compiled JavaScript module, not yet bound to a context. */
interface UnboundModuleScript : Data

/** A compiled JavaScript script, not yet bound to a context. */
interface UnboundScript : Data

/** A JavaScript value that wraps a C++ void pointer. */
interface External : Value

/** A common base class for ArrayBufferView. */
interface ArrayBufferView : Value

/** A base class for typed arrays. */
interface TypedArray : ArrayBufferView

/** A BigInt64Array in JavaScript. */
interface BigInt64Array : TypedArray

/** A BigUint64Array in JavaScript. */
interface BigUint64Array : TypedArray

/** A Float16Array in JavaScript. */
interface Float16Array : TypedArray

/** A Float32Array in JavaScript. */
interface Float32Array : TypedArray

/** A Float64Array in JavaScript. */
interface Float64Array : TypedArray

/** An Int16Array in JavaScript. */
interface Int16Array : TypedArray

/** An Int32Array in JavaScript. */
interface Int32Array : TypedArray

/** An Int8Array in JavaScript. */
interface Int8Array : TypedArray

/** A Uint16Array in JavaScript. */
interface Uint16Array : TypedArray

/** A Uint32Array in JavaScript. */
interface Uint32Array : TypedArray

/** A Uint8Array in JavaScript. */
interface Uint8Array : TypedArray

/** A Uint8ClampedArray in JavaScript. */
interface Uint8ClampedArray : TypedArray

/** An object wrapper for BigInt. */
interface BigIntObject : Value

/** An object wrapper for Boolean. */
interface BooleanObject : Value

/** A JavaScript Date object. */
interface Date : Value

/** An object wrapper for Number. */
interface NumberObject : Value

/** A PromiseResolver in JavaScript. */
interface PromiseResolver : Data

/** An object wrapper for String. */
interface StringObject : Value

/** An object wrapper for Symbol. */
interface SymbolObject : Value

/** A WebAssembly memory object. */
interface WasmMemoryObject : Value

/** A WebAssembly module object. */
interface WasmModuleObject : Value

/** A JavaScript BigInt primitive. */
interface BigInt : Primitive

/** A JavaScript integer primitive. */
interface Integer : Primitive

/** A JavaScript 32-bit signed integer. */
interface Int32 : Integer

/** A JavaScript 32-bit unsigned integer. */
interface Uint32 : Integer
