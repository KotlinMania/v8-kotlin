// port-lint: source value.rs
package io.github.kotlinmania.v8

/**
 * The superclass of all JavaScript values and objects.
 */
interface Value : Data {
    override fun isValue(): Boolean = true

    /** Returns true if this value is the undefined value. */
    fun isUndefined(): Boolean = false

    /** Returns true if this value is the null value. */
    fun isNull(): Boolean = false

    /** Returns true if this value is either the null or the undefined value. */
    fun isNullOrUndefined(): Boolean = isNull() || isUndefined()

    /** Returns true if this value is true. */
    fun isTrue(): Boolean = false

    /** Returns true if this value is false. */
    fun isFalse(): Boolean = false

    /** Returns true if this value is a symbol or a string. */
    override fun isName(): Boolean = false

    /** Returns true if this value is an instance of the String type. */
    override fun isString(): Boolean = false

    /** Returns true if this value is a symbol. */
    override fun isSymbol(): Boolean = false

    /** Returns true if this value is a function. */
    fun isFunction(): Boolean = false

    /** Returns true if this value is an array. */
    fun isArray(): Boolean = false

    /** Returns true if this value is an object. */
    fun isObject(): Boolean = false

    /** Returns true if this value is a BigInt. */
    override fun isBigInt(): Boolean = false

    /** Returns true if this value is a boolean. */
    override fun isBoolean(): Boolean = false

    /** Returns true if this value is a number. */
    override fun isNumber(): Boolean = false

    /** Returns true if this value is an External object. */
    fun isExternal(): Boolean = false

    /** Returns true if this value is a 32-bit signed integer. */
    fun isInt32(): Boolean = false

    /** Returns true if this value is a 32-bit unsigned integer. */
    fun isUint32(): Boolean = false

    /** Returns true if this value is a Date. */
    fun isDate(): Boolean = false

    /** Returns true if this value is an Arguments object. */
    fun isArgumentsObject(): Boolean = false

    /** Returns true if this value is a BigInt object. */
    fun isBigIntObject(): Boolean = false

    /** Returns true if this value is a Boolean object. */
    fun isBooleanObject(): Boolean = false

    /** Returns true if this value is a Number object. */
    fun isNumberObject(): Boolean = false

    /** Returns true if this value is a String object. */
    fun isStringObject(): Boolean = false

    /** Returns true if this value is a Symbol object. */
    fun isSymbolObject(): Boolean = false

    /** Returns true if this value is a NativeError. */
    fun isNativeError(): Boolean = false

    /** Returns true if this value is a RegExp. */
    fun isRegExp(): Boolean = false

    /** Returns true if this value is an AsyncFunction. */
    fun isAsyncFunction(): Boolean = false

    /** Returns true if this value is a GeneratorFunction. */
    fun isGeneratorFunction(): Boolean = false

    /** Returns true if this value is a Promise. */
    fun isPromise(): Boolean = false

    /** Returns true if this value is a Map. */
    fun isMap(): Boolean = false

    /** Returns true if this value is a Set. */
    fun isSet(): Boolean = false

    /** Returns true if this value is a MapIterator. */
    fun isMapIterator(): Boolean = false

    /** Returns true if this value is a SetIterator. */
    fun isSetIterator(): Boolean = false

    /** Returns true if this value is a GeneratorObject. */
    fun isGeneratorObject(): Boolean = false

    /** Returns true if this value is a WeakMap. */
    fun isWeakMap(): Boolean = false

    /** Returns true if this value is a WeakSet. */
    fun isWeakSet(): Boolean = false

    /** Returns true if this value is an ArrayBuffer. */
    fun isArrayBuffer(): Boolean = false

    /** Returns true if this value is an ArrayBufferView. */
    fun isArrayBufferView(): Boolean = false

    /** Returns true if this value is a TypedArray. */
    fun isTypedArray(): Boolean = false

    /** Returns true if this value is a Uint8Array. */
    fun isUint8Array(): Boolean = false

    /** Returns true if this value is a Uint8ClampedArray. */
    fun isUint8ClampedArray(): Boolean = false

    /** Returns true if this value is an Int8Array. */
    fun isInt8Array(): Boolean = false

    /** Returns true if this value is a Uint16Array. */
    fun isUint16Array(): Boolean = false

    /** Returns true if this value is an Int16Array. */
    fun isInt16Array(): Boolean = false

    /** Returns true if this value is a Uint32Array. */
    fun isUint32Array(): Boolean = false

    /** Returns true if this value is an Int32Array. */
    fun isInt32Array(): Boolean = false

    /** Returns true if this value is a Float16Array. */
    fun isFloat16Array(): Boolean = false

    /** Returns true if this value is a Float32Array. */
    fun isFloat32Array(): Boolean = false

    /** Returns true if this value is a Float64Array. */
    fun isFloat64Array(): Boolean = false

    /** Returns true if this value is a BigInt64Array. */
    fun isBigInt64Array(): Boolean = false

    /** Returns true if this value is a BigUint64Array. */
    fun isBigUint64Array(): Boolean = false

