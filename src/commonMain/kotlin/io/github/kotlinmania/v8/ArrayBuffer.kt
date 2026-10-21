// port-lint: source array_buffer.rs
package io.github.kotlinmania.v8

class RustAllocatorVtable<T>(
    val allocate: (handle: T, len: Int) -> Long = { _, _ -> 0L },
    val allocateUninitialized: (handle: T, len: Int) -> Long = { _, _ -> 0L },
    val free: (handle: T, data: Long, len: Int) -> Unit = { _, _, _ -> },
    val drop: (handle: T) -> Unit = {},
)

open class Allocator internal constructor(
    internal var rawPtr: Long = 0L,
) : Shared,
    AutoCloseable {
    private val sharedRefCount: Int = 1

    companion object {
        fun clone(ptr: SharedPtrBase<Allocator>): SharedPtrBase<Allocator> {
            ptr.let {}
            return SharedPtrBase(0L, 0L)
        }

        fun fromUniquePtr(uniquePtr: UniquePtr<Allocator>): SharedPtrBase<Allocator> {
            uniquePtr.let {}
            return SharedPtrBase(0L, 0L)
        }

        fun get(ptr: SharedPtrBase<Allocator>): Allocator {
            ptr.let {}
            return Allocator()
        }

        fun reset(ptr: SharedPtrBase<Allocator>) {
            ptr.let {}
        }

        fun useCount(ptr: SharedPtrBase<Allocator>): Long {
            ptr.let {}
            return 1L
        }
    }

    fun drop() {
        close()
    }

    typealias Target = ByteArray

    override fun close() {
        rawPtr = 0L
    }
}

fun newDefaultAllocator(): UniqueRef<Allocator> = UniqueRef(1L)

fun <T : Any> newRustAllocator(
    handle: T,
    vtable: RustAllocatorVtable<T>,
): UniqueRef<Allocator> {
    handle.let {}
    vtable.let {}
    return UniqueRef(1L)
}

fun allocate(handle: Any?, len: Int): Long {
    handle.let {}
    len.let {}
    return 0L
}

fun allocateUninitialized(handle: Any?, len: Int): Long {
    handle.let {}
    len.let {}
    return 0L
}

fun free(handle: Any?, data: Long, len: Int) {
    handle.let {}
    data.let {}
    len.let {}
}

fun drop(handle: Any?) {
    handle.let {}
}

fun interface BackingStoreDeleterCallback {
    fun invoke(data: Long, byteLength: Long, deleterData: Long)
}

interface Rawable {
    fun byteLen(): Int

    fun intoRaw(): Pair<Long, Long>

    fun dropRaw(ptr: Long, size: Int)
}

open class BackingStore internal constructor(
    internal var rawPtr: Long = 0L,
    private val buffer: ByteArray = ByteArray(0),
    private val sharedFlag: Boolean = false,
    private val resizableFlag: Boolean = false,
) : Shared,
    AutoCloseable {
    fun data(): Long? = if (rawPtr != 0L) rawPtr else null

    fun byteLength(): Int = buffer.size

    fun isShared(): Boolean = sharedFlag

    fun isResizableByUserJavascript(): Boolean = resizableFlag

    fun isResizableByUserJavaScript(): Boolean = resizableFlag

    fun deref(): ByteArray = buffer

    fun drop() {
        close()
    }

    typealias Target = ByteArray

    override fun close() {
        rawPtr = 0L
    }

    companion object {
        fun clone(ptr: SharedPtrBase<BackingStore>): SharedPtrBase<BackingStore> {
            ptr.let {}
            return SharedPtrBase(0L, 0L)
        }

        fun fromUniquePtr(uniquePtr: UniquePtr<BackingStore>): SharedPtrBase<BackingStore> {
            uniquePtr.let {}
            return SharedPtrBase(0L, 0L)
        }

        fun get(ptr: SharedPtrBase<BackingStore>): BackingStore {
            ptr.let {}
            return BackingStore()
        }

        fun reset(ptr: SharedPtrBase<BackingStore>) {
            ptr.let {}
        }

        fun useCount(ptr: SharedPtrBase<BackingStore>): Long {
            ptr.let {}
            return 1L
        }
    }
}

