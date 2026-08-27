// port-lint: source v8/src/symbol.rs
package io.github.kotlinmania.v8

/**
 * A JavaScript Symbol (ECMA-262 edition 6).
 */
class Symbol private constructor(
    private val desc: String?,
) : Name {
    fun description(): String? = desc

    override fun isSymbol(): Boolean = true

    override fun toString(): String = "Symbol(${desc ?: ""})"

    override fun equals(other: Any?): Boolean = this === other

    override fun hashCode(): Int = super.hashCode()

    companion object {
        fun new(description: String? = null): Symbol = Symbol(description)

        private val globalRegistry = mutableMapOf<String, Symbol>()
        private val apiRegistry = mutableMapOf<String, Symbol>()

        fun forKey(description: String): Symbol =
            globalRegistry.getOrPut(description) { Symbol(description) }

        fun forApi(description: String): Symbol =
            apiRegistry.getOrPut(description) { Symbol(description) }

        private val _asyncIterator: Symbol by lazy { Symbol("Symbol.asyncIterator") }
        private val _hasInstance: Symbol by lazy { Symbol("Symbol.hasInstance") }
        private val _isConcatSpreadable: Symbol by lazy { Symbol("Symbol.isConcatSpreadable") }
        private val _iterator: Symbol by lazy { Symbol("Symbol.iterator") }
        private val _match: Symbol by lazy { Symbol("Symbol.match") }
        private val _replace: Symbol by lazy { Symbol("Symbol.replace") }
        private val _search: Symbol by lazy { Symbol("Symbol.search") }
        private val _split: Symbol by lazy { Symbol("Symbol.split") }
        private val _toPrimitive: Symbol by lazy { Symbol("Symbol.toPrimitive") }
        private val _toStringTag: Symbol by lazy { Symbol("Symbol.toStringTag") }
        private val _unscopables: Symbol by lazy { Symbol("Symbol.unscopables") }

        fun getAsyncIterator(): Symbol = _asyncIterator
        fun getHasInstance(): Symbol = _hasInstance
        fun getIsConcatSpreadable(): Symbol = _isConcatSpreadable
        fun getIterator(): Symbol = _iterator
        fun getMatch(): Symbol = _match
        fun getReplace(): Symbol = _replace
        fun getSearch(): Symbol = _search
        fun getSplit(): Symbol = _split
        fun getToPrimitive(): Symbol = _toPrimitive
        fun getToStringTag(): Symbol = _toStringTag
        fun getUnscopables(): Symbol = _unscopables
    }
}
