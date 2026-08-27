// port-lint: tests isolate_create_params.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class IsolateCreateParamsTest {
    @Test
    fun testDefaults() {
        val params = CreateParams.default()
        assertTrue(params.allowAtomicsWait)

        params.heapLimits(1024L * 1024L, 10L * 1024L * 1024L)
        assertEquals(1024L * 1024L, params.initialOldGenerationSizeInBytes())
        assertEquals(10L * 1024L * 1024L, params.maxOldGenerationSizeInBytes())

        params.setCodeRangeSizeInBytes(5000L)
        assertEquals(5000L, params.codeRangeSizeInBytes())
    }
}
