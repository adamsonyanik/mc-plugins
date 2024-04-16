plugins {
    java
    kotlin("jvm") version "1.9.23"
    id("org.ajoberstar.grgit") version "4.1.0"
}

group = "io.github.adamsonyanik"
version = System.getenv("GH_ACTIONS_RELEASE_VERSION") ?: grgit.head().abbreviatedId

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
