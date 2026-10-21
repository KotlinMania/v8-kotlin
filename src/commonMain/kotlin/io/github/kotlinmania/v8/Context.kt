// port-lint: source context.rs
package io.github.kotlinmania.v8

import kotlin.reflect.KClass

class ContextOptions(
    val globalTemplate: ObjectTemplate? = null,
    val globalObject: Value? = null,
    val microtaskQueue: MicrotaskQueue? = null,
)

class ContextAnnex {
    @PublishedApi
    internal val slots: MutableMap<Any, Any> = mutableMapOf()
}

class Context internal constructor(
    internal var rawPtr: Long = 0L,
) : Data {
    override fun isContext(): Boolean = true

    private var annex: ContextAnnex? = null
    private var microtaskQueueRef: MicrotaskQueue? = null
    private var securityTokenVal: Value? = null
    private var allowCodeGenFromStrings: Boolean = true
    private val embedderDataMap: MutableMap<Int, Value> = mutableMapOf()
    private val alignedPointers: MutableMap<Int, Long> = mutableMapOf()

    companion object {
        const val ANNEX_SLOT: Int = 1
        const val INTERNAL_SLOT_COUNT: Int = 2

        fun new(
            scope: Any? = null,
            options: ContextOptions = ContextOptions(),
        ): Context {
            scope.let {}
            val ctx = Context()
            ctx.microtaskQueueRef = options.microtaskQueue
            return ctx
        }

        fun fromSnapshot(
            scope: Any? = null,
            contextSnapshotIndex: Int = 0,
            options: ContextOptions = ContextOptions(),
        ): Context? {
            scope.let {}
            contextSnapshotIndex.let {}
            val ctx = Context()
            ctx.microtaskQueueRef = options.microtaskQueue
            return ctx
        }
    }

    fun getExtrasBindingObject(scope: Any? = null): Object {
        scope.let {}
        return Object()
    }

    fun global(scope: Any? = null): Object {
        scope.let {}
        return Object()
    }

    fun getMicrotaskQueue(): MicrotaskQueue = microtaskQueueRef ?: MicrotaskQueue.new().also { microtaskQueueRef = it }

    fun setMicrotaskQueue(queue: MicrotaskQueue) {
        this.microtaskQueueRef = queue
    }

    @PublishedApi
    internal fun getAnnexMut(createIfNotPresent: Boolean = false): ContextAnnex? {
        if (annex == null && createIfNotPresent) {
            annex = ContextAnnex()
        }
        return annex
    }

    inline fun <reified T : Any> getSlot(): T? {
        val raw = getAnnexMut(false)?.slots?.get(T::class) ?: return null
        return raw as? T
    }

    inline fun <reified T : Any> setSlot(value: T): T? {
        val a = getAnnexMut(true) ?: return null
        val old = a.slots.put(T::class, value)
        return old as? T
    }

    inline fun <reified T : Any> removeSlot(): T? {
        val old = getAnnexMut(false)?.slots?.remove(T::class) ?: return null
        return old as? T
    }

    fun clearAllSlots() {
        annex?.slots?.clear()
        annex = null
    }

    fun setEmbedderData(slot: Int, data: Value) {
        getAnnexMut(true)
        embedderDataMap[slot + INTERNAL_SLOT_COUNT] = data
    }

    fun getEmbedderData(scope: Any? = null, slot: Int): Value? {
        scope.let {}
        return embedderDataMap[slot + INTERNAL_SLOT_COUNT]
    }

    fun setAlignedPointerInEmbedderData(slot: Int, data: Long) {
        getAnnexMut(true)
        alignedPointers[slot + INTERNAL_SLOT_COUNT] = data
    }

    fun getAlignedPointerFromEmbedderData(slot: Int): Long = alignedPointers[slot + INTERNAL_SLOT_COUNT] ?: 0L

    fun getSecurityToken(scope: Any? = null): Value {
        scope.let {}
        return securityTokenVal ?: Exception.error("DefaultSecurityToken")
    }

    fun setSecurityToken(token: Value) {
        this.securityTokenVal = token
    }

    fun useDefaultSecurityToken() {
        this.securityTokenVal = null
    }

    fun setAllowGenerationFromStrings(allow: Boolean) {
        this.allowCodeGenFromStrings = allow
    }

    fun isCodeGenerationFromStringsAllowed(): Boolean = allowCodeGenFromStrings
}
