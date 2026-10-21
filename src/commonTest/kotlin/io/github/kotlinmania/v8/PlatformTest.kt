// port-lint: tests platform.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlatformTest {
    @Test
    fun testDefaultAndUnprotectedAndSingleThreadedPlatforms() {
        val defaultPlat = newDefaultPlatform(threadPoolSize = 4u, idleTaskSupport = true)
        assertNotEquals(0L, defaultPlat.asRawPtr())
        assertEquals(4, defaultPlat.threadPoolSize())
        assertTrue(defaultPlat.hasIdleTaskSupport())
        assertFalse(defaultPlat.isUnprotected())
        assertFalse(defaultPlat.isSingleThreaded())
        assertEquals(1L, defaultPlat.useCount())
        defaultPlat.makeShared()
        assertEquals(2L, defaultPlat.useCount())
        defaultPlat.close()
        assertEquals(0L, defaultPlat.asRawPtr())

        val unprotectedPlat = newUnprotectedDefaultPlatform(threadPoolSize = 32u, idleTaskSupport = false)
        assertEquals(16, unprotectedPlat.threadPoolSize())
        assertFalse(unprotectedPlat.hasIdleTaskSupport())
        assertTrue(unprotectedPlat.isUnprotected())
        assertFalse(unprotectedPlat.isSingleThreaded())
        unprotectedPlat.close()

        val singleThreadedPlat = newSingleThreadedDefaultPlatform(idleTaskSupport = true)
        assertEquals(0, singleThreadedPlat.threadPoolSize())
        assertTrue(singleThreadedPlat.hasIdleTaskSupport())
        assertFalse(singleThreadedPlat.isUnprotected())
        assertTrue(singleThreadedPlat.isSingleThreaded())
        singleThreadedPlat.close()
    }

    @Test
    fun testCustomPlatformAndMessageLoop() {
        var postedTasks = 0
        val customImpl =
            object : PlatformImpl {
                override fun postTask(isolatePtr: Long) {
                    isolatePtr.let {}
                    postedTasks += 1
                }
            }
        val customPlat =
            newCustomPlatform(
                threadPoolSize = 2u,
                idleTaskSupport = true,
                unprotected = true,
                platformImpl = customImpl,
            )
        assertNotNull(customPlat.customImplementation())
        customPlat.customImplementation()?.postTask(123L)
        assertEquals(1, postedTasks)

        val isolate = IsolateHandle.create()
        assertFalse(Platform.pumpMessageLoop(customPlat, isolate, waitForWork = false))
        Platform.runIdleTasks(customPlat, isolate, 0.01)
        Platform.notifyIsolateShutdown(customPlat, isolate)
        isolate.dispose()
        customPlat.close()
    }
}
