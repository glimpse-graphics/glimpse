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

import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

fun writeFile(file: File, string: String) {
    FileWriter(file).use { writer -> writer.write(string) }
}

fun createDirectories(file: File) {
    Files.createDirectories(file.toPath())
}

fun deleteDirectory(directory: File) {
    if (directory.exists()) {
        Files.walkFileTree(
            directory.toPath(),
            object : SimpleFileVisitor<Path>() {

                override fun visitFile(file: Path, attrs: BasicFileAttributes?): FileVisitResult {
                    Files.deleteIfExists(file)
                    return FileVisitResult.CONTINUE
                }

                override fun postVisitDirectory(dir: Path, exc: IOException?): FileVisitResult {
                    Files.deleteIfExists(dir)
                    return FileVisitResult.CONTINUE
                }
            }
        )
    }
}
