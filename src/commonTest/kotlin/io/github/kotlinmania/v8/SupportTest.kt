// port-lint: tests support.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SupportTest {
    @Test
    fun testMaybeBoolRoundTripThroughCBinding() {
        val t = MaybeBool.fromOption(true)
        assertEquals(MaybeBool.True, t)
        assertFalse(t.isNothing())
        assertTrue(t.toBool())
        assertEquals(true, t.intoOption())

        val f = MaybeBool.fromOption(false)
        assertEquals(MaybeBool.False, f)
        assertFalse(f.isNothing())
        assertFalse(f.toBool())
        assertEquals(false, f.intoOption())

        val n = MaybeBool.fromOption(null)
        assertEquals(MaybeBool.Nothing, n)
        assertTrue(n.isNothing())
        assertFalse(n.toBool())
        assertNull(n.intoOption())
        assertEquals(MaybeBool.Nothing, MaybeBool.nothing())
    }

    @Test
    fun testSupportAbiSizesAndPointerTuples() {
        assertEquals(16uL, V8Support.twoPointersSize())
        assertEquals(24uL, V8Support.threePointersSize())
        assertEquals(24uL, V8Support.handleScopeSize())
        assertEquals(32uL, V8Support.escapableHandleScopeSize())
        assertEquals(24uL, V8Support.promiseRejectMessageSize())
        assertEquals(16uL, V8Support.lockerSize())
        assertEquals(48uL, V8Support.tryCatchSize())
        assertEquals(16uL, V8Support.allowJavascriptExecutionScopeSize())
        assertEquals(16uL, V8Support.disallowJavascriptExecutionScopeSize())
        assertEquals(24uL, V8Support.cachedDataSize())

        val p2 = TwoPointers(10L, 20L)
        assertEquals(10L, p2.first)
        assertEquals(20L, p2.second)

        val p3 = ThreePointers(10L, 20L, 30L)
        assertEquals(30L, p3.third)

        assertEquals(0u.toUByte(), MemoryPressureLevel.None.code)
        assertEquals(1u.toUByte(), MemoryPressureLevel.Moderate.code)
        assertEquals(2u.toUByte(), MemoryPressureLevel.Critical.code)
    }
}
