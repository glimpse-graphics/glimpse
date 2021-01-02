import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "0.0.0-unmerged-build21"
    id("io.gitlab.arturbosch.detekt") version "1.15.0"
}

repositories {
    google()
}

kotlin {
    jvm(name = "desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":examples:triangle"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("org.jogamp.jogl:jogl-all-main:2.3.2")
                implementation("org.jogamp.gluegen:gluegen-rt-main:2.3.2")
                implementation("org.slf4j:slf4j-api:1.7.30")
                implementation("ch.qos.logback:logback-core:1.2.3")
                implementation("ch.qos.logback:logback-classic:1.2.3")
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
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

compose.desktop {
    application {
        mainClass = "graphics.glimpse.examples.triangle.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "desktop"
        }
        jvmArgs.add("--add-exports=jogl.all/com.jogamp.opengl.util=ALL-UNNAMED")
    }
}
