// port-lint: tests v8/src/property_handler_flags.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PropertyHandlerFlagsTest {
    @Test
    fun testAttr() {
        assertTrue(PropertyHandlerFlags.NONE.isNone())
        assertFalse(PropertyHandlerFlags.NONE.isNonMasking())
        assertFalse(PropertyHandlerFlags.NONE.isOnlyInterceptStrings())
        assertFalse(PropertyHandlerFlags.NONE.isHasNoSideEffect())

        assertFalse(PropertyHandlerFlags.NON_MASKING.isNone())
        assertTrue(PropertyHandlerFlags.NON_MASKING.isNonMasking())
        assertFalse(PropertyHandlerFlags.NON_MASKING.isOnlyInterceptStrings())
        assertFalse(PropertyHandlerFlags.NON_MASKING.isHasNoSideEffect())

        assertFalse(PropertyHandlerFlags.ONLY_INTERCEPT_STRINGS.isNone())
        assertFalse(PropertyHandlerFlags.ONLY_INTERCEPT_STRINGS.isNonMasking())
        assertTrue(PropertyHandlerFlags.ONLY_INTERCEPT_STRINGS.isOnlyInterceptStrings())
        assertFalse(PropertyHandlerFlags.ONLY_INTERCEPT_STRINGS.isHasNoSideEffect())

        assertFalse(PropertyHandlerFlags.HAS_NO_SIDE_EFFECT.isNone())
        assertFalse(PropertyHandlerFlags.HAS_NO_SIDE_EFFECT.isNonMasking())
        assertFalse(PropertyHandlerFlags.HAS_NO_SIDE_EFFECT.isOnlyInterceptStrings())
        assertTrue(PropertyHandlerFlags.HAS_NO_SIDE_EFFECT.isHasNoSideEffect())

        assertEquals(PropertyHandlerFlags.NONE, PropertyHandlerFlags.default())

        val attr =
            PropertyHandlerFlags.ONLY_INTERCEPT_STRINGS or
                PropertyHandlerFlags.HAS_NO_SIDE_EFFECT or
                PropertyHandlerFlags.NON_MASKING
        assertFalse(attr.isNone())
        assertTrue(attr.isNonMasking())
        assertTrue(attr.isOnlyInterceptStrings())
        assertTrue(attr.isHasNoSideEffect())
    }
}
