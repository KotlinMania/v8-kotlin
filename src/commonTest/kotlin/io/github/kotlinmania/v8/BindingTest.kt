// port-lint: tests binding.rs
package io.github.kotlinmania.v8

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BindingTest {
    @Test
    fun testVersionAndLayoutConstantsMatchCBinding() {
        assertEquals(MAJOR_VERSION, V8Binding.majorVersion())
        assertEquals(MINOR_VERSION, V8Binding.minorVersion())
        assertEquals(BUILD_NUMBER, V8Binding.buildNumber())
        assertEquals(PATCH_LEVEL, V8Binding.patchLevel())
        assertEquals(VERSION_STRING, V8Binding.versionString())

        assertEquals(SCRIPT_ORIGIN_SIZE, V8Binding.scriptOriginSize())
        assertEquals(CPPGC_MEMBER_SIZE, V8Binding.cppgcMemberSize())
        assertEquals(CPPGC_WEAK_MEMBER_SIZE, V8Binding.cppgcWeakMemberSize())
        assertEquals(TRACED_REFERENCE_SIZE, V8Binding.tracedReferenceSize())
        assertEquals(ETERNAL_SIZE, V8Binding.eternalSize())
        assertEquals(STRING_VALUE_VIEW_SIZE, V8Binding.stringValueViewSize())
        assertEquals(STRING_MAX_LENGTH, V8Binding.stringMaxLength())
        assertEquals(TYPED_ARRAY_MAX_BYTE_LENGTH, V8Binding.typedArrayMaxByteLength())
        assertEquals(TYPED_ARRAY_MAX_SIZE_IN_HEAP, V8Binding.typedArrayMaxSizeInHeap())
        assertEquals(UINT8_ARRAY_MAX_LENGTH, V8Binding.uint8ArrayMaxLength())
        assertEquals(UINT16_ARRAY_MAX_LENGTH, V8Binding.uint16ArrayMaxLength())
        assertEquals(UINT32_ARRAY_MAX_LENGTH, V8Binding.uint32ArrayMaxLength())
        assertEquals(BIG_UINT64_ARRAY_MAX_LENGTH, V8Binding.bigUint64ArrayMaxLength())
        assertEquals("InternalNode", CPPGC_HIDDEN_NAME)
        assertEquals("<No name>", CPPGC_NO_NAME_DEDUCIBLE)
    }

    @Test
    fun testWriteFlagsAndGcFlags() {
        val wf = WriteFlags.NULL_TERMINATE or WriteFlags.REPLACE_INVALID_UTF8
        assertTrue(wf.contains(WriteFlags.NULL_TERMINATE))
        assertTrue(wf.contains(WriteFlags.REPLACE_INVALID_UTF8))
        assertEquals(3u, wf.bits)

        val gcType = GCType.SCAVENGE or GCType.MARK_SWEEP_COMPACT
        assertTrue(gcType.contains(GCType.SCAVENGE))
        assertTrue(GCType.ALL.contains(gcType))

        val gcFlags = GCCallbackFlags.FORCED or GCCallbackFlags.COLLECT_ALL_AVAILABLE_GARBAGE
        assertTrue(gcFlags.contains(GCCallbackFlags.FORCED))
        assertFalse(gcFlags.contains(GCCallbackFlags.LAST_RESORT))
    }

    @Test
    fun testFastApiTypesAndEnums() {
        val arg0 = CTypeInfo(CTypeInfoType.V8Value)
        val arg1 = CTypeInfo(CTypeInfoType.Int32, CTypeInfoFlags.ENFORCE_RANGE_BIT or CTypeInfoFlags.CLAMP_BIT)
        assertTrue(arg1.flags.contains(CTypeInfoFlags.ENFORCE_RANGE_BIT))
        assertTrue(arg1.flags.contains(CTypeInfoFlags.CLAMP_BIT))

        val fnInfo =
            CFunctionInfo(
                returnInfo = CTypeInfo(CTypeInfoType.Bool),
                argumentInfos = listOf(arg0, arg1),
                repr = Int64Representation.BigInt,
            )
        assertEquals(2u, fnInfo.argumentCount)
        assertEquals(arg0, fnInfo.argumentInfo(0u))
        assertEquals(arg1, fnInfo.argumentInfo(1u))
        assertNull(fnInfo.argumentInfo(2u))
        assertEquals(Int64Representation.BigInt, fnInfo.repr)

        val cfn = CFunction(address = 0x12345678L, typeInfo = fnInfo)
        assertEquals(0x12345678L, cfn.address)
        assertEquals(fnInfo, cfn.typeInfo)

        val fastStr = FastOneByteString("hello")
        assertEquals(5u, fastStr.length)
        assertEquals(0u, Intercepted.Yes.code)
        assertEquals(1u, Intercepted.No.code)
        assertEquals(2, ModuleImportPhase.Evaluation.code)
        assertEquals(2, OverloadResolution.AtCompileTime.code)
        assertEquals(184u, UseCounterFeature.UseCounterFeatureCount.code)
    }
}
