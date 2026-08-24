import Testing
import V8

@Suite("V8 Export Smoke Tests")
struct V8ExportTests {
    @Test("Swift module loads cleanly")
    func testSwiftModuleLoads() throws {
        #expect(true)
    }
}
