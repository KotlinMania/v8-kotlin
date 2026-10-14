// port-lint: source build.rs
package io.github.kotlinmania.v8

import io.github.kotlinmania.which.which

/**
 * Build and environment helper functions transliterated from upstream `build.rs`.
 */
object BuildSupport {
    /**
     * Looks up an executable in PATH using `which-kotlin`.
     */
    fun whichExecutable(name: String): String? = which(name).getOrNull()

    /**
     * Checks if ninja build tool is available in PATH.
     */
    fun hasNinja(): Boolean = which("ninja").isSuccess

    /**
     * Checks if gn build tool is available in PATH.
     */
    fun hasGn(): Boolean = which("gn").isSuccess

    /**
     * Checks if sccache compiler cache is available in PATH.
     */
    fun hasSccache(): Boolean = which("sccache").isSuccess

    /**
     * Checks if ccache compiler cache is available in PATH.
     */
    fun hasCcache(): Boolean = which("ccache").isSuccess
}
