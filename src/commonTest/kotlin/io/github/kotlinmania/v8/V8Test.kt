// port-lint: tests V8.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class V8Test {
    @Test
    fun testV8LifecycleAndGlobalStateMachine() {
        V8Engine.resetForTesting()
        assertEquals(V8GlobalState.Uninitialized, V8Engine.state())
        assertFailsWith<IllegalStateException> {
            assertInitialized()
        }

        val platform = newDefaultPlatform(2u, true)
        initializePlatform(platform)
        assertEquals(V8GlobalState.PlatformInitialized, V8Engine.state())

        initialize()
        assertEquals(V8GlobalState.Initialized, V8Engine.state())
        assertInitialized()
        assertEquals(platform, getCurrentPlatform())
        assertTrue(getVersion().startsWith("14.6."))

        assertTrue(dispose())
        assertEquals(V8GlobalState.Disposed, V8Engine.state())

        disposePlatform()
        assertEquals(V8GlobalState.PlatformShutdown, V8Engine.state())
        assertEquals(0L, platform.asRawPtr())
        V8Engine.resetForTesting()
    }

    @Test
    fun testFlagsFromCommandLineAndString() {
        setFlagsFromString("--use-strict")
        assertTrue(V8Engine.getFlagsString().contains("--use-strict"))

        val remaining =
            setFlagsFromCommandLineWithUsage(
                listOf("v8-app", "--expose-gc", "--custom-flag", "--single-threaded", "script.js"),
                "Usage: v8-app [options] script.js",
            )
        assertEquals(listOf("v8-app", "--custom-flag", "script.js"), remaining)
        assertTrue(V8Engine.getFlagsString().contains("--expose-gc"))
        assertTrue(V8Engine.getFlagsString().contains("--single-threaded"))
    }

    @Test
    fun testSandboxOffByDefault() {
        assertFalse(isSandboxEnabled())
    }

    @Test
    fun testIsolateLifecycleSlotsMicrotasksAndHeapStatistics() {
        val isolate = IsolateHandle.create()
        assertNotEquals(0L, isolate.asRawPtr())
        assertEquals(isolate.asRawPtr(), IsolateHandle.currentRawPtr())

        assertTrue(isolate.getNumberOfDataSlots() >= 4u)
        assertEquals(0L, isolate.getData(0u))
        isolate.setData(0u, 0xCAFEBABEL)
        isolate.setData(1u, 0x12345678L)
        assertEquals(0xCAFEBABEL, isolate.getData(0u))
        assertEquals(0x12345678L, isolate.getData(1u))

        assertEquals(MicrotasksPolicy.AUTO, isolate.getMicrotasksPolicy())
        isolate.setMicrotasksPolicy(MicrotasksPolicy.EXPLICIT)
        assertEquals(MicrotasksPolicy.EXPLICIT, isolate.getMicrotasksPolicy())
        isolate.setMicrotasksPolicy(MicrotasksPolicy.SCOPED)
        assertEquals(MicrotasksPolicy.SCOPED, isolate.getMicrotasksPolicy())

        isolate.performMicrotaskCheckpoint()
        isolate.memoryPressureNotification(MemoryPressureLevel.Moderate)
        isolate.lowMemoryNotification()
        isolate.clearKeptObjects()

        val heapStats = isolate.getHeapStatistics()
        assertTrue(heapStats.totalHeapSize() > 0uL)
        assertTrue(heapStats.usedHeapSize() > 0uL)
        assertTrue(heapStats.heapSizeLimit() >= heapStats.totalHeapSize())
        assertEquals(1uL, heapStats.numberOfNativeContexts())
        assertEquals(0uL, heapStats.numberOfDetachedContexts())
        assertFalse(heapStats.doesZapGarbage())

        val space0 = isolate.getHeapSpaceStatistics(0u)
        assertNotNull(space0)
        assertEquals("read_only_space", space0.spaceName())
        assertTrue(space0.spaceSize() > 0uL)
        assertNull(isolate.getHeapSpaceStatistics(99u))

        val codeStats = isolate.getHeapCodeStatistics()
        assertNotNull(codeStats)
        assertTrue(codeStats.codeAndMetadataSize() > 0uL)
        assertTrue(codeStats.bytecodeAndMetadataSize() > 0uL)

        isolate.exit()
        assertEquals(0L, IsolateHandle.currentRawPtr())
        isolate.enter()
        assertEquals(isolate.asRawPtr(), IsolateHandle.currentRawPtr())

        isolate.dispose()
        assertTrue(isolate.isDisposed())
        assertEquals(0L, IsolateHandle.currentRawPtr())
    }
}
