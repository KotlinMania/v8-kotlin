# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 13/58 (22.4%)
- **Function parity:** 145/1409 matched (target 210) — 10.3%
- **Class/type parity:** 9/370 matched (target 36) — 2.4%
- **Combined symbol parity:** 154/1779 matched (target 246) — 8.7%
- **Average inline-code cosine:** 0.38 (function body across 13 matched files)
- **Average documentation cosine:** 0.56 (doc text across 13 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 10 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. value
- **Similarity:** 0.19 (needs 66% improvement)
- **Dependencies:** 23
- **Priority Score:** 23207908.0
- **Functions:** 59/79 matched (target 60)
- **Missing functions:** `is_generator_object`, `same_value_zero`, `to_big_int`, `to_number`, `to_string`, `to_rust_string_lossy`, `to_detail_string`, `to_object`, `to_integer`, `to_uint32`, `to_int32`, `to_boolean`, `instance_of`, `number_value`, `integer_value`, `uint32_value`, `int32_value`, `boolean_value`, `type_of`, `type_repr`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Symbol Deficit:** 20 (functions: 20, types: 0)
- **Action:** Deep review - likely missing major functionality

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **context** (18 deps)
   - Path: `context.rs`
   - Essential for 18 other files

2. **string** (16 deps)
   - Path: `string.rs`
   - Essential for 16 other files

3. **isolate** (14 deps)
   - Path: `isolate.rs`
   - Essential for 14 other files

4. **object** (12 deps)
   - Path: `object.rs`
   - Essential for 12 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. value

- **Target:** `v8.Value`
- **Similarity:** 0.19
- **Dependents:** 23
- **Priority Score:** 23207908.0
- **Functions:** 59/79 matched (target 60)
- **Missing functions:** `is_generator_object`, `same_value_zero`, `to_big_int`, `to_number`, `to_string`, `to_rust_string_lossy`, `to_detail_string`, `to_object`, `to_integer`, `to_uint32`, `to_int32`, `to_boolean`, `instance_of`, `number_value`, `integer_value`, `uint32_value`, `int32_value`, `boolean_value`, `type_of`, `type_repr`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 2. data

- **Target:** `v8.Data`
- **Similarity:** 0.27
- **Dependents:** 9
- **Priority Score:** 9658407.0
- **Functions:** 17/18 matched (target 17)
- **Missing functions:** `fmt`
- **Types:** 2/66 matched (target 4)
- **Missing types:** `AccessorSignature`, `Context`, `FixedArray`, `Message`, `Module`, `ModuleRequest`, `PrimitiveArray`, `Private`, `Script`, `ScriptOrModule`, `Signature`, `StackFrame`, `StackTrace`, `Template`, `FunctionTemplate`, `ObjectTemplate`, `UnboundModuleScript`, `UnboundScript`, `Value`, `External`, `Object`, `Array`, `ArrayBuffer`, `ArrayBufferView`, `DataView`, `TypedArray`, `BigInt64Array`, `BigUint64Array`, `Float16Array`, `Float32Array`, `Float64Array`, `Int16Array`, `Int32Array`, `Int8Array`, `Uint16Array`, `Uint32Array`, `Uint8Array`, `Uint8ClampedArray`, `BigIntObject`, `BooleanObject`, `Date`, `Function`, `Map`, `NumberObject`, `Promise`, `PromiseResolver`, `Proxy`, `RegExp`, `Set`, `SharedArrayBuffer`, `StringObject`, `SymbolObject`, `WasmMemoryObject`, `WasmModuleObject`, `Primitive`, `BigInt`, `Boolean`, `Name`, `String`, `Symbol`, `Number`, `Integer`, `Int32`, `Uint32`

### 3. name

- **Target:** `v8.Name`
- **Similarity:** 0.24
- **Dependents:** 3
- **Priority Score:** 3000107.5
- **Functions:** 1/1 matched (target 2)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 4. exception

- **Target:** `v8.Exception`
- **Similarity:** 0.14
- **Dependents:** 2
- **Priority Score:** 2243708.5
- **Functions:** 12/36 matched (target 16)
- **Missing functions:** `get_line_number`, `get_column`, `get_script_id`, `get_script_name`, `get_script_name_or_source_url`, `get_script_source`, `get_script_source_mapping_url`, `get_function_name`, `is_eval`, `is_constructor`, `is_wasm`, `is_user_javascript`, `get`, `get_source_line`, `get_script_resource_name`, `get_start_position`, `get_end_position`, `get_wasm_function_index`, `error_level`, `get_start_column`, `get_end_column`, `is_shared_cross_origin`, `is_opaque`, `new_error_with`
- **Types:** 1/1 matched (target 6)
- **Missing types:** _none_

### 5. property_attribute

- **Target:** `v8.PropertyAttribute`
- **Similarity:** 0.67
- **Dependents:** 2
- **Priority Score:** 2021103.2
- **Functions:** 8/9 matched (target 12)
- **Missing functions:** `bitor`
- **Types:** 1/2 matched
- **Missing types:** `Output`
- **Tests:** 1/1 matched

### 6. property_filter

- **Target:** `v8.PropertyFilter`
- **Similarity:** 0.73
- **Dependents:** 2
- **Priority Score:** 2011202.8
- **Functions:** 10/10 matched (target 15)
- **Missing functions:** _none_
- **Types:** 1/2 matched
- **Missing types:** `Output`
- **Tests:** 1/1 matched

### 7. property_handler_flags

- **Target:** `v8.PropertyHandlerFlags`
- **Similarity:** 0.71
- **Dependents:** 1
- **Priority Score:** 1011002.9
- **Functions:** 8/8 matched (target 13)
- **Missing functions:** _none_
- **Types:** 1/2 matched
- **Missing types:** `Output`
- **Tests:** 1/1 matched

### 8. private

- **Target:** `v8.Private`
- **Similarity:** 0.31
- **Dependents:** 1
- **Priority Score:** 1010306.9
- **Functions:** 2/3 matched (target 7)
- **Missing functions:** `name`
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 9. isolate_create_params

- **Target:** `v8.IsolateCreateParams`
- **Similarity:** 0.33
- **Dependents:** 0
- **Priority Score:** 123006.7
- **Functions:** 16/26 matched (target 17)
- **Missing functions:** `counter_lookup_callback`, `snapshot_blob`, `array_buffer_allocator`, `has_set_array_buffer_allocator`, `external_references`, `stack_limit`, `set_stack_limit`, `cpp_heap`, `finalize`, `create_param_defaults`
- **Types:** 2/4 matched (target 3)
- **Missing types:** `CounterLookupCallback`, `CreateParamAllocations`
- **Tests:** 0/1 matched
- **Lint issues:** 1

### 10. primitives

- **Target:** `v8.Primitives`
- **Similarity:** 0.16
- **Dependents:** 0
- **Priority Score:** 20308.4
- **Functions:** 1/3 matched (target 18)
- **Missing functions:** `null`, `undefined`
- **Types:** 0/0 matched (target 5)
- **Missing types:** _none_

### 11. microtask

- **Target:** `v8.Microtask`
- **Similarity:** 0.30
- **Dependents:** 0
- **Priority Score:** 10707.0
- **Functions:** 5/6 matched
- **Missing functions:** `drop`
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_

### 12. symbol

- **Target:** `v8.Symbol`
- **Similarity:** 0.41
- **Dependents:** 0
- **Priority Score:** 405.9
- **Functions:** 4/4 matched (target 20)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 13. json

- **Target:** `v8.Json`
- **Similarity:** 0.43
- **Dependents:** 0
- **Priority Score:** 205.7
- **Functions:** 2/2 matched (target 7)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

