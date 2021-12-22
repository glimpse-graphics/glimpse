plugins {
    id("graphics.glimpse.jvm")
}

dependencies {
    api(project(":glimpse:processor"))
    implementation("com.squareup:javapoet:1.13.0")
}
