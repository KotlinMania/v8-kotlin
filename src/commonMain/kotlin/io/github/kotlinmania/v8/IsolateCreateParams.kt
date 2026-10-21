// port-lint: source isolate_create_params.rs
package io.github.kotlinmania.v8

/**
 * Resource constraints for an Isolate.
 */
class ResourceConstraints {
    private var codeRangeSize: Long = 0
    private var maxOldGenSize: Long = 0
    private var maxYoungGenSize: Long = 0
    private var initialOldGenSize: Long = 0
    private var initialYoungGenSize: Long = 0
    private var physicalMemorySize: ULong = 0uL
    private var stackLimitValue: ULong = 0uL

    fun maxOldGenerationSizeInBytes(): Long = maxOldGenSize

    fun setMaxOldGenerationSizeInBytes(limit: Long) {
        maxOldGenSize = limit
    }

    fun maxYoungGenerationSizeInBytes(): Long = maxYoungGenSize

    fun setMaxYoungGenerationSizeInBytes(limit: Long) {
        maxYoungGenSize = limit
    }

    fun codeRangeSizeInBytes(): Long = codeRangeSize

    fun setCodeRangeSizeInBytes(limit: Long) {
        codeRangeSize = limit
    }

    fun stackLimit(): ULong = stackLimitValue

    fun setStackLimit(limit: ULong) {
        stackLimitValue = limit
    }

    fun initialOldGenerationSizeInBytes(): Long = initialOldGenSize

    fun setInitialOldGenerationSizeInBytes(initialSize: Long) {
        initialOldGenSize = initialSize
    }

    fun initialYoungGenerationSizeInBytes(): Long = initialYoungGenSize

    fun setInitialYoungGenerationSizeInBytes(initialSize: Long) {
        initialYoungGenSize = initialSize
    }

    fun configureDefaultsFromHeapSize(initialHeapSizeInBytes: Long, maximumHeapSizeInBytes: Long) {
        initialOldGenSize = initialHeapSizeInBytes
        maxOldGenSize = maximumHeapSizeInBytes
    }

    fun configureDefaults(physicalMemory: ULong, virtualMemoryLimit: ULong) {
        virtualMemoryLimit.hashCode()
        physicalMemorySize = physicalMemory
    }
}

typealias CounterLookupCallback = (name: String) -> Int?

class CreateParamAllocations {
    var snapshotBlobData: ByteArray? = null
    var externalReferences: List<Any>? = null
}

/**
 * Initial configuration parameters for a new Isolate.
 */
class CreateParams(
    var allowAtomicsWait: Boolean = true,
) {
    val constraints: ResourceConstraints = ResourceConstraints()
    var counterLookupCallback: CounterLookupCallback? = null
    var snapshotBlob: ByteArray? = null
    private var hasSetAllocator: Boolean = false
    private var stackLimitValue: ULong = 0uL

    fun allowAtomicsWait(value: Boolean): CreateParams {
        this.allowAtomicsWait = value
        return this
    }

    fun counterLookupCallback(callback: CounterLookupCallback?): CreateParams {
        this.counterLookupCallback = callback
        return this
    }

    fun snapshotBlob(data: ByteArray?): CreateParams {
        this.snapshotBlob = data
        return this
    }

    fun arrayBufferAllocator(): CreateParams {
        this.hasSetAllocator = true
        return this
    }

    fun hasSetArrayBufferAllocator(): Boolean = hasSetAllocator

    fun externalReferences(extRefs: List<Any> = emptyList()): CreateParams = this

    fun stackLimit(): ULong = stackLimitValue

    fun setStackLimit(limit: ULong): CreateParams {
        this.stackLimitValue = limit
        return this
    }

    fun cppHeap(heap: Any? = null): CreateParams = this

    fun finalize(): CreateParams = this

    fun heapLimits(initial: Long, max: Long): CreateParams {
        constraints.configureDefaultsFromHeapSize(initial, max)
        return this
    }

    fun heapLimitsFromSystemMemory(physicalMemory: ULong, virtualMemoryLimit: ULong): CreateParams {
        constraints.configureDefaults(physicalMemory, virtualMemoryLimit)
        return this
    }

    fun maxOldGenerationSizeInBytes(): Long = constraints.maxOldGenerationSizeInBytes()

    fun setMaxOldGenerationSizeInBytes(limit: Long): CreateParams {
        constraints.setMaxOldGenerationSizeInBytes(limit)
        return this
    }

    fun maxYoungGenerationSizeInBytes(): Long = constraints.maxYoungGenerationSizeInBytes()

    fun setMaxYoungGenerationSizeInBytes(limit: Long): CreateParams {
        constraints.setMaxYoungGenerationSizeInBytes(limit)
        return this
    }

    fun codeRangeSizeInBytes(): Long = constraints.codeRangeSizeInBytes()

    fun setCodeRangeSizeInBytes(limit: Long): CreateParams {
        constraints.setCodeRangeSizeInBytes(limit)
        return this
    }

    fun initialOldGenerationSizeInBytes(): Long = constraints.initialOldGenerationSizeInBytes()

    fun setInitialOldGenerationSizeInBytes(initialSize: Long): CreateParams {
        constraints.setInitialOldGenerationSizeInBytes(initialSize)
        return this
    }

    fun initialYoungGenerationSizeInBytes(): Long = constraints.initialYoungGenerationSizeInBytes()

    fun setInitialYoungGenerationSizeInBytes(initialSize: Long): CreateParams {
        constraints.setInitialYoungGenerationSizeInBytes(initialSize)
        return this
    }

    companion object {
        fun default(): CreateParams = CreateParams()
    }
}

fun createParamDefaults(): CreateParams = CreateParams()
