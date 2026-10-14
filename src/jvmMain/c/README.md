# JVM JNI C Bindings (`project.ffi.jvm`)

This directory houses the JNI shared library source for the JVM target when `project.ffi.jvm=true` in `gradle.properties`.

- `v8_jni.c` — includes `../../nativeInterop/cinterop/v8_wrapper.c` and exposes JNI entry points for `io.github.kotlinmania.v8.internal.V8Jni`.
- Compiled by the `compileV8Jni` Gradle task into `build/natives/` (`libv8_jni.dylib` / `libv8_jni.so` / `v8_jni.dll`) and wired into `jvmTest` via `-Djava.library.path`.
