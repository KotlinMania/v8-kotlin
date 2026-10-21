// port-lint: source object.rs
package io.github.kotlinmania.v8

enum class IntegrityLevel {
    Frozen,
    Sealed,
}

enum class KeyCollectionMode {
    OwnOnly,
    IncludePrototypes,
}

enum class IndexFilter {
    IncludeIndices,
    SkipIndices,
}

enum class KeyConversionMode {
    KeepNumbers,
    ConvertToString,
}

class GetPropertyNamesArgs(
    val mode: KeyCollectionMode = KeyCollectionMode.IncludePrototypes,
    val propertyFilter: PropertyFilter = PropertyFilter.ALL_PROPERTIES,
    val indexFilter: IndexFilter = IndexFilter.IncludeIndices,
    val keyConversion: KeyConversionMode = KeyConversionMode.KeepNumbers,
)

class PropertyDescriptor(
    val enumerable: Boolean? = null,
    val hasEnumerable: Boolean = false,
    val configurable: Boolean? = null,
    val hasConfigurable: Boolean = false,
    val value: Value? = null,
    val hasValue: Boolean = false,
    val writable: Boolean? = null,
    val hasWritable: Boolean = false,
    val get: Value? = null,
    val hasGet: Boolean = false,
    val set: Value? = null,
    val hasSet: Boolean = false,
)

class AccessorConfiguration(
    val getter: Any? = null,
    val setter: Any? = null,
    val data: Value? = null,
    val propertyAttribute: PropertyAttribute = PropertyAttribute.NONE,
)

const val LAST_TAG: UShort = 0x7fffu

