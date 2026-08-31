// port-lint: source v8/src/name.rs
package io.github.kotlinmania.v8

/**
 * A superclass for symbols and strings.
 */
interface Name : Value {
    override fun isName(): Boolean = true

    /**
     * Returns the V8 hash value for this name.
     * The return value will never be 0. Also, it is not guaranteed to be unique.
     */
    fun getIdentityHash(): Int = getHash().toInt().let { if (it == 0) 1 else it }
}
