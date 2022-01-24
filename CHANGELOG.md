# Glimpse Changelog

## [Unreleased]
### Added

### Changed

### Deprecated

### Removed

### Fixed

### Security

## [1.1.0-ALPHA2]
### Added
- Support bitangents shader attribute

### Changed
- Set texture parameters in texture builder
- Dependencies:
  - Upgrade `slf4j-api` to `1.7.33`
  - Upgrade `org.jetbrains.dokka` to `1.6.10`
  - Upgrade `androidx.appcompat:appcompat` to `1.4.1`
  - Upgrade `com.google.android.material:material` to `1.5.0`

## [1.1.0-ALPHA1]
### Added
- Support for `Boolean` uniform shader parameters
  (uniform value in shader is still an `Int`, with a value of `GL_TRUE` or `GL_FALSE`):
  - Method `GlimpseAdapter.glUniform(Int, Boolean)`
  - Method `BaseProgramExecutor.glUniform(GlimpseAdapter, String, Boolean)`
- Method `ProgramExecutor.dispose(GlimpseAdapter)`, disposing also referenced `Program`
- Support for framebuffers
- Additional parameters in composable `GlimpseView`:
  - `modifier` – the modifier to be applied to the layout
  - `onClick` – called when the view has been clicked
  - `update` – the callback to be invoked after the layout is inflated
- Composable `GlimpseView` function with separate functional parameters instead of `callback`
- Utility functions for creating `Vec3` and `Vec4` from Jetpack Compose `Color`
- KSP processor for shader parameters

### Changed
- Make Kapt processors incremental (isolating)
- Change target JVM to Java 11
- Upgrade Gradle Wrapper to `7.3.2`
- Dependencies:
  - Upgrade Kotlin to `1.6.10`
  - Upgrade JetBrains Compose for Desktop to `1.0.1`
  - Upgrade Android Gradle Plugin to `7.0.4`
  - Upgrade `org.jetbrains.dokka` to `1.6.0`
  - Upgrade `kotlinpoet` to `1.10.2`
  - Upgrade `androidx.annotation:annotation` to `1.3.0`
  - Upgrade `mockk` to `1.12.2`
  - Upgrade `activity-compose` to `1.4.0`
  - Upgrade `kotlinx-coroutines-android` to `1.6.0-native-mt`
  - Upgrade `slf4j-api` to `1.7.32`
  - Upgrade `logback-core` to `1.2.10`
  - Upgrade `logback-classic` to `1.2.10`
  - Upgrade `androidx.appcompat:appcompat` to `1.4.0`
  - Upgrade `com.google.android.material:material` to `1.4.0`
  - Upgrade `detekt-gradle-plugin` to `1.19.0`
  - Upgrade `org.jetbrains.changelog` to `1.3.1`

### Deprecated
- Method `ProgramExecutor.dispose()` without parameters

### Removed
- **BREAKING CHANGE:** Remove `zOrderOnTop` from `GlimpseView` composable function.
  Call `zOrderOnTop = …` inside `update` block instead.

## [1.0.0]
### Changed
- Dependencies:
  - Upgrade JetBrains Compose for Desktop to `0.3.1`

## [1.0.0-RC1]
### Added
- `TextureImageSourceBuilder.buildPrepared()`

### Removed
- **BREAKING CHANGE:** Remove `filename` from `TextureImageSource`
- **BREAKING CHANGE:** Remove `withFilename` from Android implementation
  of `TextureImageSourceBuilder`

## [1.0.0-BETA2]
### Added
- Support for non-32-bpp texture images (Desktop)
- Matrix times vector multiplication

### Changed
- Use `ComponentActivity.setContent(...)` from `activity-compose`

### Fixed
- Flip texture image vertically on Android to match Desktop behaviour

## [1.0.0-BETA1]
### Added
- `GlimpseAdapter` methods:
  - `glEnableLineSmooth()`
  - `glDisableLineSmooth()`
  - `glEnableProgramPointSize()`
  - `glDisableProgramPointSize()`

### Changed
- Dependencies:
  - Upgrade Kotlin to `1.4.30`
  - Upgrade JetBrains Compose for Desktop to `0.3.0-build152`
  - Upgrade `org.jetbrains.changelog` to `1.1.1`
- Use a temporary workaround for the missing
  `ComponentActivity.setContent(...)` extension function

## [1.0.0-ALPHA4]
### Added
- `GlimpseAdapter.glLineWidth(float)` to set line width
- Blending-related methods in `GlimpseAdapter`:
  - `glEnableBlending`
  - `glDisableBlending`
  - `glBlendingFunction`
  - `glBlendingColor`
- Color conversion tools for AWT Color (Desktop) and Android
- Functions `Angle.atan()` and `Angle.atan2()`, returning `Angle`

### Changed
- Make `GLCapabilitiesFactory` internal in `glimpse-ui`
- Setting fixed surface scale in `GlimpsePanel` with parameter `fixedScale`
- Convert JogAmp and slf4j dependencies to `compileOnly`

## [1.0.0-ALPHA3]
### Changed
- Create `GlimpsePanel` with `GLCapabilities` for any existing `GLProfile`
  supporting OpenGL ES 2.0 and GLSL 1.00

## [1.0.0-ALPHA2]
### Added
- Java resource texture image source (Desktop)
- Offscreen rendering (Desktop)

### Fixed
- Publish Android artifacts for `debug` variant
- Fix IndexOutOfBoundsException in texture builder
- Fix loading texture image (Desktop)

## [1.0.0-ALPHA1]
### Added
- Core types:
  - Angles
  - Vectors
  - Matrices, including transformation, view and projection matrices
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
- Offscreen rendering (Android)

[Unreleased]: https://github.com/glimpse-graphics/glimpse/tree/main
[1.1.0-ALPHA2]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.1.0-ALPHA2
[1.1.0-ALPHA1]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.1.0-ALPHA1
[1.0.0]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.0.0
[1.0.0-RC1]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.0.0-RC1
[1.0.0-BETA2]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.0.0-BETA2
[1.0.0-BETA1]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.0.0-BETA1
[1.0.0-ALPHA4]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.0.0-ALPHA4
[1.0.0-ALPHA3]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.0.0-ALPHA3
[1.0.0-ALPHA2]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.0.0-ALPHA2
[1.0.0-ALPHA1]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.0.0-ALPHA1