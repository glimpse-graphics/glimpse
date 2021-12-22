plugins {
    id("graphics.glimpse.jvm")
}

dependencies {
    api(project(":glimpse:processor"))
    implementation("com.squareup:kotlinpoet:1.10.2")
}
