/*
 * Copyright 2020-2021 Slawomir Czerwinski
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

package graphics.glimpse

plugins {
    kotlin("jvm")
    id("graphics.glimpse.test.logging")
    id("graphics.glimpse.detekt")
    id("graphics.glimpse.dokka")
    id("graphics.glimpse.sonatype")
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
        archives(
            create<Jar>("sourcesJar") {
                archiveClassifier.set("sources")
                from(sourceSets.named("main").get().allJava.srcDirs)
            }
        )
    }
}

afterEvaluate {
    publishing {
        publications {
            register("libraryJar", MavenPublication::class) {
                from(project.components["kotlin"])

                artifact(project.tasks["javadocJar"])
                artifact(project.tasks["sourcesJar"])

                artifactId = "${project.parent?.name}-${project.name}"
                groupId = "${project.group}"
                version = "${project.version}"

                pom {
                    val gitUrl = "https://github.com/glimpse-graphics/glimpse"

                    name.set("Glimpse ${project.name.replace('-', ' ').capitalize()}")
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
        signing.sign(*mavenPublications.toTypedArray())
    }
}
