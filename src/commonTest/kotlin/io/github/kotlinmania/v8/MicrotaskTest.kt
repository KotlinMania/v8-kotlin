// port-lint: tests v8/src/microtask.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class MicrotaskTest {
    @Test
    fun testQueue() {
        val queue = MicrotaskQueue.new(MicrotasksPolicy.AUTO)
        var count = 0
        queue.enqueueMicrotask { count++ }
        queue.enqueueMicrotask { count += 2 }

        assertEquals(0, count)
        assertFalse(queue.isRunningMicrotasks())
        queue.performCheckpoint()
        assertEquals(3, count)
        assertFalse(queue.isRunningMicrotasks())
    }
}
