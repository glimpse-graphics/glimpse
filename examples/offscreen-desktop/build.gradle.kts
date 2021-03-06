plugins {
    application
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    implementation(project(":examples:offscreen-common"))
    implementation("org.jogamp.jogl:jogl-all-main:2.3.2")
    implementation("org.jogamp.gluegen:gluegen-rt-main:2.3.2")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

detekt { setUpDetekt(project, kotlin.sourceSets.flatMap { it.kotlin.sourceDirectories }) }

application {
    mainClass.set("graphics.glimpse.examples.offscreen.MainKt")
    applicationDefaultJvmArgs = listOf(
        "--add-exports=jogl.all/com.jogamp.opengl.util=ALL-UNNAMED",
        "--add-exports=jogl.all/com.jogamp.opengl.util.awt=ALL-UNNAMED",
        "--add-exports=jogl.all/com.jogamp.opengl.util.texture=ALL-UNNAMED"
    )
}
