/*
 * Copyright 2020-2022 Slawomir Czerwinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.textures

import java.awt.image.BufferedImage
import java.io.File

/**
 * Will build a texture source from a given [file].
 */
fun TextureImageSourceBuilder.fromFile(file: File): TextureImageSourceBuilder = this
    .withFilename(file.name)
    .fromInputStream { file.inputStream() }

/**
 * Will build a texture source from a resource with a given [name], associated with a given [owner].
 */
fun TextureImageSourceBuilder.fromResource(owner: Any, name: String): TextureImageSourceBuilder = this
    .withFilename(name)
    .fromInputStream { owner.javaClass.getResourceAsStream(name) }

/**
 * Will build a texture source from a given [bufferedImage].
 *
 * @since v1.2.0
 */
fun TextureImageSourceBuilder.fromBufferedImage(bufferedImage: BufferedImage): TextureImageSourceBuilder = this
    .fromBufferedImage { bufferedImage }
