// port-lint: tests isolate.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class IsolateTest {
    @Test
    fun testIsolateLifecycle() {
        val owned = Isolate.new(CreateParams())
        assertNotNull(owned)
        val isolate = owned.deref()
        assertNotNull(isolate)

        isolate.enter()
        isolate.exit()

        val handle = isolate.threadSafeHandle()
        assertNotNull(handle)

        val stats = isolate.getHeapStatistics()
        assertNotNull(stats)
        assertTrue(stats.totalHeapSize() >= 0uL)
        assertTrue(stats.usedHeapSize() >= 0uL)

        isolate.setMicrotasksPolicy(MicrotasksPolicy.Explicit)
        assertEquals(MicrotasksPolicy.Explicit, isolate.getMicrotasksPolicy())

        isolate.performMicrotaskCheckpoint()
        isolate.lowMemoryNotification()
        isolate.clearKeptObjects()

        assertEquals(1024L, isolate.adjustAmountOfExternalAllocatedMemory(1024L))
        assertFalse(isolate.hasPendingBackgroundTasks())

        assertTrue(isolate.terminateExecution())
        assertTrue(isolate.cancelTerminateExecution())
        assertFalse(isolate.isExecutionTerminating())

        isolate.dateTimeConfigurationChangeNotification(TimeZoneDetection.Skip)

        owned.close()
    }

    @Test
    fun testIsolateHandleOperations() {
        val owned = Isolate.new(CreateParams())
        val isolate = owned.deref()
        val handle = isolate.threadSafeHandle()

        val stats = handle.getHeapStatistics()
        assertNotNull(stats)

        val codeStats = handle.getHeapCodeAndMetadataStatistics()
        assertNotNull(codeStats)
        assertTrue(codeStats.codeAndMetadataSize() >= 0uL)

        owned.close()
    }

    @Test
    fun testMicrotasksPolicy() {
        assertEquals(MicrotasksPolicy.EXPLICIT, MicrotasksPolicy.fromValue(0))
        assertEquals(MicrotasksPolicy.SCOPED, MicrotasksPolicy.fromValue(1))
        assertEquals(MicrotasksPolicy.AUTO, MicrotasksPolicy.fromValue(2))
    }

    @Test
    fun testHeapSpaceStatistics() {
        val owned = Isolate.new(CreateParams())
        val isolate = owned.deref()
        val count = isolate.numberOfHeapSpaces()
        assertTrue(count >= 0)
        owned.close()
    }
}
