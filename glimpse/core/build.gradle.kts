plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
}

kotlin {
    android {
        publishLibraryVariants("debug", "release")
    }

    jvm(name = "desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                compileOnly("androidx.annotation:annotation:1.2.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val desktopMain by getting {
            dependencies {
                compileOnly("org.jogamp.jogl:jogl-all-main:2.3.2")
                compileOnly("org.jogamp.gluegen:gluegen-rt-main:2.3.2")
                compileOnly("org.slf4j:slf4j-api:1.7.32")
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

detekt { setUpDetekt(project, kotlin.sourceSets.flatMap { it.kotlin.sourceDirectories }) }

android {
    compileSdkVersion(apiLevel = 31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(31)
    }
}

tasks {

    withType(Test::class.java) {
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    dokkaHtml { setUpDokkaTask(project) }

    artifacts {
        archives(createJavadocJar(dokkaHtml))
    }
}

afterEvaluate {
    publishing {
        publications { multiplatformPublications(project) }
        repositories { sonatype(project) }
    }
    signing { signAllMavenPublications(project, publishing) }
}
