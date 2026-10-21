// port-lint: tests tests/test_api.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FunctionTest {
    @Test
    fun testReturnValue() {
        val rv = ReturnValue<Value>()
        rv.setNull()
        assertTrue(rv.get().isNull())

        rv.setUndefined()
        assertTrue(rv.get().isUndefined())

        rv.setInt32(42)
        assertTrue(rv.get().isNumber())

        rv.setDouble(3.14)
        assertTrue(rv.get().isNumber())

        rv.setEmptyString()
        assertTrue(rv.get().isString())
    }

    @Test
    fun testFunctionCallbackInfoAndArguments() {
        var called = false
        val cb: FunctionCallback = { info ->
            called = true
            assertEquals(0, info.length())
            assertTrue(info.data().isUndefined())
            assertEquals(false, info.isConstructCall())
        }

        val fn = Function.new(callback = cb)
        assertNotNull(fn)
        assertTrue(fn.isFunction())

        fn.setName(V8String(0L, "myFunc"))
        assertEquals("myFunc", fn.getName().value)

        fn.call(recv = Object(), args = emptyList())
        assertTrue(called)
    }

    @Test
    fun testFunctionBuilder() {
        val builder = Function.builder { _ -> }
            .length(2)
            .constructorBehavior(ConstructorBehavior.Allow)
            .sideEffectType(SideEffectType.HasSideEffect)

        val fn = builder.build()
        assertNotNull(fn)
        assertTrue(fn.isFunction())
    }
}
