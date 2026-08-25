# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 1/58 (1.7%)
- **Function parity:** 8/1422 matched (target 12) — 0.6%
- **Class/type parity:** 1/370 matched (target 2) — 0.3%
- **Combined symbol parity:** 9/1792 matched (target 14) — 0.5%
- **Average inline-code cosine:** 0.67 (function body across 1 matched files)
- **Average documentation cosine:** 1.00 (doc text across 1 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 0 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **value** (23 deps)
   - Path: `value.rs`
   - Essential for 23 other files

2. **context** (18 deps)
   - Path: `context.rs`
   - Essential for 18 other files

3. **string** (16 deps)
   - Path: `string.rs`
   - Essential for 16 other files

4. **isolate** (14 deps)
   - Path: `isolate.rs`
   - Essential for 14 other files

5. **object** (12 deps)
   - Path: `object.rs`
   - Essential for 12 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. property_attribute

- **Target:** `v8.PropertyAttribute [PROVENANCE-FALLBACK]`
- **Similarity:** 0.67
- **Dependents:** 2
- **Priority Score:** 2021103.2
- **Functions:** 8/9 matched (target 12)
- **Missing functions:** `bitor`
- **Types:** 1/2 matched
- **Missing types:** `Output`
- **Tests:** 1/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/property_attribute.rs` vs expected `property_attribute.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/property_attribute.rs` vs expected `property_attribute.rs`
- **Proposed provenance header:** `// port-lint: source property_attribute.rs` (current: `// port-lint: source src/property_attribute.rs`)
- **Proposed provenance header:** `// port-lint: source property_attribute.rs` (current: `// port-lint: source src/property_attribute.rs`)
- **Lint issues:** 2

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |

