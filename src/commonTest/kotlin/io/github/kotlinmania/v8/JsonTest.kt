// port-lint: tests json.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class JsonTest {
    @Test
    fun testParseAndStringify() {
        val parsedNull = Json.parse("null")
        assertNotNull(parsedNull)
        assertTrue(parsedNull.isNull())

        val parsedBool = Json.parse("true")
        assertNotNull(parsedBool)
        assertTrue(parsedBool.isTrue())

        val parsedStr = Json.parse("\"hello\"")
        assertNotNull(parsedStr)
        assertTrue(parsedStr.isString())

        assertEquals("null", Json.stringify(Null))
        assertEquals("true", Json.stringify(V8Boolean.TRUE))
        assertEquals("\"hello\"", Json.stringify(V8String("hello")))
    }
}
