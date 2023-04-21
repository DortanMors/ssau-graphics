import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "ru.ssau"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.kotlin.link")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:multik-core:0.2.0")
    implementation("org.jetbrains.kotlinx:multik-default:0.2.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}