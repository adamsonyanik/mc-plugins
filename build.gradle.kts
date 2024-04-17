plugins {
    java
    kotlin("jvm") version "1.9.23"
    id("me.qoomon.git-versioning") version "6.4.2"
}

group = "io.github.adamsonyanik"
version = System.getenv("ARTIFACT_VERSION") ?: "LOCAL-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    dependencies {
        "compileOnly"("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    }
}
