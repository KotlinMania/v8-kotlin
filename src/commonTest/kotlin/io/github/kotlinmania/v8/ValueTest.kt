// port-lint: tests value.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValueTest {
    private class SimpleValue(
        private val name: String,
        private val isNum: Boolean = false,
        private val isUndef: Boolean = false,
        private val isNul: Boolean = false,
        private val isGenObj: Boolean = false,
    ) : Value {
        override fun isNumber(): Boolean = isNum

        override fun isUndefined(): Boolean = isUndef

        override fun isNull(): Boolean = isNul

        override fun isGeneratorObject(): Boolean = isGenObj

        override fun toString(): String = name

        override fun equals(other: Any?): Boolean = other is SimpleValue && name == other.name

        override fun hashCode(): Int = name.hashCode()
    }

    @Test
    fun testSameValueZero() {
        val v1 = SimpleValue("test")
        val v2 = SimpleValue("test")
        val v3 = SimpleValue("other")

        assertTrue(v1.sameValue(v2))
        assertTrue(v1.sameValueZero(v2))
        assertFalse(v1.sameValue(v3))
        assertFalse(v1.sameValueZero(v3))
    }

    @Test
    fun testTypeOfAndRepr() {
        val undef = SimpleValue("undefined", isUndef = true)
        assertEquals("undefined", undef.typeOf())
        assertEquals("undefined", undef.typeRepr())

        val nul = SimpleValue("null", isNul = true)
        assertEquals("object", nul.typeOf())
        assertEquals("null", nul.typeRepr())

        val num = SimpleValue("42", isNum = true)
        assertEquals("number", num.typeOf())
        assertEquals("Number", num.typeRepr())

        val gen = SimpleValue("generator", isGenObj = true)
        assertTrue(gen.isGeneratorObject())
    }

    @Test
    fun testDefaultConversions() {
        val v = SimpleValue("test")
        assertEquals("test", v.toRustStringLossy())
        assertEquals("test", v.toDetailString())
        assertFalse(v.booleanValue())
        assertFalse(v.toBoolean())
        assertEquals(null, v.numberValue())
        assertEquals(null, v.integerValue())
        assertEquals(null, v.uint32Value())
        assertEquals(null, v.int32Value())
        assertFalse(v.instanceOf("Object"))
    }
}
