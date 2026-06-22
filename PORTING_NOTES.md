# Porting Notes

## `chromium_prelude` under `tmp/v8/build/rust/`

The `tmp/v8/build/rust/chromium_prelude/` subtree is vendored Chromium
`//build/rust` infrastructure, not part of the `v8` library crate. The
`v8` crate's `Cargo.toml` (`tmp/v8/Cargo.toml`) lists only `bitflags`,
`paste`, and `temporal_capi` as runtime dependencies — `syn`,
`proc_macro2`, and `quote` are not among them.

`chromium_prelude` provides the `chromium::import!{}` proc-macro used by
first-party Rust code inside the Chromium project to import other
first-party crates by their GN path. It is:

- A proc-macro crate (`rust_macro("import_attribute")` in `BUILD.gn`),
  built exclusively via GN, not Cargo. It has no `Cargo.toml`.
- Gated by `enable_chromium_prelude = build_with_chromium`, which is off
  for the standalone `v8` crate. The code only compiles when building
  with Chromium.
- Uses `syn`, `proc_macro2`, and `quote` for token parsing and code
  generation in `import_attribute.rs` (162 lines), plus `hmac_sha256`
  for GN-path-to-crate-name mangling.

The macro exists to work around Rust 2018's pre-`use` crate-name
ambiguity in the Chromium GN build graph — a Rust-specific problem that
does not exist in Kotlin. Kotlin's import system already does what
`chromium::import!` hacks around.

## Conclusion

No `syn-kotlin`, `proc-macro2-kotlin`, or `quote-kotlin` Maven dependency is
required for `v8-kotlin`. The `v8` library crate has no relationship to
`syn`. The `chromium_prelude` build tool is Chromium GN infrastructure, not
v8 library code, and is unportable per AGENTS.md §3.