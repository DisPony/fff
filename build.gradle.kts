import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
    application
}

group = "wgh"
version = "1.0-SNAPSHOT"

val koin_version: String by project

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.docx4j:docx4j:6.1.2")
    implementation(kotlin("stdlib-jdk8"))
    implementation("javax.xml.bind:jaxb-api:2.2.11")
    implementation("com.sun.xml.bind:jaxb-core:2.2.11")
    implementation("com.sun.xml.bind:jaxb-impl:2.2.11")
    implementation("javax.activation:activation:1.1.1")
    implementation("org.koin:koin-core:$koin_version")
    compile("com.github.ajalt:clikt:2.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "CliKt"
}