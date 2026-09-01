// port-lint: tests exception.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExceptionTest {
    @Test
    fun testErrors() {
        val err = Exception.error("something went wrong")
        assertTrue(err.isNativeError())
        assertTrue(err.isObject())
        assertEquals("Error: something went wrong", err.toString())

        val typeErr = Exception.typeError("invalid argument")
        assertTrue(typeErr.isNativeError())
        assertEquals("TypeError: invalid argument", typeErr.toString())

        val msg = Exception.createMessage(err)
        assertEquals("Error: something went wrong", msg.text)

        val frame =
            StackFrame(
                lineNumber = 42,
                column = 10,
                scriptName = "index.js",
                functionName = "main",
            )
        val trace = StackTrace(listOf(frame))
        assertEquals(1, trace.getFrameCount())
        assertEquals(42, trace.getFrame(0)?.lineNumber)
        assertEquals("index.js", trace.getFrame(0)?.scriptName)
    }
}
