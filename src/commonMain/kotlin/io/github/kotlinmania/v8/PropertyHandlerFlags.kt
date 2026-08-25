// port-lint: source property_handler_flags.rs
package io.github.kotlinmania.v8

class PropertyHandlerFlags private constructor(
    private val bits: UInt,
) {
    /** Test if no property handler flags are set. */
    fun isNone(): Boolean = this == NONE

    /** Test if the non-masking property handler flag is set. */
    fun isNonMasking(): Boolean = has(NON_MASKING)

    /** Test if the only-intercept-strings property handler flag is set. */
    fun isOnlyInterceptStrings(): Boolean = has(ONLY_INTERCEPT_STRINGS)

    /** Test if the has-no-side-effect property handler flag is set. */
    fun isHasNoSideEffect(): Boolean = has(HAS_NO_SIDE_EFFECT)

    private fun has(that: PropertyHandlerFlags): Boolean = (bits and that.bits) != 0u

    fun asU32(): UInt = bits

    infix fun or(rhs: PropertyHandlerFlags): PropertyHandlerFlags = PropertyHandlerFlags(bits or rhs.bits)

    fun bitor(rhs: PropertyHandlerFlags): PropertyHandlerFlags = this or rhs

    override fun equals(other: Any?): Boolean =
        other is PropertyHandlerFlags && bits == other.bits

    override fun hashCode(): Int = bits.hashCode()

    override fun toString(): String = "PropertyHandlerFlags($bits)"

    companion object {
        val NONE: PropertyHandlerFlags = PropertyHandlerFlags(0u)
        val NON_MASKING: PropertyHandlerFlags = PropertyHandlerFlags(1u shl 0)
        val ONLY_INTERCEPT_STRINGS: PropertyHandlerFlags = PropertyHandlerFlags(1u shl 1)
        val HAS_NO_SIDE_EFFECT: PropertyHandlerFlags = PropertyHandlerFlags(1u shl 2)

        fun default(): PropertyHandlerFlags = NONE
    }
}
