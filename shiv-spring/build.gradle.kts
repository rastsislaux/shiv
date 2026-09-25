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

    compileOnly("org.springframework.boot:spring-boot-starter-data-jpa:4.1.1")

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
            artifactId = "shiv-spring"
            version = project.version.toString()
        }
    }
}
