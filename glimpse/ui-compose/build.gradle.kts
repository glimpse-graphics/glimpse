plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "0.0.0-unmerged-build21" // This version contains SwingPanel
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
            kotlinOptions.jvmTarget = "11"
        }
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":glimpse:core"))
                api(project(":glimpse:ui"))

                implementation(compose.runtime)
                implementation(compose.foundation)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val desktopMain by getting {
            dependencies {
                compileOnly("org.jogamp.jogl:jogl-all-main:2.3.2")
                compileOnly("org.jogamp.gluegen:gluegen-rt-main:2.3.2")
                compileOnly("org.slf4j:slf4j-api:1.7.30")
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
    compileSdkVersion(apiLevel = 30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
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
        publications { multiplatformPublications(project)}
        repositories { sonatype(project) }
    }
    signing { signAllMavenPublications(project, publishing) }
}
