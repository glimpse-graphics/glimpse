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

package graphics.glimpse.internal

import java.util.*

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("graphics.glimpse.internal.test.logging")
    id("graphics.glimpse.internal.detekt")
    id("graphics.glimpse.internal.dokka")
    id("graphics.glimpse.internal.sonatype")
    signing
}

tasks {
    artifacts {
        archives(
            create<Jar>("javadocJar") {
                dependsOn.add(dokkaHtml)
                archiveClassifier.set("javadoc")
                from(dokkaHtml)
            }
        )
    }
}

kotlin {
    android {
        publishLibraryVariants("debug", "release")
    }

    jvm(name = "desktop") {
        compilations.all {
            jvmToolchain(11)
            kotlinOptions.jvmTarget = "11"
        }
    }
}

android {
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

afterEvaluate {
    tasks.withType(Jar::class) {
        manifest {
            attributes["Built-By"] = System.getenv("GITHUB_ACTOR") ?: System.getProperty("user.name")
            attributes["Automatic-Module-Name"] = "${project.group}.${project.name.replace('-', '.')}"
        }
    }
    publishing {
        publications {
            filterIsInstance<MavenPublication>().forEach { publication ->
                publication.artifactId = "${project.parent?.name}-${publication.artifactId}"
                publication.artifact(project.tasks["javadocJar"])
                publication.pom {
                    val gitUrl = "https://github.com/glimpse-graphics/glimpse"

                    name.set("Glimpse ${
                        project.name.replace('-', ' ')
                            .replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                            }
                    }")
                    description.set("OpenGL made simple")
                    url.set("https://glimpse.graphics/")

                    scm {
                        connection.set("scm:git:$gitUrl.git")
                        developerConnection.set("scm:git:$gitUrl.git")
                        url.set(gitUrl)
                    }
                    licenses {
                        license {
                            name.set("The Apache Software License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0")
                        }
                    }
                    developers {
                        developer {
                            id.set("sczerwinski")
                            name.set("Slawomir Czerwinski")
                            email.set("slawomir@czerwinski.it")
                            url.set("https://czerwinski.it/")
                        }
                    }
                    issueManagement {
                        system.set("GitHub Issues")
                        url.set("$gitUrl/issues")
                    }
                    ciManagement {
                        system.set("GitHub Actions")
                        url.set("$gitUrl/actions")
                    }
                }
            }
        }
    }
    if (project.hasProperty("signing.keyId")) {
        val mavenPublications = publishing.publications.filterIsInstance<MavenPublication>()
        val signTasks = signing.sign(*mavenPublications.toTypedArray())
        tasks.withType(PublishToMavenRepository::class) {
            mustRunAfter(*signTasks.toTypedArray())
        }
    }
}
