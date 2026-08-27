// port-lint: source v8/src/exception.rs
package io.github.kotlinmania.v8

/**
 * Representation of a single stack frame in a stack trace.
 */
class StackFrame(
    val lineNumber: Int = 0,
    val column: Int = 0,
    val scriptId: Int = 0,
    val scriptName: String? = null,
    val scriptNameOrSourceUrl: String? = null,
    val scriptSource: String? = null,
    val scriptSourceMapUrl: String? = null,
    val functionName: String? = null,
    val isEval: Boolean = false,
    val isConstructor: Boolean = false,
    val isWasm: Boolean = false,
    val isUserJavaScript: Boolean = true,
)

/**
 * Representation of a JavaScript execution stack trace.
 */
class StackTrace(
    val frames: List<StackFrame> = emptyList(),
) {
    fun getFrameCount(): Int = frames.size

    fun getFrame(index: Int): StackFrame? = frames.getOrNull(index)

    companion object {
        fun currentStackTrace(frameLimit: Int = 10): StackTrace =
            StackTrace(emptyList())

        fun currentScriptNameOrSourceUrl(): String? = null
    }
}

/**
 * An error message.
 */
class Message(
    val text: String,
    val stackTrace: StackTrace? = null,
    val sourceLine: String? = null,
    val scriptResourceName: String? = null,
    val lineNumber: Int? = null,
    val startPosition: Int = 0,
    val endPosition: Int = 0,
    val wasmFunctionIndex: Int = -1,
    val errorLevel: Int = 0,
    val startColumn: Int = 0,
    val endColumn: Int = 0,
    val isSharedCrossOrigin: Boolean = false,
    val isOpaque: Boolean = false,
)

/**
 * Helper for creating JavaScript Error objects and exceptions.
 */
object Exception {
    fun error(message: String): Value =
        ErrorValue("Error", message)

    fun rangeError(message: String): Value =
        ErrorValue("RangeError", message)

    fun referenceError(message: String): Value =
        ErrorValue("ReferenceError", message)

    fun syntaxError(message: String): Value =
        ErrorValue("SyntaxError", message)

    fun typeError(message: String): Value =
        ErrorValue("TypeError", message)

    fun createMessage(exception: Value): Message =
        Message(text = exception.toString())

    fun getStackTrace(exception: Value): StackTrace? = null

    fun captureStackTrace(target: Any): Boolean = true
}

private class ErrorValue(
    val errorName: String,
    val errorMessage: String,
) : Value {
    override fun isNativeError(): Boolean = true
    override fun isObject(): Boolean = true
    override fun toString(): String = "$errorName: $errorMessage"
}
