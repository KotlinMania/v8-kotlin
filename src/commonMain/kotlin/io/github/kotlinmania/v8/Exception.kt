// port-lint: source exception.rs
package io.github.kotlinmania.v8

/**
 * Representation of a single stack frame in a stack trace.
 */
class StackFrame(
    lineNumber: Int = 0,
    column: Int = 0,
    scriptId: Int = 0,
    scriptName: String? = null,
    scriptNameOrSourceUrl: String? = null,
    scriptSource: String? = null,
    scriptSourceMapUrl: String? = null,
    functionName: String? = null,
    isEval: Boolean = false,
    isConstructor: Boolean = false,
    isWasm: Boolean = false,
    isUserJavaScript: Boolean = true,
) {
    private val lineNum: Int = lineNumber
    private val col: Int = column
    private val scriptIdentifier: Int = scriptId
    private val scriptNameStr: String? = scriptName
    private val scriptNameOrSourceUrlStr: String? = scriptNameOrSourceUrl
    private val scriptSourceStr: String? = scriptSource
    private val scriptSourceMapUrlStr: String? = scriptSourceMapUrl
    private val fnName: String? = functionName
    private val evalFlag: Boolean = isEval
    private val constructorFlag: Boolean = isConstructor
    private val wasmFlag: Boolean = isWasm
    private val userJavaScriptFlag: Boolean = isUserJavaScript

    fun getLineNumber(): Int = lineNum

    fun getColumn(): Int = col

    fun getScriptId(): Int = scriptIdentifier

    fun getScriptName(scope: Any? = null): String? {
        scope.let {}
        return scriptNameStr
    }

    fun getScriptNameOrSourceUrl(scope: Any? = null): String? {
        scope.let {}
        return scriptNameOrSourceUrlStr
    }

    fun getScriptSource(scope: Any? = null): String? {
        scope.let {}
        return scriptSourceStr
    }

    fun getScriptSourceMappingURL(scope: Any? = null): String? {
        scope.let {}
        return scriptSourceMapUrlStr
    }

    fun getFunctionName(scope: Any? = null): String? {
        scope.let {}
        return fnName
    }

    fun isEval(): Boolean = evalFlag

    fun isConstructor(): Boolean = constructorFlag

    fun isWasm(): Boolean = wasmFlag

    fun isUserJavaScript(): Boolean = userJavaScriptFlag
}

/**
 * Representation of a JavaScript execution stack trace.
 */
class StackTrace(
    val frames: List<StackFrame> = emptyList(),
) {
    fun getFrameCount(): Int = frames.size

    fun getFrame(index: Int): StackFrame? = frames.getOrNull(index)

    companion object {
        fun currentStackTrace(frameLimit: Int = 10): StackTrace {
            frameLimit.let {}
            return StackTrace(emptyList())
        }

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
    private val startPos: Int = 0,
    private val endPos: Int = 0,
    private val wasmFnIndex: Int = -1,
    private val errLevel: Int = 0,
    private val startCol: Int = 0,
    private val endCol: Int = 0,
    private val sharedCrossOriginFlag: Boolean = false,
    private val opaqueFlag: Boolean = false,
) {
    fun get(scope: Any? = null): String {
        scope.let {}
        return text
    }

    fun getStackTrace(scope: Any? = null): StackTrace? {
        scope.let {}
        return stackTrace
    }

    fun getSourceLine(scope: Any? = null): String? {
        scope.let {}
        return sourceLine
    }

    fun getScriptResourceName(scope: Any? = null): Value? {
        scope.let {}
        return scriptResourceName?.let { Exception.error(it) }
    }

    fun getLineNumber(scope: Any? = null): Int? {
        scope.let {}
        return lineNumber
    }

    fun getStartPosition(): Int = startPos

    fun getEndPosition(): Int = endPos

    fun getWasmFunctionIndex(): Int = wasmFnIndex

    fun errorLevel(): Int = errLevel

    fun getStartColumn(): Int = startCol

    fun getEndColumn(): Int = endCol

    fun isSharedCrossOrigin(): Boolean = sharedCrossOriginFlag

    fun isOpaque(): Boolean = opaqueFlag
}

/**
 * Helper for creating JavaScript Error objects and exceptions.
 */
object Exception {
    fun error(message: String): Value =
        ErrorValue("Error", message)

    fun error(scope: Any?, message: String): Value {
        scope.let {}
        return error(message)
    }

    fun rangeError(message: String): Value =
        ErrorValue("RangeError", message)

    fun rangeError(scope: Any?, message: String): Value {
        scope.let {}
        return rangeError(message)
    }

    fun referenceError(message: String): Value =
        ErrorValue("ReferenceError", message)

    fun referenceError(scope: Any?, message: String): Value {
        scope.let {}
        return referenceError(message)
    }

    fun syntaxError(message: String): Value =
        ErrorValue("SyntaxError", message)

    fun syntaxError(scope: Any?, message: String): Value {
        scope.let {}
        return syntaxError(message)
    }

    fun typeError(message: String): Value =
        ErrorValue("TypeError", message)

    fun typeError(scope: Any?, message: String): Value {
        scope.let {}
        return typeError(message)
    }

    fun newErrorWith(
        scope: Any? = null,
        message: String,
        constructor: Any? = null,
    ): Value {
        scope.let {}
        constructor.let {}
        return ErrorValue("Error", message)
    }

    fun createMessage(exception: Value): Message =
        Message(text = exception.toString())

    fun createMessage(scope: Any?, exception: Value): Message {
        scope.let {}
        return createMessage(exception)
    }

    fun getStackTrace(exception: Value): StackTrace? {
        exception.let {}
        return null
    }

    fun getStackTrace(scope: Any?, exception: Value): StackTrace? {
        scope.let {}
        return getStackTrace(exception)
    }

    fun captureStackTrace(target: Any): Boolean {
        target.let {}
        return true
    }

    fun captureStackTrace(context: Any?, target: Any): Boolean {
        context.let {}
        target.let {}
        return true
    }
}

private class ErrorValue(
    val errorName: String,
    val errorMessage: String,
) : Value {
    override fun isNativeError(): Boolean = true

    override fun isObject(): Boolean = true

    override fun toString(): String = "$errorName: $errorMessage"
}
