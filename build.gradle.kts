import kr.entree.spigradle.kotlin.spigot

val spigotVersion = "1.20.4"
val spigotSnapshot = "-R0.1-SNAPSHOT"

plugins {
    java
    id("kr.entree.spigradle") version("2.4.3") apply(false)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "kr.entree.spigradle")

    group = "io.github.adamsonyanik"
    version = System.getenv("RELEASE_VERSION") ?: "LOCAL-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly(spigot("$spigotVersion$spigotSnapshot"))
    }
}

tasks.register("copyPlugins", Copy::class) {
    group = "dev"
    dependsOn("jar")
    subprojects {
        from(tasks.jar.get().outputs.files)
        into("dev-server/plugins")
    }
}
tasks.clean { delete("dev-server/plugins") }
tasks.register("dev", JavaExec::class) {
    group = "dev"
    dependsOn("copyPlugins")
    workingDir = file("dev-server")
    classpath = files("dev-server/spigot-$spigotVersion.jar")
}
