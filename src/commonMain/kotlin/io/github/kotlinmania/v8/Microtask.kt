// port-lint: source v8/src/microtask.rs
package io.github.kotlinmania.v8

/**
 * Policy for running microtasks.
 */
enum class MicrotasksPolicy {
    /** Microtasks are invoked explicitly. */
    EXPLICIT,

    /** Microtasks are invoked when the outermost MicrotasksScope ends. */
    SCOPED,

    /** Microtasks are invoked when the JavaScript call stack becomes empty. */
    AUTO,
}

/**
 * Represents the microtask queue, where microtasks are stored and processed.
 */
class MicrotaskQueue(
    val policy: MicrotasksPolicy = MicrotasksPolicy.AUTO,
) {
    private val queue = mutableListOf<() -> Unit>()
    private var running = false
    private var scopeDepth = 0

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

    companion object {
        fun new(policy: MicrotasksPolicy = MicrotasksPolicy.AUTO): MicrotaskQueue =
            MicrotaskQueue(policy)
    }
}
