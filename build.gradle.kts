buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
        classpath("com.android.tools.build:gradle:4.0.2")
    }
}

group = "graphics.glimpse"
version = "1.0.0"

allprojects {
    repositories {
        jcenter()
        mavenCentral()
    }
}

plugins {
    id("org.jetbrains.changelog") version "0.6.2"
}
