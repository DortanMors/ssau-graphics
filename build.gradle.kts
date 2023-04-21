import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "ru.ssau"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.kotlin.link")
}

dependencies {
    implementation("org.jetbrains.kotlinx:multik-core:0.2.0")
    implementation("org.jetbrains.kotlinx:multik-default:0.2.0")
    implementation("org.jetbrains.kotlinx:multik-openblas:0.2.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    exclude("META-INF/versions/9/module-info.class")
    manifest {
        attributes["Main-Class"] = "ru.ssau.ssau_graphics.lab5.MainKt"
    }
    from({ configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) } })
}