open class Object internal constructor(
    internal var rawPtr: Long = 0L,
) : Value {
    override fun isObject(): Boolean = true

    private val properties: MutableMap<Any, Value> = mutableMapOf()
    private val indexProperties: MutableMap<UInt, Value> = mutableMapOf()
    private val privateProperties: MutableMap<Private, Value> = mutableMapOf()
    private val internalFields: MutableMap<Int, Data> = mutableMapOf()
    private val alignedPointers: MutableMap<Int, Long> = mutableMapOf()
    private var prototypeVal: Value? = null
    private var integrity: IntegrityLevel? = null
    private var isWrapper: Boolean = false
    private val creationContext: Context? = null

    companion object {
        fun new(scope: Any? = null): Object {
            scope.let {}
            return Object()
        }

        fun withPrototypeAndProperties(
            scope: Any? = null,
            prototypeOrNull: Value? = null,
            names: List<Name> = emptyList(),
            values: List<Value> = emptyList(),
        ): Object {
            scope.let {}
            val obj = Object()
            obj.prototypeVal = prototypeOrNull
            for (i in names.indices) {
                if (i < values.size) {
                    obj.properties[names[i]] = values[i]
                }
            }
            return obj
        }

        fun wrap(
            isolate: Any? = null,
            wrapper: Object,
            value: Any? = null,
        ) {
            isolate.let {}
            value.let {}
            wrapper.isWrapper = true
        }

        fun unwrap(
            isolate: Any? = null,
            wrapper: Object,
        ): Any? {
            isolate.let {}
            wrapper.let {}
            return null
        }
    }

    open fun set(
        scope: Any? = null,
        key: Value,
        value: Value,
    ): Any? {
        scope.let {}
        properties[key] = value
        return true
    }

    fun setWithReceiver(
        scope: Any? = null,
        key: Value,
        value: Value,
        receiver: Object,
    ): Boolean? {
        scope.let {}
        receiver.set(scope, key, value)
        return true
    }

    fun setIndex(
        scope: Any? = null,
        index: UInt,
        value: Value,
    ): Boolean? {
        scope.let {}
        indexProperties[index] = value
        return true
    }

    fun setPrototype(
        scope: Any? = null,
        prototype: Value,
    ): Boolean? {
        scope.let {}
        this.prototypeVal = prototype
        return true
    }

    fun getConstructorName(): String? = "Object"

    fun createDataProperty(
        scope: Any? = null,
        key: Name,
        value: Value,
    ): Boolean? {
        scope.let {}
        properties[key] = value
        return true
    }

    fun defineOwnProperty(
        scope: Any? = null,
        key: Name,
        value: Value,
        attr: PropertyAttribute = PropertyAttribute.NONE,
    ): Boolean? {
        scope.let {}
        attr.let {}
        properties[key] = value
        return true
    }

    fun defineProperty(
        scope: Any? = null,
        key: Name,
        descriptor: PropertyDescriptor,
    ): Boolean? {
        scope.let {}
        descriptor.value?.let { properties[key] = it }
        return true
    }

    open fun get(
        scope: Any? = null,
        key: Value,
    ): Value? {
        scope.let {}
        return properties[key]
    }

    fun getWithReceiver(
        scope: Any? = null,
        key: Value,
        receiver: Object,
    ): Value? {
        scope.let {}
        receiver.let {}
        return properties[key]
    }

    fun getIndex(
        scope: Any? = null,
        index: UInt,
    ): Value? {
        scope.let {}
        return indexProperties[index]
    }

    fun getPrototype(scope: Any? = null): Value? {
        scope.let {}
        return prototypeVal
    }

    fun setAccessor(
        scope: Any? = null,
        name: Name,
        getter: Any? = null,
    ): Boolean? {
        scope.let {}
        getter.let {}
        name.let {}
        return true
    }

    fun setAccessorWithSetter(
        scope: Any? = null,
        name: Name,
        getter: Any? = null,
        setter: Any? = null,
    ): Boolean? {
        scope.let {}
        getter.let {}
        setter.let {}
        name.let {}
        return true
    }

    fun setAccessorWithConfiguration(
        scope: Any? = null,
        name: Name,
        configuration: AccessorConfiguration,
    ): Boolean? {
        scope.let {}
        name.let {}
        configuration.let {}
        return true
    }

    fun getIdentityHash(): Int =
        (rawPtr.toInt() and 0x7fffffff).let { if (it == 0) 1 else it }

    fun getCreationContext(scope: Any? = null): Context? {
        scope.let {}
        return creationContext
    }

    fun getOwnPropertyNames(
        scope: Any? = null,
        args: GetPropertyNamesArgs = GetPropertyNamesArgs(),
    ): Array? {
        scope.let {}
        args.let {}
        return Array.new(scope, properties.size)
    }

    fun getPropertyNames(
        scope: Any? = null,
        args: GetPropertyNamesArgs = GetPropertyNamesArgs(),
    ): Array? {
        scope.let {}
        args.let {}
        return Array.new(scope, properties.size)
    }

    open fun has(
        scope: Any? = null,
        key: Value,
    ): Boolean? {
        scope.let {}
        return properties.containsKey(key)
    }

    fun hasIndex(
        scope: Any? = null,
        index: UInt,
    ): Boolean? {
        scope.let {}
        return indexProperties.containsKey(index)
    }

    fun hasOwnProperty(
        scope: Any? = null,
        key: Name,
    ): Boolean? {
        scope.let {}
        return properties.containsKey(key)
    }

    open fun delete(
        scope: Any? = null,
        key: Value,
    ): Boolean? {
        scope.let {}
        return properties.remove(key) != null
    }

    fun deleteIndex(
        scope: Any? = null,
        index: UInt,
    ): Boolean? {
        scope.let {}
        return indexProperties.remove(index) != null
    }

    fun internalFieldCount(): Int = internalFields.size

    fun getInternalField(
        scope: Any? = null,
        index: Int,
    ): Data? {
        scope.let {}
        return internalFields[index]
    }

    fun getAlignedPointerFromInternalField(
        index: Int,
        tag: UShort = 0u,
    ): Long {
        tag.let {}
        return alignedPointers[index] ?: 0L
    }

    fun setAlignedPointerInInternalField(
        index: Int,
        value: Long,
        tag: UShort = 0u,
    ) {
        tag.let {}
        alignedPointers[index] = value
    }

    fun isApiWrapper(): Boolean = isWrapper

    fun setIntegrityLevel(
        scope: Any? = null,
        level: IntegrityLevel,
    ): Boolean? {
        scope.let {}
        this.integrity = level
        return true
    }

    fun setInternalField(
        index: Int,
        data: Data,
    ): Boolean {
        internalFields[index] = data
        return true
    }

    fun getPrivate(
        scope: Any? = null,
        key: Private,
    ): Value? {
        scope.let {}
        return privateProperties[key]
    }

    fun setPrivate(
        scope: Any? = null,
        key: Private,
        value: Value,
    ): Boolean? {
        scope.let {}
        privateProperties[key] = value
        return true
    }

    fun deletePrivate(
        scope: Any? = null,
        key: Private,
    ): Boolean? {
        scope.let {}
        return privateProperties.remove(key) != null
    }

    fun hasPrivate(
        scope: Any? = null,
        key: Private,
    ): Boolean? {
        scope.let {}
        return privateProperties.containsKey(key)
    }

    fun getPropertyAttributes(
        scope: Any? = null,
        key: Value,
    ): PropertyAttribute? {
        scope.let {}
        return if (properties.containsKey(key)) PropertyAttribute.NONE else null
    }

    fun getOwnPropertyDescriptor(
        scope: Any? = null,
        key: Name,
    ): Value? {
        scope.let {}
        return properties[key]
    }

    fun previewEntries(scope: Any? = null): Pair<Array?, Boolean> {
        scope.let {}
        return Pair(Array.new(scope, 0), false)
    }

    fun getRealNamedProperty(
        scope: Any? = null,
        key: Name,
    ): Value? {
        scope.let {}
        return properties[key]
    }

    fun hasRealNamedProperty(
        scope: Any? = null,
        key: Name,
    ): Boolean? {
        scope.let {}
        return properties.containsKey(key)
    }

    fun getRealNamedPropertyAttributes(
        scope: Any? = null,
        key: Name,
    ): PropertyAttribute? {
        scope.let {}
        return if (properties.containsKey(key)) PropertyAttribute.NONE else null
    }
}

