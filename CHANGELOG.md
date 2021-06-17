# Changelog

## [Unreleased]
### Changed
- Upgrade Gradle Wrapper to `6.9`
- Dependencies:
  - Upgrade Kotlin to `1.5.10`
  - Upgrade JetBrains Compose for Desktop to `0.5.0-build225`
  - Upgrade Android Gradle Plugin to `4.1.3`
  - Upgrade `org.jetbrains.dokka` to `1.4.32`
  - Upgrade `kotlinpoet` to `1.8.0`
  - Upgrade `androidx.annotation:annotation` to `1.2.0`
  - Upgrade `mockk` to `1.11.0`
  - Upgrade `activity-compose` to `1.3.0-beta02`
  - Upgrade `kotlin-coroutines-android` to `1.5.0-native-mt`

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
