// port-lint: source microtask.rs
package io.github.kotlinmania.v8

/**
 * Represents the microtask queue, where microtasks are stored and processed.
 */
class MicrotaskQueue(
    val policy: MicrotasksPolicy = MicrotasksPolicy.AUTO,
) : AutoCloseable {
    private val queue = mutableListOf<() -> Unit>()
    private var running = false
    private val scopeDepth = 0

    fun enqueueMicrotask(microtask: () -> Unit) {
        queue.add(microtask)
    }

    fun performCheckpoint() {
        if (running) return
        running = true
        try {
            while (queue.isNotEmpty()) {
                val task = queue.removeAt(0)
                task()
            }
        } finally {
            running = false
        }
    }

    fun isRunningMicrotasks(): Boolean = running

    fun getMicrotasksScopeDepth(): Int = scopeDepth

    fun drop() {
        queue.clear()
    }

    override fun close() {
        drop()
    }

    companion object {
        fun new(policy: MicrotasksPolicy = MicrotasksPolicy.AUTO): MicrotaskQueue =
            MicrotaskQueue(policy)
    }
}
