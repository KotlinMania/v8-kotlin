// port-lint: source function.rs
package io.github.kotlinmania.v8

enum class ConstructorBehavior {
    Throw,
    Allow,
}

enum class SideEffectType {
    HasSideEffect,
    HasNoSideEffect,
    HasSideEffectToReceiver,
}

class RawReturnValue(var ptr: Long = 0L)

class NumberValue(val num: Double) : Primitive {
    override fun isNumber(): Boolean = true
    override fun numberValue(): Double = num
    override fun integerValue(): Long = num.toLong()
    override fun int32Value(): Int = num.toInt()
    override fun uint32Value(): UInt = num.toUInt()
    override fun toString(): String = num.toString()
}

class ReturnValue<T>(
    private val raw: RawReturnValue = RawReturnValue(),
) {
    private var valueVal: Value? = null

    fun setBool(value: Boolean) {
        valueVal = V8Boolean.new(value)
    }
    fun set_bool(value: Boolean) = setBool(value)

    fun set(value: Value) {
        valueVal = value
    }

    fun setInt32(value: Int) {
        valueVal = NumberValue(value.toDouble())
    }
    fun set_int32(value: Int) = setInt32(value)

    fun setUint32(value: UInt) {
        valueVal = NumberValue(value.toDouble())
    }
    fun set_uint32(value: UInt) = setUint32(value)

    fun setDouble(value: Double) {
        valueVal = NumberValue(value)
    }
    fun set_double(value: Double) = setDouble(value)

    fun setNull() {
        valueVal = nullValue()
    }
    fun set_null() = setNull()

    fun setUndefined() {
        valueVal = undefinedValue()
    }
    fun set_undefined() = setUndefined()

    fun setEmptyString() {
        valueVal = V8String(0L, "")
    }
    fun set_empty_string() = setEmptyString()

    fun get(scope: Any? = null): Value {
        if (scope != null) {
            check(scope.hashCode() != 0)
        }
        return valueVal ?: undefinedValue()
    }

    companion object {
        fun <T> fromPropertyCallbackInfo(info: PropertyCallbackInfo<T>): ReturnValue<T> {
            checkNotNull(info)
            return ReturnValue()
        }
        fun <T> from_property_callback_info(info: PropertyCallbackInfo<T>): ReturnValue<T> =
            fromPropertyCallbackInfo(info)

        fun fromFunctionCallbackInfo(info: FunctionCallbackInfo): ReturnValue<Value> {
            checkNotNull(info)
            return ReturnValue()
        }
        fun from_function_callback_info(info: FunctionCallbackInfo): ReturnValue<Value> =
            fromFunctionCallbackInfo(info)
    }
}

class FunctionCallbackInfo(
    private val isolatePtr: Long = 0L,
    private val thisObj: Object = Object(),
    private val dataVal: Value = undefinedValue(),
    private val args: List<Value> = emptyList(),
    private val isConstruct: Boolean = false,
) {
    fun getIsolatePtr(): Long = isolatePtr
    fun get_isolate_ptr(): Long = getIsolatePtr()

    fun newTarget(): Value = if (isConstruct) thisObj else undefinedValue()
    fun new_target(): Value = newTarget()

    fun thisValue(): Object = thisObj
    fun `this`(): Object = thisObj

    fun isConstructCall(): Boolean = isConstruct
    fun is_construct_call(): Boolean = isConstructCall()

    fun data(): Value = dataVal

    fun length(): Int = args.size

    fun get(index: Int): Value = if (index in args.indices) args[index] else undefinedValue()
}

class RawPropertyCallbackInfo(var ptr: Long = 0L)

class PropertyCallbackInfo<T>(
    val raw: RawPropertyCallbackInfo = RawPropertyCallbackInfo(),
    private val isolatePtr: Long = 0L,
    private val holderObj: Object = Object(),
    private val dataVal: Value = undefinedValue(),
    private val shouldThrow: Boolean = false,
) {
    fun getIsolatePtr(): Long = isolatePtr
    fun get_isolate_ptr(): Long = getIsolatePtr()
    fun holder(): Object = holderObj
    fun data(): Value = dataVal
    fun shouldThrowOnError(): Boolean = shouldThrow
}

class FunctionCallbackArguments(
    private val info: FunctionCallbackInfo,
) {
    fun getIsolate(): IsolateHandle = IsolateHandle(info.getIsolatePtr())
    fun get_isolate(): IsolateHandle = getIsolate()

    fun newTarget(): Value = info.newTarget()
    fun new_target(): Value = newTarget()

    fun isConstructCall(): Boolean = info.isConstructCall()
    fun is_construct_call(): Boolean = isConstructCall()

    fun thisValue(): Object = info.thisValue()
    fun `this`(): Object = info.`this`()

    fun data(): Value = info.data()

    fun length(): Int = info.length()

    fun get(i: Int): Value = info.get(i)

    companion object {
        fun fromFunctionCallbackInfo(info: FunctionCallbackInfo): FunctionCallbackArguments =
            FunctionCallbackArguments(info)
        fun from_function_callback_info(info: FunctionCallbackInfo): FunctionCallbackArguments =
            fromFunctionCallbackInfo(info)
    }
}

