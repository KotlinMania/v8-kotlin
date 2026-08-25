// port-lint: source property_filter.rs
package io.github.kotlinmania.v8

class PropertyFilter private constructor(
    private val bits: UInt,
) {
    /** Test if all property filters are set. */
    fun isAllProperties(): Boolean = this == ALL_PROPERTIES

    /** Test if the only-writable property filter is set. */
    fun isOnlyWritable(): Boolean = has(ONLY_WRITABLE)

    /** Test if the only-enumerable property filter is set. */
    fun isOnlyEnumerable(): Boolean = has(ONLY_ENUMERABLE)

    /** Test if the only-configurable property filter is set. */
    fun isOnlyConfigurable(): Boolean = has(ONLY_CONFIGURABLE)

    /** Test if the skip-strings property filter is set. */
    fun isSkipStrings(): Boolean = has(SKIP_STRINGS)

    /** Test if the skip-symbols property filter is set. */
    fun isSkipSymbols(): Boolean = has(SKIP_SYMBOLS)

    private fun has(that: PropertyFilter): Boolean = (bits and that.bits) != 0u

    fun asU32(): UInt = bits

    infix fun or(rhs: PropertyFilter): PropertyFilter = PropertyFilter(bits or rhs.bits)

    fun bitor(rhs: PropertyFilter): PropertyFilter = this or rhs

    override fun equals(other: Any?): Boolean =
        other is PropertyFilter && bits == other.bits

    override fun hashCode(): Int = bits.hashCode()

    override fun toString(): String = "PropertyFilter($bits)"

    companion object {
        val ALL_PROPERTIES: PropertyFilter = PropertyFilter(0u)
        val ONLY_WRITABLE: PropertyFilter = PropertyFilter(1u shl 0)
        val ONLY_ENUMERABLE: PropertyFilter = PropertyFilter(1u shl 1)
        val ONLY_CONFIGURABLE: PropertyFilter = PropertyFilter(1u shl 2)
        val SKIP_STRINGS: PropertyFilter = PropertyFilter(1u shl 3)
        val SKIP_SYMBOLS: PropertyFilter = PropertyFilter(1u shl 4)

        fun default(): PropertyFilter = ALL_PROPERTIES
    }
}
