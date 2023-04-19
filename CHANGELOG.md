# Glimpse Changelog

## [Unreleased]
### Added
- `Double` buffer data wrapper: `DoubleBufferData`
- Vectors containing `Int` numbers
- Vectors containing `Long` numbers
- Vectors containing `Double` numbers
- Matrices containing `Double` numbers
- Extension functions `VecN<Int>.toIntArray()`
- Extension functions `VecN<Long>.toLongArray()`
- Extension functions `VecN<Double>.toDoubleArray()`
- Extension function `BaseMat<Double, M, V>.toDoubleArray()`
- Add support for `Int` vectors to `@Uniform` annotation
- Buffers for uniform data: `IntUniformBuffer`, `FloatUniformBuffer`
- Add support for `IntUniformBuffer` and `FloatUniformBuffer` to `@Uniform` annotation
- Add `Vec2.cross(Vec2): Vec3` method
- Methods: `Vec2.magnitude()`, `Vec3.magnitude()`, `Vec2.normalize()`, `Vec3.normalize()`
- Extension functions: `Color.toVec3()`, `Color.toVec4()`, `Int.toVec3()`, `Int.toVec4()`
- Extension functions: `Vec3.toColor()`, `Vec4.toColor()`, `Vec3.toColorInt()`, `vec4.toColorInt()`, `Vec3.toComposeColor()`, `Vec4.toComposeColor()`

### Changed
- **BREAKING CHANGE:** Add parameter type `T : Number` to `Angle` and `AngleRange`
- **BREAKING CHANGE:** Add parameter type `T : Number` to `Vec` and all inheriting types
- **BREAKING CHANGE:** Method `Vec.toFloatArray()` replaced with a set of extension functions `VecN<Float>.toFloatArray()`
- **BREAKING CHANGE:** Add parameter type `T : Number` to `Mat` and all inheriting types
- **BREAKING CHANGE:** Method `Mat.toFloatArray()` replaced with a set of extension functions `BaseMat<Float, M, V>.toFloatArray()`
- **BREAKING CHANGE:** Add parameter type `T : Number` to `Camera` and its implementations
- **BREAKING CHANGE:** Add parameter type `T : Number` to `Lens` and its implementations
- Upgrade Gradle Wrapper to `8.1`
- Dependencies:
  - Upgrade Kotlin to `1.8.20`
  - Upgrade KSP to `1.8.20-1.0.11`
  - Upgrade JetBrains Compose for Desktop to `1.4.0`
  - Upgrade Android Gradle Plugin to `7.4.2`
  - Upgrade JogAmp to `2.4.0`
  - Upgrade `kotlinx-coroutines-android` to `1.6.4`
  - Upgrade `androidx.appcompat:appcompat` to `1.6.1`
  - Upgrade `com.google.android.material:material` to `1.8.0`
  - Upgrade `androidx.annotation:annotation` to `1.6.0`
  - Upgrade `activity-compose` to `1.7.0`
  - Upgrade `kotlinpoet` to `1.13.0`
  - Upgrade `slf4j-api` to `2.0.7`
  - Upgrade `logback-core` to `1.4.6`
  - Upgrade `logback-classic` to `1.4.6`
  - Upgrade `mockk` to `1.13.5`
  - Upgrade `detekt-gradle-plugin` to `1.22.0`
  - Upgrade `org.jetbrains.changelog` to `2.0.0`
  - Upgrade `org.jetbrains.dokka` to `1.8.10`

### Deprecated
- Functions: `magnitude(Vec2)`, `magnitude(Vec3)`, `normalize(Vec2)`, `normalize(Vec3)`
- Functions: `Vec3(Color)`, `Vec4(Color)`, `Vec3(@ColorInt Int)`, `Vec4(@ColorInt Int)`

### Removed
- Method `ProgramExecutor.dispose()` without parameters

### Fixed

### Security

## [1.2.1]
### Fixed
- Correctly apply face culling mode

## [1.2.0]
### Added
- Utilities for rendering HUD-style 2D elements on top of 3D scene
- Build `TextureImageSource` from `BufferedImage`
- Build `TextureImageSource` containing text
- Properties `width` and `height` in `TextureImageSource` and `Texture`
- `@Sampler2D` annotation that can be used to annotate shader parameters
  properties of types:
  - `Texture`
  - `Array<Texture>`
  - `Iterable<Texture>`
- Method `GlimpseAdapter.glVSync(VSync)`, setting V-sync mode (if supported)
- Automatic module names for Java 9 (desktop and processor)

### Changed
- `@Uniform` can no longer be used to annotate properties of type `Texture`
  (`@Sampler2D` should be used instead)
- Upgrade Gradle Wrapper to `7.5`
- Dependencies:
  - Upgrade Kotlin to `1.7.20`
  - Upgrade KSP to `1.7.20-1.0.8`
  - Upgrade JetBrains Compose for Desktop to `1.2.1`
  - Upgrade Android Gradle Plugin to `7.2.1`
  - Upgrade `kotlinx-coroutines-android` to `1.6.3-native-mt`
  - Upgrade `androidx.appcompat:appcompat` to `1.5.1`
  - Upgrade `com.google.android.material:material` to `1.7.0`
  - Upgrade `androidx.annotation:annotation` to `1.5.0`
  - Upgrade `activity-compose` to `1.5.0`
  - Upgrade `kotlinpoet` to `1.12.0`
  - Upgrade `slf4j-api` to `2.0.3`
  - Upgrade `logback-core` to `1.4.4`
  - Upgrade `logback-classic` to `1.4.4`
  - Upgrade `mockk` to `1.13.2`
  - Upgrade `detekt-gradle-plugin` to `1.21.0`
  - Upgrade `org.jetbrains.dokka` to `1.7.10`

### Deprecated
- Usage of method `ProgramExecutor.dispose()` without parameters will be reported as an error

### Removed
- Remove Kapt processors (`glimpse-processor-java` and `glimpse-processor-kotlin`).
  _Use KSP processor instead (`glimpse-processor-ksp`)_

### Fixed
- Apply correct unpack pixel alignment for texture data before calling `glTexImage2D()`
- Remove misleading error after deleting program or shader

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

[Unreleased]: https://github.com/glimpse-graphics/glimpse/compare/v1.2.0...main
[1.2.1]: https://github.com/glimpse-graphics/glimpse/compare/v1.2.0...v1.2.1
[1.2.0]: https://github.com/glimpse-graphics/glimpse/compare/v1.1.0...v1.2.0
[1.1.0]: https://github.com/glimpse-graphics/glimpse/compare/v1.0.0...v1.1.0
[1.0.0]: https://github.com/glimpse-graphics/glimpse/releases/tag/v1.0.0