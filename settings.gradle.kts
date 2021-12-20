pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        // JB Composable dev versions:
        maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
    }
}

rootProject.name = "glimpse-graphics"

include(":glimpse:core")
include(":glimpse:obj")
include(":glimpse:offscreen")
include(":glimpse:processor")
include(":glimpse:processor-java")
include(":glimpse:processor-kotlin")
include(":glimpse:ui")
include(":glimpse:ui-compose")

include(":examples:triangle-common")
include(":examples:triangle-android")
include(":examples:triangle-desktop")
include(":examples:offscreen-common")
include(":examples:offscreen-android")
include(":examples:offscreen-desktop")
