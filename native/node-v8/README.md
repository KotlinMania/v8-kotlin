# N-API Node Addon (`project.ffi.napi`)

This directory houses the Node N-API C++ addon (`@kotlinmania/v8-native-bindings`) for the `js` and `wasmJs` targets when `project.ffi.napi=true` in `gradle.properties`.

- `binding.gyp` — compiles `src/v8_bindings.cpp` and `../../src/nativeInterop/cinterop/v8_wrapper.c` into `build/Release/v8_native.node`.
- `src/v8_bindings.cpp` — exposes the `v8_wrapper.h` C ABI functions to Node.js via `node-addon-api`.
- Built by `buildNodeV8` and copied into `build/js/node_modules/@kotlinmania/v8-native-bindings` by `copyNodeV8`.
