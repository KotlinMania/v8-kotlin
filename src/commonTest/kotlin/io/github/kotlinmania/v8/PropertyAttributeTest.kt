// port-lint: source property_attribute.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PropertyAttributeTest {
    @Test
    fun testAttr() {
        assertTrue(PropertyAttribute.NONE.isNone())
        assertFalse(PropertyAttribute.NONE.isReadOnly())
        assertFalse(PropertyAttribute.NONE.isDontEnum())
        assertFalse(PropertyAttribute.NONE.isDontDelete())

        assertFalse(PropertyAttribute.READ_ONLY.isNone())
        assertTrue(PropertyAttribute.READ_ONLY.isReadOnly())
        assertFalse(PropertyAttribute.READ_ONLY.isDontEnum())
        assertFalse(PropertyAttribute.READ_ONLY.isDontDelete())

        assertFalse(PropertyAttribute.DONT_ENUM.isNone())
        assertFalse(PropertyAttribute.DONT_ENUM.isReadOnly())
        assertTrue(PropertyAttribute.DONT_ENUM.isDontEnum())
        assertFalse(PropertyAttribute.DONT_ENUM.isDontDelete())

        assertFalse(PropertyAttribute.DONT_DELETE.isNone())
        assertFalse(PropertyAttribute.DONT_DELETE.isReadOnly())
        assertFalse(PropertyAttribute.DONT_DELETE.isDontEnum())
        assertTrue(PropertyAttribute.DONT_DELETE.isDontDelete())

        assertEquals(PropertyAttribute.NONE, PropertyAttribute.default())
        assertEquals(PropertyAttribute.READ_ONLY, PropertyAttribute.NONE or PropertyAttribute.READ_ONLY)

        var attr = PropertyAttribute.READ_ONLY or PropertyAttribute.DONT_ENUM
        assertFalse(attr.isNone())
        assertTrue(attr.isReadOnly())
        assertTrue(attr.isDontEnum())
        assertFalse(attr.isDontDelete())

        attr = PropertyAttribute.READ_ONLY or PropertyAttribute.READ_ONLY or PropertyAttribute.DONT_ENUM
        assertFalse(attr.isNone())
        assertTrue(attr.isReadOnly())
        assertTrue(attr.isDontEnum())
        assertFalse(attr.isDontDelete())
    }
}
