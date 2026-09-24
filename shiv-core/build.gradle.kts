plugins {
    kotlin("jvm") version "2.2.21"
    `maven-publish`
    `java-library`
}

group = "io.github.rastsislaux"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
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
            artifactId = "shiv-core"
            version = project.version.toString()
        }
    }
}
