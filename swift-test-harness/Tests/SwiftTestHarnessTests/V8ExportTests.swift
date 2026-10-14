import Testing
import V8

@Suite("V8 Export Smoke Tests")
struct V8ExportTests {
    @Test("Swift module loads cleanly and invokes C bindings")
    func testSwiftModuleLoads() throws {
        let attr = PropertyAttribute.Companion.shared.NONE
        #expect(attr.isNone())

        let filter = PropertyFilter.Companion.shared.ALL_PROPERTIES
        #expect(filter.isAllProperties())

        let flags = PropertyHandlerFlags.Companion.shared.NONE
        #expect(flags.isNone())

        let policy = MicrotasksPolicy.AUTO
        #expect(policy.description == "AUTO")

        let major = V8Binding.shared.majorVersion()
        #expect(major == 14)

        let version = V8Engine.shared.getVersion()
        #expect(version.hasPrefix("14.6."))
    }
}
