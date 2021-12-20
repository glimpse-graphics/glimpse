plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
}

dependencies {
    api(project(":glimpse:processor"))
    implementation("com.squareup:kotlinpoet:1.10.2")
}

detekt { setUpDetekt(project, kotlin.sourceSets.flatMap { it.kotlin.sourceDirectories }) }

tasks {

    withType(Test::class.java) {
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    dokkaHtml { setUpDokkaTask(project) }
    dokkaHtmlPartial { setUpDokkaTask(project) }

    artifacts {
        archives(createJavadocJar(dokkaHtml))
        archives(createSourcesJar(sourceSets.named("main").get().allJava.srcDirs))
    }
}

afterEvaluate {
    publishing {
        publications { registerJarPublication(project) }
        repositories { sonatype(project) }
    }
    signing { signAllMavenPublications(project, publishing) }
}
