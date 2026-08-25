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

    /** Returns the identity hash for this value. */
    fun getHash(): UInt = hashCode().toUInt()
}
