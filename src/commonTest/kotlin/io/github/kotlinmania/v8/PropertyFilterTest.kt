// port-lint: tests property_filter.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PropertyFilterTest {
    @Test
    fun testAttr() {
        assertTrue(PropertyFilter.ALL_PROPERTIES.isAllProperties())
        assertFalse(PropertyFilter.ALL_PROPERTIES.isOnlyWritable())
        assertFalse(PropertyFilter.ALL_PROPERTIES.isOnlyEnumerable())
        assertFalse(PropertyFilter.ALL_PROPERTIES.isOnlyConfigurable())
        assertFalse(PropertyFilter.ALL_PROPERTIES.isSkipStrings())
        assertFalse(PropertyFilter.ALL_PROPERTIES.isSkipSymbols())

        assertFalse(PropertyFilter.ONLY_WRITABLE.isAllProperties())
        assertTrue(PropertyFilter.ONLY_WRITABLE.isOnlyWritable())
        assertFalse(PropertyFilter.ONLY_WRITABLE.isOnlyEnumerable())
        assertFalse(PropertyFilter.ONLY_WRITABLE.isOnlyConfigurable())
        assertFalse(PropertyFilter.ONLY_WRITABLE.isSkipStrings())
        assertFalse(PropertyFilter.ONLY_WRITABLE.isSkipSymbols())

        assertFalse(PropertyFilter.ONLY_ENUMERABLE.isAllProperties())
        assertFalse(PropertyFilter.ONLY_ENUMERABLE.isOnlyWritable())
        assertTrue(PropertyFilter.ONLY_ENUMERABLE.isOnlyEnumerable())
        assertFalse(PropertyFilter.ONLY_ENUMERABLE.isOnlyConfigurable())
        assertFalse(PropertyFilter.ONLY_ENUMERABLE.isSkipStrings())
        assertFalse(PropertyFilter.ONLY_ENUMERABLE.isSkipSymbols())

        assertFalse(PropertyFilter.ONLY_CONFIGURABLE.isAllProperties())
        assertFalse(PropertyFilter.ONLY_CONFIGURABLE.isOnlyWritable())
        assertFalse(PropertyFilter.ONLY_CONFIGURABLE.isOnlyEnumerable())
        assertTrue(PropertyFilter.ONLY_CONFIGURABLE.isOnlyConfigurable())
        assertFalse(PropertyFilter.ONLY_CONFIGURABLE.isSkipStrings())
        assertFalse(PropertyFilter.ONLY_CONFIGURABLE.isSkipSymbols())

        assertFalse(PropertyFilter.SKIP_STRINGS.isAllProperties())
        assertFalse(PropertyFilter.SKIP_STRINGS.isOnlyWritable())
        assertFalse(PropertyFilter.SKIP_STRINGS.isOnlyEnumerable())
        assertFalse(PropertyFilter.SKIP_STRINGS.isOnlyConfigurable())
        assertTrue(PropertyFilter.SKIP_STRINGS.isSkipStrings())
        assertFalse(PropertyFilter.SKIP_STRINGS.isSkipSymbols())

        assertFalse(PropertyFilter.SKIP_SYMBOLS.isAllProperties())
        assertFalse(PropertyFilter.SKIP_SYMBOLS.isOnlyWritable())
        assertFalse(PropertyFilter.SKIP_SYMBOLS.isOnlyEnumerable())
        assertFalse(PropertyFilter.SKIP_SYMBOLS.isOnlyConfigurable())
        assertFalse(PropertyFilter.SKIP_SYMBOLS.isSkipStrings())
        assertTrue(PropertyFilter.SKIP_SYMBOLS.isSkipSymbols())

        assertEquals(PropertyFilter.ALL_PROPERTIES, PropertyFilter.default())
        assertEquals(
            PropertyFilter.ONLY_WRITABLE,
            PropertyFilter.ALL_PROPERTIES or PropertyFilter.ONLY_WRITABLE,
        )

        val attr =
            PropertyFilter.ONLY_WRITABLE or
                PropertyFilter.ONLY_WRITABLE or
                PropertyFilter.SKIP_STRINGS
        assertFalse(attr.isAllProperties())
        assertTrue(attr.isOnlyWritable())
        assertFalse(attr.isOnlyEnumerable())
        assertFalse(attr.isOnlyConfigurable())
        assertTrue(attr.isSkipStrings())
        assertFalse(attr.isSkipSymbols())
    }
}
