buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
        classpath("org.jetbrains.compose:compose-gradle-plugin:0.3.1")
        classpath("com.android.tools.build:gradle:4.0.2")
    }
}

allprojects {

    val glimpseGroupId: String by project
    val glimpseVersion: String by project
    val glimpseVersionSuffix: String by project

    group = glimpseGroupId
    version =
        if (glimpseVersionSuffix.isBlank()) glimpseVersion
        else "$glimpseVersion-$glimpseVersionSuffix"

    repositories {
        google()
        jcenter()
        mavenCentral()
        // JB Composable dev versions:
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}

plugins {
    id("org.jetbrains.changelog") version "1.1.1"
    id("org.jetbrains.dokka")
}

changelog {
    version = "${project.version}"
}
