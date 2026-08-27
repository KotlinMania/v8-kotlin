// port-lint: tests v8/src/private.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class PrivateTest {
    @Test
    fun testPrivateCreation() {
        val p1 = Private.new("privateKey")
        val p2 = Private.new("privateKey")
        assertNotEquals(p1, p2)
        assertEquals("privateKey", p1.name)
        assertTrue(p1.isPrivate())

        val api1 = Private.forApi("Class#field")
        val api2 = Private.forApi("Class#field")
        assertEquals(api1, api2)
    }
}
