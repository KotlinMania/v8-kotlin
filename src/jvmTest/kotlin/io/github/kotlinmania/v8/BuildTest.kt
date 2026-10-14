// port-lint: tests build.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertTrue

class BuildTest {
    @Test
    fun testWhichExecutable() {
        val result = BuildSupport.whichExecutable("nonexistent_binary_name_12345")
        assertTrue(result == null)
    }
}
