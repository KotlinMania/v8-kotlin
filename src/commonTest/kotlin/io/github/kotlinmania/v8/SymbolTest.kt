// port-lint: tests symbol.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SymbolTest {
    @Test
    fun testSymbolCreation() {
        val s1 = Symbol.new("test")
        val s2 = Symbol.new("test")
        assertNotEquals(s1, s2)
        assertEquals("test", s1.description())
        assertTrue(s1.isSymbol())
        assertTrue(s1.isName())
        assertTrue(s1.isValue())

        val global1 = Symbol.forKey("shared")
        val global2 = Symbol.forKey("shared")
        assertEquals(global1, global2)

        val api1 = Symbol.forApi("api_sym")
        val api2 = Symbol.forApi("api_sym")
        assertEquals(api1, api2)

        assertNotNull(Symbol.getIterator())
        assertNotNull(Symbol.getAsyncIterator())
        assertNotNull(Symbol.getToStringTag())
        assertNotNull(Symbol.getToPrimitive())
    }
}
