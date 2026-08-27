// port-lint: tests v8/src/primitives.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PrimitivesTest {
    @Test
    fun testNullAndUndefined() {
        val n = nullValue()
        assertTrue(n.isNull())
        assertTrue(n.isNullOrUndefined())
        assertFalse(n.isUndefined())
        assertEquals("null", n.toString())

        val u = undefinedValue()
        assertTrue(u.isUndefined())
        assertTrue(u.isNullOrUndefined())
        assertFalse(u.isNull())
        assertEquals("undefined", u.toString())
    }

    @Test
    fun testBoolean() {
        val t = V8Boolean.new(true)
        val f = V8Boolean.new(false)

        assertTrue(t.isTrue())
        assertFalse(t.isFalse())
        assertTrue(t.isBoolean())
        assertTrue(t.isPrimitive())
        assertTrue(t.isValue())

        assertFalse(f.isTrue())
        assertTrue(f.isFalse())
        assertTrue(f.isBoolean())

        assertEquals(V8Boolean.TRUE, t)
        assertEquals(V8Boolean.FALSE, f)
    }
}