class PropertyCallbackArguments(
    private val info: PropertyCallbackInfo<*>,
) {
    fun holder(): Object = info.holder()

    fun data(): Value = info.data()

    fun shouldThrowOnError(): Boolean = info.shouldThrowOnError()
    fun should_throw_on_error(): Boolean = shouldThrowOnError()

    companion object {
        fun fromPropertyCallbackInfo(info: PropertyCallbackInfo<*>): PropertyCallbackArguments =
            PropertyCallbackArguments(info)
        fun from_property_callback_info(info: PropertyCallbackInfo<*>): PropertyCallbackArguments =
            fromPropertyCallbackInfo(info)
    }
}

typealias FunctionCallback = (FunctionCallbackInfo) -> Unit
typealias NamedGetterCallbackForAccessor = (V8String, PropertyCallbackInfo<*>) -> Unit
typealias NamedGetterCallback = (V8String, PropertyCallbackInfo<*>) -> Unit
typealias NamedQueryCallback = (V8String, PropertyCallbackInfo<*>) -> Unit
typealias NamedSetterCallbackForAccessor = (V8String, Value, PropertyCallbackInfo<*>) -> Unit
typealias NamedSetterCallback = (V8String, Value, PropertyCallbackInfo<*>) -> Unit
typealias PropertyEnumeratorCallback = (PropertyCallbackInfo<*>) -> Unit
typealias NamedDefinerCallback = (V8String, PropertyDescriptor, PropertyCallbackInfo<*>) -> Unit
typealias NamedDeleterCallback = (V8String, PropertyCallbackInfo<*>) -> Unit
typealias IndexedGetterCallback = (UInt, PropertyCallbackInfo<*>) -> Unit
typealias IndexedQueryCallback = (UInt, PropertyCallbackInfo<*>) -> Unit
typealias IndexedSetterCallback = (UInt, Value, PropertyCallbackInfo<*>) -> Unit
typealias IndexedDefinerCallback = (UInt, PropertyDescriptor, PropertyCallbackInfo<*>) -> Unit
typealias IndexedDeleterCallback = (UInt, PropertyCallbackInfo<*>) -> Unit

class FunctionCallbackAdapter {
    fun mapping(): FunctionCallback = { _ -> }
}
class NamedGetterCallbackForAccessorAdapter {
    fun mapping(): NamedGetterCallbackForAccessor = { _, _ -> }
}
class NamedGetterCallbackAdapter {
    fun mapping(): NamedGetterCallback = { _, _ -> }
}
class NamedQueryCallbackAdapter {
    fun mapping(): NamedQueryCallback = { _, _ -> }
}
class NamedSetterCallbackForAccessorAdapter {
    fun mapping(): NamedSetterCallbackForAccessor = { _, _, _ -> }
}
class NamedSetterCallbackAdapter {
    fun mapping(): NamedSetterCallback = { _, _, _ -> }
}
class PropertyEnumeratorCallbackAdapter {
    fun mapping(): PropertyEnumeratorCallback = { _ -> }
}
class NamedDefinerCallbackAdapter {
    fun mapping(): NamedDefinerCallback = { _, _, _ -> }
}
class NamedDeleterCallbackAdapter {
    fun mapping(): NamedDeleterCallback = { _, _ -> }
}
class IndexedGetterCallbackAdapter {
    fun mapping(): IndexedGetterCallback = { _, _ -> }
}
class IndexedQueryCallbackAdapter {
    fun mapping(): IndexedQueryCallback = { _, _ -> }
}
class IndexedSetterCallbackAdapter {
    fun mapping(): IndexedSetterCallback = { _, _, _ -> }
}
class IndexedDefinerCallbackAdapter {
    fun mapping(): IndexedDefinerCallback = { _, _, _ -> }
}
class IndexedDeleterCallbackAdapter {
    fun mapping(): IndexedDeleterCallback = { _, _ -> }
}

