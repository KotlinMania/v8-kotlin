// port-lint: tests tests/test_api.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class StringTest {
    @Test
    fun testStringEmpty() {
        val local = V8String.empty()
        assertEquals(0, local.length())
        assertEquals(0, local.utf8Length())
        assertEquals("", local.toRustStringLossy())
    }

    @Test
    fun testStringNewFromUtf8() {
        val empty = V8String.newFromUtf8(buffer = ByteArray(0), newType = NewStringType.Normal)
        assertNotNull(empty)
        assertEquals(0, empty.length())
        assertEquals(0, empty.utf8Length())
        assertEquals("", empty.toRustStringLossy())

        val hello = V8String.newFromUtf8(buffer = "hello".encodeToByteArray())
        assertNotNull(hello)
        assertEquals(5, hello.length())
        assertEquals(5, hello.utf8Length())
        assertEquals("hello", hello.toRustStringLossy())
    }

    @Test
    fun testStringNewFromOneByte() {
        val local = V8String.newFromOneByte(buffer = "foo".encodeToByteArray(), newType = NewStringType.Normal)
        assertNotNull(local)
        assertEquals(3, local.length())
        assertEquals(3, local.utf8Length())

        val buffer = ByteArray(3)
        local.writeOneByteV2(offset = 0u, buffer = buffer, flags = WriteFlags.NONE)
        assertEquals("foo", buffer.decodeToString())
        assertEquals("foo", local.toRustStringLossy())
    }

    @Test
    fun testStringNewFromTwoByte() {
        // [0xD83E, 0xDD95] is the dinosaur emoji 🦕
        val local =
            V8String.newFromTwoByte(
                buffer = shortArrayOf(0xD83E.toShort(), 0xDD95.toShort()),
                newType = NewStringType.Normal,
            )
        assertNotNull(local)
        assertEquals(2, local.length())
        assertEquals(4, local.utf8Length())
        assertEquals("🦕", local.toRustStringLossy())
    }

    @Test
    fun testOneByteConst() {
        val constStr = V8String.createExternalOnebyteConst("const static".encodeToByteArray())
        assertEquals("const static", constStr.asStr())
        assertEquals("const static", constStr.asRef())
        assertEquals("const static", constStr.deref())
        assertEquals(12, constStr.length)

        val local = V8String.newFromOnebyteConst(onebyteConst = constStr)
        assertNotNull(local)
        assertEquals("const static", local.toRustStringLossy())
    }

    @Test
    fun testStringWriteUtf8() {
        val local = V8String.new(value = "hello world")
        assertNotNull(local)
        val buffer = ByteArray(11)
        val written = local.writeUtf8V2(buffer = buffer)
        assertEquals(11, written)
        assertEquals("hello world", buffer.decodeToString())
    }

    @Test
    fun testValueView() {
        val local = V8String.new(value = "view test")
        assertNotNull(local)
        val view = ValueView.new(string = local)
        val data = view.data()
        assertTrue(data is ValueViewData.OneByte)
        assertEquals("view test", data.bytes.decodeToString())
        view.drop()
    }
}
