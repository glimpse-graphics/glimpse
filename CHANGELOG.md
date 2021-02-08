# Changelog

## [Unreleased]
### Changed
- Make `GLCapabilitiesFactory` internal in `glimpse-ui`
- Setting fixed surface scale in `GlimpsePanel` with parameter `fixedScale`

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