class FunctionBuilder<T> internal constructor(
    private val callback: FunctionCallback,
    private var dataVal: Value? = null,
    private var signatureVal: Signature? = null,
    private var lengthVal: Int = 0,
    private var constructorBehaviorVal: ConstructorBehavior = ConstructorBehavior.Allow,
    private var sideEffectTypeVal: SideEffectType = SideEffectType.HasSideEffect,
) {
    fun data(data: Value): FunctionBuilder<T> {
        this.dataVal = data
        return this
    }

    fun length(length: Int): FunctionBuilder<T> {
        this.lengthVal = length
        return this
    }

    fun constructorBehavior(constructorBehavior: ConstructorBehavior): FunctionBuilder<T> {
        this.constructorBehaviorVal = constructorBehavior
        return this
    }
    fun constructor_behavior(constructorBehavior: ConstructorBehavior): FunctionBuilder<T> =
        constructorBehavior(constructorBehavior)

    fun sideEffectType(sideEffectType: SideEffectType): FunctionBuilder<T> {
        this.sideEffectTypeVal = sideEffectType
        return this
    }
    fun side_effect_type(sideEffectType: SideEffectType): FunctionBuilder<T> =
        sideEffectType(sideEffectType)

    fun build(scope: Any? = null): Function? {
        if (scope != null) {
            check(scope.hashCode() != 0)
        }
        return Function(0L, callback, lengthVal)
    }

    companion object {
        fun <T> new(callback: FunctionCallback): FunctionBuilder<T> =
            FunctionBuilder(callback)

        fun <T> newRaw(callback: FunctionCallback): FunctionBuilder<T> =
            FunctionBuilder(callback)
        fun <T> new_raw(callback: FunctionCallback): FunctionBuilder<T> =
            newRaw(callback)
    }
}

class ScriptOrigin(
    val resourceName: Value? = null,
    val lineOffset: Int = 0,
    val columnOffset: Int = 0,
)

class CachedData(
    val data: ByteArray = ByteArray(0),
)

open class Function internal constructor(
    rawPtr: Long = 0L,
    private val callback: FunctionCallback? = null,
    private val lengthVal: Int = 0,
) : Object(rawPtr) {
    override fun isFunction(): Boolean = true

    private var nameVal: V8String = V8String(0L, "")
    private var scriptIdVal: Int = 0

    fun call(
        scope: Any? = null,
        recv: Value,
        args: List<Value> = emptyList(),
    ): Value? {
        if (scope != null) {
            check(scope.hashCode() != 0)
        }
        if (callback != null) {
            val retVal = ReturnValue<Value>()
            val info = FunctionCallbackInfo(
                isolatePtr = 0L,
                thisObj = (recv as? Object) ?: Object(),
                dataVal = undefinedValue(),
                args = args,
                isConstruct = false,
            )
            callback.invoke(info)
            return retVal.get()
        }
        return undefinedValue()
    }

    fun callWithContext(
        scope: Any? = null,
        context: Context,
        recv: Value,
        args: List<Value> = emptyList(),
    ): Value? {
        checkNotNull(context)
        return call(scope, recv, args)
    }
    fun call_with_context(
        scope: Any? = null,
        context: Context,
        recv: Value,
        args: List<Value> = emptyList(),
    ): Value? = callWithContext(scope, context, recv, args)

    fun newInstance(
        scope: Any? = null,
        args: List<Value> = emptyList(),
    ): Object? {
        if (scope != null) {
            check(scope.hashCode() != 0)
        }
        return Object.new(scope)
    }
    fun new_instance(
        scope: Any? = null,
        args: List<Value> = emptyList(),
    ): Object? = newInstance(scope, args)

    fun getName(scope: Any? = null): V8String {
        if (scope != null) {
            check(scope.hashCode() != 0)
        }
        return nameVal
    }
    fun get_name(scope: Any? = null): V8String = getName(scope)

    fun setName(name: V8String) {
        this.nameVal = name
    }
    fun set_name(name: V8String) = setName(name)

    fun getScriptColumnNumber(): UInt? = null
    fun get_script_column_number(): UInt? = getScriptColumnNumber()

    fun getScriptLineNumber(): UInt? = null
    fun get_script_line_number(): UInt? = getScriptLineNumber()

    fun getScriptOrigin(): ScriptOrigin? = null
    fun get_script_origin(): ScriptOrigin? = getScriptOrigin()

    fun scriptId(): Int = scriptIdVal
    fun script_id(): Int = scriptId()

    fun createCodeCache(): UniqueRef<CachedData>? = null
    fun create_code_cache(): UniqueRef<CachedData>? = createCodeCache()

    companion object {
        fun builder(callback: FunctionCallback): FunctionBuilder<Function> =
            FunctionBuilder.new(callback)

        fun builderRaw(callback: FunctionCallback): FunctionBuilder<Function> =
            FunctionBuilder.newRaw(callback)
        fun builder_raw(callback: FunctionCallback): FunctionBuilder<Function> =
            builderRaw(callback)

        fun new(
            scope: Any? = null,
            callback: FunctionCallback,
        ): Function? = builder(callback).build(scope)

        fun newRaw(
            scope: Any? = null,
            callback: FunctionCallback,
        ): Function? = builderRaw(callback).build(scope)
        fun new_raw(
            scope: Any? = null,
            callback: FunctionCallback,
        ): Function? = newRaw(scope, callback)
    }
}
