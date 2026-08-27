// port-lint: source json.rs
package io.github.kotlinmania.v8

/**
 * A JSON Parser and Stringifier.
 */
object Json {
    /**
     * Tries to parse the string `jsonString` and returns it as a value if successful.
     */
    fun parse(jsonString: String): Value? {
        val trimmed = jsonString.trim()
        return when {
            trimmed == "null" -> Null
            trimmed == "true" -> V8Boolean.TRUE
            trimmed == "false" -> V8Boolean.FALSE
            trimmed.startsWith("\"") && trimmed.endsWith("\"") && trimmed.length >= 2 ->
                V8String(trimmed.substring(1, trimmed.length - 1))
            else -> null
        }
    }

    /**
     * Tries to stringify the JSON-serializable object `jsonObject` and returns it as a string if successful.
     */
    fun stringify(jsonObject: Value): String? =
        when (jsonObject) {
            is Null -> "null"
            is Undefined -> null
            is V8Boolean -> jsonObject.value.toString()
            is V8String -> "\"${jsonObject.value}\""
            else -> jsonObject.toString()
        }
}

/**
 * A JavaScript string.
 */
class V8String(
    val value: String,
) : Name {
    override fun isString(): Boolean = true

    override fun toString(): String = value

    override fun equals(other: Any?): Boolean =
        other is V8String && value == other.value

    override fun hashCode(): Int = value.hashCode()
}
