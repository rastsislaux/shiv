plugins {
    kotlin("jvm") version "2.2.21"
    `maven-publish`
    `java-library`
}

group = "io.github.rastsislaux"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("io.github.rastsislaux:shiv-core:1.0-SNAPSHOT")

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(23)
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = project.group.toString()
            artifactId = "shiv-horse"
            version = project.version.toString()
        }
    }
}
