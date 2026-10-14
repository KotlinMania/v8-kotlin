// port-lint: source isolate_create_params.rs
package io.github.kotlinmania.v8

/**
 * Resource constraints for an Isolate.
 */
class ResourceConstraints {
    var codeRangeSizeInBytes: Long = 0
    var maxOldGenerationSizeInBytes: Long = 0
    var maxYoungGenerationSizeInBytes: Long = 0
    var initialOldGenerationSizeInBytes: Long = 0
    var initialYoungGenerationSizeInBytes: Long = 0
    var physicalMemorySize: ULong = 0uL

    fun configureDefaultsFromHeapSize(initialHeapSizeInBytes: Long, maximumHeapSizeInBytes: Long) {
        initialOldGenerationSizeInBytes = initialHeapSizeInBytes
        maxOldGenerationSizeInBytes = maximumHeapSizeInBytes
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

    fun maxOldGenerationSizeInBytes(): Long = constraints.maxOldGenerationSizeInBytes

    fun setMaxOldGenerationSizeInBytes(limit: Long): CreateParams {
        constraints.maxOldGenerationSizeInBytes = limit
        return this
    }

    fun maxYoungGenerationSizeInBytes(): Long = constraints.maxYoungGenerationSizeInBytes

    fun setMaxYoungGenerationSizeInBytes(limit: Long): CreateParams {
        constraints.maxYoungGenerationSizeInBytes = limit
        return this
    }

    fun codeRangeSizeInBytes(): Long = constraints.codeRangeSizeInBytes

    fun setCodeRangeSizeInBytes(limit: Long): CreateParams {
        constraints.codeRangeSizeInBytes = limit
        return this
    }

    fun initialOldGenerationSizeInBytes(): Long = constraints.initialOldGenerationSizeInBytes

    fun setInitialOldGenerationSizeInBytes(initialSize: Long): CreateParams {
        constraints.initialOldGenerationSizeInBytes = initialSize
        return this
    }

    fun initialYoungGenerationSizeInBytes(): Long = constraints.initialYoungGenerationSizeInBytes

    fun setInitialYoungGenerationSizeInBytes(initialSize: Long): CreateParams {
        constraints.initialYoungGenerationSizeInBytes = initialSize
        return this
    }

    companion object {
        fun default(): CreateParams = CreateParams()
    }
}

fun createParamDefaults(): CreateParams = CreateParams()
