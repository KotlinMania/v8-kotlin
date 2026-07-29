// port-lint: source src/property_attribute.rs
package io.github.kotlinmania.v8

class PropertyAttribute private constructor(
    private val bits: UInt,
) {
    /** Test if no property attributes are set. */
    fun isNone(): Boolean = this == NONE

    /** Test if the read-only property attribute is set. */
    fun isReadOnly(): Boolean = has(READ_ONLY)

    /** Test if the non-enumerable property attribute is set. */
    fun isDontEnum(): Boolean = has(DONT_ENUM)

    /** Test if the non-configurable property attribute is set. */
    fun isDontDelete(): Boolean = has(DONT_DELETE)

    private fun has(that: PropertyAttribute): Boolean = (bits and that.bits) != 0u

    fun asU32(): UInt = bits

    infix fun or(rhs: PropertyAttribute): PropertyAttribute = PropertyAttribute(bits or rhs.bits)

    override fun equals(other: Any?): Boolean =
        other is PropertyAttribute && bits == other.bits

    override fun hashCode(): Int = bits.hashCode()

    override fun toString(): String = "PropertyAttribute($bits)"

    companion object {
        /** No property attributes. */
        val NONE: PropertyAttribute = PropertyAttribute(0u)

        /**
         * Not writable. Corresponds to
         * `Object.defineProperty(o, "p", { writable: false })`.
         */
        val READ_ONLY: PropertyAttribute = PropertyAttribute(1u)

        /**
         * Not enumerable. Corresponds to
         * `Object.defineProperty(o, "p", { enumerable: false })`.
         */
        val DONT_ENUM: PropertyAttribute = PropertyAttribute(1u shl 1)

        /**
         * Not configurable. Corresponds to
         * `Object.defineProperty(o, "p", { configurable: false })`.
         */
        val DONT_DELETE: PropertyAttribute = PropertyAttribute(1u shl 2)

        fun default(): PropertyAttribute = NONE
    }
}
