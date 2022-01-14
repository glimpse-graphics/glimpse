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

package graphics.glimpse.shaders.annotations

/**
 * Attribute role.
 *
 * Determines which buffer should be used with the attribute.
 */
enum class AttributeRole {

    /**
     * Positions of vertices.
     */
    POSITIONS,

    /**
     * Texture coordinates.
     */
    TEX_COORDS,

    /**
     * Normal vectors.
     */
    NORMALS,

    /**
     * Tangent vectors.
     */
    TANGENTS,

    /**
     * Bitangent vectors.
     *
     * @since v1.1.0
     */
    BITANGENTS
}
