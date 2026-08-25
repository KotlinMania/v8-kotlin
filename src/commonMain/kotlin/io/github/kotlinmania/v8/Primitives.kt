// port-lint: source primitives.rs
package io.github.kotlinmania.v8

/**
 * The superclass of primitive values. See ECMA-262 4.3.2.
 */
interface Primitive : Value {
    override fun isPrimitive(): Boolean = true
}

object Null : Primitive {
    override fun isNull(): Boolean = true
    override fun isNullOrUndefined(): Boolean = true
    override fun toString(): String = "null"
}

object Undefined : Primitive {
    override fun isUndefined(): Boolean = true
    override fun isNullOrUndefined(): Boolean = true
    override fun toString(): String = "undefined"
}

class V8Boolean private constructor(
    val value: Boolean,
) : Primitive {
    override fun isBoolean(): Boolean = true
    override fun isTrue(): Boolean = value
    override fun isFalse(): Boolean = !value

    override fun equals(other: Any?): Boolean =
        other is V8Boolean && value == other.value

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = value.toString()

    companion object {
        val TRUE: V8Boolean = V8Boolean(true)
        val FALSE: V8Boolean = V8Boolean(false)

        fun new(value: Boolean): V8Boolean = if (value) TRUE else FALSE
    }
}

fun nullValue(): Primitive = Null
fun undefinedValue(): Primitive = Undefined
