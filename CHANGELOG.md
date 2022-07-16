# Glimpse Changelog

## [Unreleased]
### Added
- Build `TextureImageSource` from `BufferedImage`

### Changed

### Deprecated

### Removed

### Fixed

### Security

## [1.1.0]
### Added
- Support for `Boolean` uniform shader parameters
  (uniform value in shader is still an `Int`, with a value of `GL_TRUE` or `GL_FALSE`):
  - Method `GlimpseAdapter.glUniform(Int, Boolean)`
  - Method `BaseProgramExecutor.glUniform(GlimpseAdapter, String, Boolean)`
- Method `ProgramExecutor.dispose(GlimpseAdapter)`, disposing also referenced `Program`
- Support for framebuffers
- Support bitangents shader attribute
- Additional parameters in composable `GlimpseView`:
  - `modifier` – the modifier to be applied to the layout
  - `onClick` – called when the view has been clicked
  - `update` – the callback to be invoked after the layout is inflated
- Composable `GlimpseView` function with separate functional parameters instead of `callback`
- Utility functions for creating `Vec3` and `Vec4` from Jetpack Compose `Color`
- Method `Vec2.dot(Vec2)`
- Method `Vec2.atan()`
- Method `Mat3.toMat2()`
- Method `Mat4.toMat2()`
- Factory function `Vec2.fromPolarCoordinates(Float, Angle)`
- Constants:
  - `Vec2.nullVector`
  - `Vec2.unitX`
  - `Vec2.unitY`
- KSP processor for shader parameters

### Changed
- Set texture parameters in texture builder
- Make Kapt processors incremental (isolating)
- Change target JVM to Java 11
- Upgrade Gradle Wrapper to `7.4.2`
- Dependencies:
  - Upgrade Kotlin to `1.6.10`
  - Upgrade KSP to `1.6.10-1.0.4`
  - Upgrade JetBrains Compose for Desktop to `1.1.1`
  - Upgrade Android Gradle Plugin to `7.0.4`
  - Upgrade `kotlinx-coroutines-android` to `1.6.1-native-mt`
  - Upgrade `androidx.appcompat:appcompat` to `1.4.1`
  - Upgrade `com.google.android.material:material` to `1.5.0`
  - Upgrade `androidx.annotation:annotation` to `1.3.0`
  - Upgrade `activity-compose` to `1.4.0`
  - Upgrade `kotlinpoet` to `1.11.0`
  - Upgrade `slf4j-api` to `1.7.36`
  - Upgrade `logback-core` to `1.2.11`
  - Upgrade `logback-classic` to `1.2.11`
  - Upgrade `mockk` to `1.12.3`
  - Upgrade `detekt-gradle-plugin` to `1.19.0`
  - Upgrade `org.jetbrains.changelog` to `1.3.1`
  - Upgrade `org.jetbrains.dokka` to `1.6.20`

### Deprecated
- Method `ProgramExecutor.dispose()` without parameters

### Removed
- **BREAKING CHANGE:** Remove `zOrderOnTop` from `GlimpseView` composable function.
  Call `zOrderOnTop = …` inside `update` block instead.

### Fixed
- Fix bitangent calculations
- Correct implementation of `toString()` for buffer data objects

## [1.0.0]
### Added
- Core types:
  - Angles
  - Vectors
  - Matrices, including transformation, view and projection matrices
  - Color conversion tools for AWT Color (Desktop) and Android
- Glimpse OpenGL adapter
- Glimpse logger
- Buffers
- Meshes
- Models
- Textures
- Shaders
- Cameras
- Lenses
- UI components:
  - `GlimpseSurfaceView` (Android)
  - `GlimpseFrame` (Desktop)
  - `GlimpsePanel` (Desktop)
- Composable UI components:
  - `GlimpseView`
- Generating program executors from annotated shader parameters class
- Support for Wavefront OBJ files
- Offscreen rendering

[Unreleased]: https://github.com/glimpse-graphics/glimpse/compare/v1.1.0...main
[1.1.0]: https://github.com/glimpse-graphics/glimpse/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.0.0