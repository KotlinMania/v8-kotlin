# port-lint Proposed Changes

**Generated:** 2026-08-24
**Source:** tmp/v8/src
**Target:** src/commonMain/kotlin

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/v8/PropertyAttribute.kt` | `// port-lint: source src/property_attribute.rs` | `// port-lint: source property_attribute.rs` | `property_attribute.rs` | `port-lint provenance header matched only after fallback normalization: 'src/property_attribute.rs' vs expected 'property_attribute.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/v8/PropertyAttributeTest.kt` | `// port-lint: source src/property_attribute.rs` | `// port-lint: source property_attribute.rs` | `property_attribute.rs` | `port-lint provenance header matched only after fallback normalization: 'src/property_attribute.rs' vs expected 'property_attribute.rs'` |