open class Array internal constructor(
    rawPtr: Long = 0L,
) : Object(rawPtr) {
    override fun isArray(): Boolean = true

    private var arrLength: UInt = 0u

    companion object {
        fun new(
            scope: Any? = null,
            length: Int = 0,
        ): Array {
            scope.let {}
            val a = Array()
            a.arrLength = if (length > 0) length.toUInt() else 0u
            return a
        }

        fun newWithElements(
            scope: Any? = null,
            elements: List<Value>,
        ): Array {
            scope.let {}
            val a = Array()
            a.arrLength = elements.size.toUInt()
            for (i in elements.indices) {
                a.setIndex(scope, i.toUInt(), elements[i])
            }
            return a
        }
    }

    fun length(): UInt = arrLength
}

internal open class Map internal constructor(
    rawPtr: Long = 0L,
) : Object(rawPtr) {
    private val mapStorage: MutableMap<Value, Value> = mutableMapOf()

    companion object {
        fun new(scope: Any? = null): Map {
            scope.let {}
            return Map()
        }
    }

    fun size(): Int = mapStorage.size

    fun clear() {
        mapStorage.clear()
    }

    override fun get(
        scope: Any?,
        key: Value,
    ): Value? {
        scope.let {}
        return mapStorage[key]
    }

    override fun set(
        scope: Any?,
        key: Value,
        value: Value,
    ): Map? {
        scope.let {}
        mapStorage[key] = value
        return this
    }

    override fun has(
        scope: Any?,
        key: Value,
    ): Boolean? {
        scope.let {}
        return mapStorage.containsKey(key)
    }

    override fun delete(
        scope: Any?,
        key: Value,
    ): Boolean? {
        scope.let {}
        return mapStorage.remove(key) != null
    }

    fun asArray(scope: Any? = null): Array {
        val flatList = mutableListOf<Value>()
        for ((k, v) in mapStorage) {
            flatList.add(k)
            flatList.add(v)
        }
        return Array.newWithElements(scope, flatList)
    }
}

internal open class Set internal constructor(
    rawPtr: Long = 0L,
) : Object(rawPtr) {
    private val setStorage: MutableSet<Value> = mutableSetOf()

    companion object {
        fun new(scope: Any? = null): Set {
            scope.let {}
            return Set()
        }
    }

    fun size(): Int = setStorage.size

    fun clear() {
        setStorage.clear()
    }

    fun add(
        scope: Any? = null,
        key: Value,
    ): Set? {
        scope.let {}
        setStorage.add(key)
        return this
    }

    override fun has(
        scope: Any?,
        key: Value,
    ): Boolean? {
        scope.let {}
        return setStorage.contains(key)
    }

    override fun delete(
        scope: Any?,
        key: Value,
    ): Boolean? {
        scope.let {}
        return setStorage.remove(key)
    }

    fun asArray(scope: Any? = null): Array = Array.newWithElements(scope, setStorage.toList())
}
