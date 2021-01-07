/*
 * Copyright 2020 Slawomir Czerwinski
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
 *
 */

package graphics.glimpse.meshes.obj

internal val triangleObj: String =
    """
        # triangle.obj
        mtllib triangle.mtl
        o Triangle
        v -1.0 -1.0 0.0
        v 1.0 1.0 0.0
        v 0.0 0.0 1.0
        vt 0.0 0.0
        vt 2.0 0.0
        vt 1.0 1.0
        vn 0.0 -1.0 0.0
        vn 1.0 0.0 0.0
        vn 0.7 -0.7 0.0
        usemtl Red
        s 1
        f 1/1/1 2/2/2 3/3/3
    """.trimIndent()
