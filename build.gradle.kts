plugins {
    java
    kotlin("jvm") version "1.9.23"
    id("me.qoomon.git-versioning") version "6.4.3"
}

group = "io.github.adamsonyanik"
version = "0.0.0-SNAPSHOT"
gitVersioning.apply {
    refs {
        branch(".+") {
            version = "\${ref}-\${commit}-SNAPSHOT"
        }
        tag("v(?<version>.*)") {
            version = "\${ref.version}"
        }
    }
}

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
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://oss.sonatype.org/content/repositories/central")
    }

    dependencies {
        "compileOnly"("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    }
}
