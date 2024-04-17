import kr.entree.spigradle.kotlin.spigot

plugins {
    java
    kotlin("jvm") version("1.9.23")
    id("kr.entree.spigradle") version("2.4.3") apply(false)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "kr.entree.spigradle")

    group = "io.github.adamsonyanik"
    version = System.getenv("RELEASE_VERSION") ?: "LOCAL-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.23")
        compileOnly(spigot("1.20.4-R0.1-SNAPSHOT"))
    }
}
