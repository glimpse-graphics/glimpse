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

package graphics.glimpse.gradle

import org.gradle.internal.impldep.com.esotericsoftware.kryo.io.ByteBufferOutputStream
import java.io.DataOutputStream
import java.nio.ByteBuffer

const val EMPTY: String = ""
const val BUILD_JVM: String = """
            plugins {
                id("org.jetbrains.kotlin.jvm")
                id("graphics.glimpse.jvm")
            }
        """
const val BUILD_ANDROID: String = """
            plugins {
                id("com.android.library")
                id("org.jetbrains.kotlin.android")
                id("graphics.glimpse.android")
            }
            android {
                compileSdk = 31
            }
        """
const val TRIANGLE: String = """
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
        """
const val TEXT = "Foo"

const val EXPECTED_DATA_SIZE = 3

@Suppress("MagicNumber")
val EXPECTED_DATA = floatArrayOf(
    -1f, -1f, 0f, 1f, 1f, 0f, 0f, 0f, 1f, // positions
    0f, 0f, 2f, 0f, 1f, 1f, // texture coordinates
    0f, -1f, 0f, 1f, 0f, 0f, 0.7f, -0.7f, 0f, // normals
    1f, 1f, 0f, 1f, 1f, 0f, 1f, 1f, 0f, // tangents
    0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f // bitangents
)

val expectedMeshDataBytes: ByteArray by lazy {
    val buffer = ByteBuffer.allocate(Int.SIZE_BYTES + EXPECTED_DATA.size * Float.SIZE_BYTES)
    val outputStream = DataOutputStream(ByteBufferOutputStream(buffer))
    outputStream.use { out ->
        out.writeInt(EXPECTED_DATA_SIZE)
        for (float in EXPECTED_DATA) {
            out.writeFloat(float)
        }
        out.flush()
    }
    return@lazy buffer.array()
}
