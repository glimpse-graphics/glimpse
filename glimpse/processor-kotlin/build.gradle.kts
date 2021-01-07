plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt") version "1.15.0"
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
}

repositories {
    google()
}

dependencies {
    api(project(":glimpse:processor"))
    implementation("com.squareup:kotlinpoet:1.7.2")
}

detekt {
    input = files(kotlin.sourceSets.flatMap { it.kotlin.sourceDirectories })
    config = files("$rootDir/.config/detekt.yml")
    buildUponDefaultConfig = true
    reports {
        xml {
            enabled = true
            destination = file("$buildDir/reports/detekt.xml")
        }
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt.html")
        }
    }
}

tasks {

    withType(Test::class.java) {
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    dokkaHtml {
        moduleName.set("${project.parent?.name}-${project.name}")
        outputDirectory.set(buildDir.resolve("javadoc"))
        dokkaSourceSets {
            removeAll { it.displayName.get() == "androidJvm" }
            forEach { it.includes.from(files("module.md", "packages.md")) }
        }
    }

    val javadocJar = create<Jar>("javadocJar") {
        dependsOn.add(dokkaHtml)
        archiveClassifier.set("javadoc")
        from(dokkaHtml)
    }

    val sourcesJar = create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets.named("main").get().allJava.srcDirs)
    }

    artifacts {
        archives(javadocJar)
        archives(sourcesJar)
    }
}

afterEvaluate {
    publishing {
        publications {
            register("libJar", MavenPublication::class) {
                from(project.components["kotlin"])
                artifactId = "${project.parent?.name}-${artifactId}"
                artifact(tasks["javadocJar"])
                artifact(tasks["sourcesJar"])
                pom {
                    name.set("Glimpse ${project.name.capitalize()}")
                    description.set("OpenGL made simple")
                    url.set("https://glimpse.graphics/")
                    scm {
                        connection.set("scm:git:https://github.com/glimpse-graphics/glimpse.git")
                        developerConnection.set("scm:git:https://github.com/glimpse-graphics/glimpse.git")
                        url.set("https://github.com/glimpse-graphics/glimpse")
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
                        url.set("https://github.com/glimpse-graphics/glimpse/issues")
                    }
                    ciManagement {
                        system.set("GitHub Actions")
                        url.set("https://github.com/glimpse-graphics/glimpse/actions")
                    }
                }
            }
        }

        repositories {
            maven {
                if (System.getenv("SONATYPE_USERNAME") != null) {
                    val isSnapshot = project.version.toString().endsWith("SNAPSHOT")
                    url = project.uri(
                        if (isSnapshot) "https://oss.sonatype.org/content/repositories/snapshots/"
                        else "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
                    )
                    credentials {
                        username = System.getenv("SONATYPE_USERNAME")
                        password = System.getenv("SONATYPE_PASSWORD")
                    }
                } else {
                    url = project.uri("${buildDir}/maven")
                }
            }
        }
    }

    if (project.hasProperty("signing.keyId")) {
        signing {
            sign(
                *publishing.publications
                    .filterIsInstance<MavenPublication>()
                    .toTypedArray()
            )
        }
    }
}
