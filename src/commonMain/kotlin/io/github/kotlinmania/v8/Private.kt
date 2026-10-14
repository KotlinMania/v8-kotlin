// port-lint: source private.rs
package io.github.kotlinmania.v8

/**
 * A private symbol.
 */
class Private private constructor(
    val name: String?,
) : Data {
    override fun isPrivate(): Boolean = true

    /** Returns the print name string of the private symbol, or null if none. */
    fun name(scope: Any? = null): String? = name

    override fun toString(): String = "Private(${name.orEmpty()})"

    override fun equals(other: Any?): Boolean = this === other

    override fun hashCode(): Int = super.hashCode()

    companion object {
        fun new(name: String? = null): Private = Private(name)

        private val apiRegistry = mutableMapOf<String, Private>()

        fun forApi(name: String? = null): Private =
            if (name != null) {
                apiRegistry.getOrPut(name) { Private(name) }
            } else {
                Private(null)
            }
    }
}
