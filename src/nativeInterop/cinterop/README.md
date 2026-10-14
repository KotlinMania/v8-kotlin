# Native C Interop (`project.ffi.native`)

This directory houses the Kotlin/Native `cinterop` definition and per-target C wrapper for `v8-kotlin`, gated by `project.ffi.native=true` in `gradle.properties` with `project.ffi.name=V8`.

## Files

- `V8.def` — `cinterop` definition referencing `v8_wrapper.h` and `v8_wrapper.a`.
- `v8_wrapper.h` — C ABI declarations mirroring `tmp/v8/src/binding.hpp`, `tmp/v8/src/support.h`, and `tmp/v8/src/binding.cc` (`v8__V8__*`, `v8__Platform__*`, `v8__Isolate__*`, `v8_HeapStatistics`, `v8_HeapSpaceStatistics`, `v8_HeapCodeStatistics`, `v8_binding_*`, `v8_support_*`).
- `v8_wrapper.c` — C implementation compiled into `build/cinterop-targets/<konanTargetName>/v8_wrapper.a` by `compileV8WrapperFor<Target>`. Resolves symbols dynamically from `librusty_v8` when present and provides portable C ABI execution across all Kotlin/Native targets.
