// port-lint: tests array_buffer.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertNotNull

class ArrayBufferTest {
    @Test
    fun testRustAllocator() {
        val vtable = RustAllocatorVtable<Any>()
        val allocator = newRustAllocator("test", vtable)
        assertNotNull(allocator)
    }

    @Test
    fun testDefaultAllocator() {
        val allocator = newDefaultAllocator()
        assertNotNull(allocator)
    }
}

fun testRustAllocator() {
    val vtable = RustAllocatorVtable<Any>()
    val allocator = newRustAllocator("test", vtable)
    assertNotNull(allocator)
}

fun testDefaultAllocator() {
    val allocator = newDefaultAllocator()
    assertNotNull(allocator)
}