    /** Returns true if this value is a DataView. */
    fun isDataView(): Boolean = false

    /** Returns true if this value is a SharedArrayBuffer. */
    fun isSharedArrayBuffer(): Boolean = false

    /** Returns true if this value is a Proxy. */
    fun isProxy(): Boolean = false

    /** Returns true if this value is a WasmMemoryObject. */
    fun isWasmMemoryObject(): Boolean = false

    /** Returns true if this value is a WasmModuleObject. */
    fun isWasmModuleObject(): Boolean = false

    /** Returns true if this value is a ModuleNamespaceObject. */
    fun isModuleNamespaceObject(): Boolean = false

    /** Returns true if this value strictly equals [that]. */
    fun strictEquals(that: Value): Boolean = this == that

    /** Returns true if this value has the same value as [that]. */
    fun sameValue(that: Value): Boolean = this == that

    /**
     * Implements the abstract operation `SameValueZero`.
     * Distinguishes NaN == NaN and considers -0 equal to 0.
     */
    fun sameValueZero(that: Value): Boolean = strictEquals(that) || sameValue(that)

    /** Returns the identity hash for this value. */
    fun getHash(): UInt = hashCode().toUInt()

    /** Returns the typeof result for this value. */
    fun typeOf(): String =
        when {
            isUndefined() -> "undefined"
            isNull() -> "object"
            isBoolean() -> "boolean"
            isNumber() -> "number"
            isString() -> "string"
            isSymbol() -> "symbol"
            isBigInt() -> "bigint"
            isFunction() -> "function"
            else -> "object"
        }

    /** Utility method returning a human-readable representation of the underlying value type. */
    fun typeRepr(): String =
        when {
            isModuleNamespaceObject() -> "Module"
            isWasmModuleObject() -> "WASM module"
            isWasmMemoryObject() -> "WASM memory object"
            isProxy() -> "Proxy"
            isSharedArrayBuffer() -> "SharedArrayBuffer"
            isDataView() -> "DataView"
            isBigUint64Array() -> "BigUint64Array"
            isBigInt64Array() -> "BigInt64Array"
            isFloat64Array() -> "Float64Array"
            isFloat32Array() -> "Float32Array"
            isInt32Array() -> "Int32Array"
            isUint32Array() -> "Uint32Array"
            isInt16Array() -> "Int16Array"
            isUint16Array() -> "Uint16Array"
            isInt8Array() -> "Int8Array"
            isUint8ClampedArray() -> "Uint8ClampedArray"
            isUint8Array() -> "Uint8Array"
            isTypedArray() -> "TypedArray"
            isArrayBufferView() -> "ArrayBufferView"
            isArrayBuffer() -> "ArrayBuffer"
            isWeakSet() -> "WeakSet"
            isWeakMap() -> "WeakMap"
            isSetIterator() -> "Set Iterator"
            isMapIterator() -> "Map Iterator"
            isSet() -> "Set"
            isMap() -> "Map"
            isPromise() -> "Promise"
            isGeneratorFunction() -> "Generator function"
            isAsyncFunction() -> "Async function"
            isRegExp() -> "RegExp"
            isDate() -> "Date"
            isNumber() -> "Number"
            isBoolean() -> "Boolean"
            isBigInt() -> "bigint"
            isArray() -> "array"
            isFunction() -> "function"
            isSymbol() -> "symbol"
            isString() -> "string"
            isNull() -> "null"
            isUndefined() -> "undefined"
            else -> "unknown"
        }

    /** Convenience method returning string representation of the value. */
    fun toRustStringLossy(): String = toString()

    /** Number value as double if convertible, otherwise null. */
    fun numberValue(): Double? = null

    /** Integer value as 64-bit int if convertible, otherwise null. */
    fun integerValue(): Long? = null

    /** 32-bit unsigned int value if convertible, otherwise null. */
    fun uint32Value(): UInt? = null

    /** 32-bit signed int value if convertible, otherwise null. */
    fun int32Value(): Int? = null

    /** Boolean value of this value. */
    fun booleanValue(): Boolean = false

    /** Checks if this value is an instance of the given constructor [objectRef]. */
    fun instanceOf(objectRef: Any): Boolean = false

    /** Converts this value to a 64-bit signed integer if possible. */
    fun toInteger(): Long? = integerValue()

    /** Converts this value to a 32-bit unsigned integer if possible. */
    fun toUint32(): UInt? = uint32Value()

    /** Converts this value to a 32-bit signed integer if possible. */
    fun toInt32(): Int? = int32Value()

    /** Converts this value to a boolean. */
    fun toBoolean(): Boolean = booleanValue()

    /** Converts this value to a BigInt if possible. */
    fun toBigInt(): Any? = null

    /** Converts this value to a Number (double) if possible. */
    fun toNumber(): Double? = numberValue()

    /** Converts this value to an Object if possible. */
    fun toObject(): Any? = null

    /** Returns this value as a detail string. */
    fun toDetailString(): String = toString()
}