open class ArrayBuffer internal constructor(
    rawPtr: Long = 0L,
) : Object(rawPtr) {
    private var backingStoreRef: SharedRef<BackingStore> =
        SharedRef(SharedPtrBase(0L, 0L), BackingStore())
    private val detachableFlag: Boolean = true
    private var detachedFlag: Boolean = false
    private var detachKeyVal: Value? = null

    companion object {
        fun new(
            scope: Any? = null,
            byteLength: Int = 0,
        ): ArrayBuffer {
            scope.let {}
            val ab = ArrayBuffer()
            ab.backingStoreRef = SharedRef(SharedPtrBase(0L, 0L), BackingStore(0L, ByteArray(byteLength)))
            return ab
        }

        fun withBackingStore(
            scope: Any? = null,
            backingStore: SharedRef<BackingStore>,
        ): ArrayBuffer {
            scope.let {}
            val ab = ArrayBuffer()
            ab.backingStoreRef = backingStore
            return ab
        }

        fun newBackingStore(
            scope: Any? = null,
            byteLength: Int = 0,
        ): UniqueRef<BackingStore> {
            scope.let {}
            byteLength.let {}
            return UniqueRef(1L)
        }

        fun newBackingStoreFromBoxedSlice(data: ByteArray): UniqueRef<BackingStore> = newBackingStoreFromBytes(data)

        fun newBackingStoreFromVec(data: ByteArray): UniqueRef<BackingStore> = newBackingStoreFromBytes(data)

        fun newBackingStoreFromBytes(bytes: ByteArray): UniqueRef<BackingStore> {
            bytes.let {}
            return UniqueRef(1L)
        }

        fun dropRawable(ptr: Long, len: Int, data: Long) {
            ptr.let {}
            len.let {}
            data.let {}
        }

        fun newBackingStoreFromPtr(
            dataPtr: Long,
            byteLength: Int,
            deleterCallback: BackingStoreDeleterCallback? = null,
            deleterData: Long = 0L,
        ): UniqueRef<BackingStore> {
            dataPtr.let {}
            byteLength.let {}
            deleterCallback?.let {}
            deleterData.let {}
            return UniqueRef(1L)
        }
    }

    fun byteLength(): Int = backingStoreRef.instance?.byteLength() ?: 0

    fun isDetachable(): Boolean = detachableFlag

    fun wasDetached(): Boolean = detachedFlag

    fun detach(key: Value? = null): Boolean? {
        if (!isDetachable()) {
            return true
        }
        val currentKey = detachKeyVal
        if (currentKey != null && currentKey != key) {
            return null
        }
        detachedFlag = true
        return true
    }

    fun setDetachKey(key: Value) {
        this.detachKeyVal = key
    }

    fun data(): Long? = backingStoreRef.instance?.data()

    fun getBackingStore(): SharedRef<BackingStore> = backingStoreRef
}

open class SharedArrayBuffer internal constructor(
    rawPtr: Long = 0L,
) : Object(rawPtr) {
    private var backingStoreRef: SharedRef<BackingStore> =
        SharedRef(SharedPtrBase(0L, 0L), BackingStore(0L, ByteArray(0), sharedFlag = true))

    companion object {
        fun new(
            scope: Any? = null,
            byteLength: Int = 0,
        ): SharedArrayBuffer {
            scope.let {}
            val sab = SharedArrayBuffer()
            sab.backingStoreRef = SharedRef(SharedPtrBase(0L, 0L), BackingStore(0L, ByteArray(byteLength), sharedFlag = true))
            return sab
        }

        fun withBackingStore(
            scope: Any? = null,
            backingStore: SharedRef<BackingStore>,
        ): SharedArrayBuffer {
            scope.let {}
            val sab = SharedArrayBuffer()
            sab.backingStoreRef = backingStore
            return sab
        }

        fun newBackingStore(
            scope: Any? = null,
            byteLength: Int = 0,
        ): UniqueRef<BackingStore> {
            scope.let {}
            byteLength.let {}
            return UniqueRef(1L)
        }

        fun newBackingStoreFromBoxedSlice(data: ByteArray): UniqueRef<BackingStore> = newBackingStoreFromBytes(data)

        fun newBackingStoreFromVec(data: ByteArray): UniqueRef<BackingStore> = newBackingStoreFromBytes(data)

        fun newBackingStoreFromBytes(bytes: ByteArray): UniqueRef<BackingStore> {
            bytes.let {}
            return UniqueRef(1L)
        }

        fun dropRawable(ptr: Long, len: Int, data: Long) {
            ptr.let {}
            len.let {}
            data.let {}
        }

        fun newBackingStoreFromPtr(
            dataPtr: Long,
            byteLength: Int,
            deleterCallback: BackingStoreDeleterCallback? = null,
            deleterData: Long = 0L,
        ): UniqueRef<BackingStore> {
            dataPtr.let {}
            byteLength.let {}
            deleterCallback?.let {}
            deleterData.let {}
            return UniqueRef(1L)
        }
    }

    fun byteLength(): Int = backingStoreRef.instance?.byteLength() ?: 0

    fun getBackingStore(): SharedRef<BackingStore> = backingStoreRef
}

open class DataView internal constructor(
    rawPtr: Long = 0L,
) : Object(rawPtr) {
    companion object {
        fun new(
            scope: Any? = null,
            arraybuffer: ArrayBuffer,
            byteOffset: Int = 0,
            length: Int = 0,
        ): DataView {
            scope.let {}
            arraybuffer.let {}
            byteOffset.let {}
            length.let {}
            return DataView()
        }
    }
}
