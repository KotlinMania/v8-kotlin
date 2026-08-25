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
        physicalMemorySize = physicalMemory
    }
}

/**
 * Initial configuration parameters for a new Isolate.
 */
class CreateParams(
    var allowAtomicsWait: Boolean = true,
) {
    val constraints: ResourceConstraints = ResourceConstraints()

    fun allowAtomicsWait(value: Boolean): CreateParams {
        this.allowAtomicsWait = value
        return this
    }

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
