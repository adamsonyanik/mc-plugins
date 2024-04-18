import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
    java
    id("com.github.johnrengelman.shadow") version("8.1.1")
}

val spigotVersion = "1.20.4"
val spigotSnapshot = "-R0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_17
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")

    group = "io.github.adamsonyanik"
    version = System.getenv("RELEASE_VERSION") ?: "${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))}-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    dependencies {
        compileOnly("org.spigotmc:spigot-api:$spigotVersion$spigotSnapshot")
    }

    tasks {
        processResources {
            filesMatching("plugin.yml") {
                expand("name" to project.name, "version" to project.version)
            }
        }
        shadowJar {
            configurations = listOf(project.configurations.runtimeClasspath.get())
        }
    }
}

tasks.register("copyPlugins", Copy::class) {
    group = "dev"
    dependsOn("cleanCopiedPlugins")
    subprojects {
        from(tasks.shadowJar.get().outputs.files)
        into("dev-server/plugins")
    }
}
tasks.register("cleanCopiedPlugins") {
    group = "dev"
    delete(fileTree("dev-server/plugins") { include("*.jar") })
}
tasks.clean { dependsOn("cleanCopiedPlugins") }
tasks.register("dev", JavaExec::class) {
    group = "dev"
    dependsOn("copyPlugins")
    workingDir = file("dev-server")
    classpath = files("dev-server/spigot-$spigotVersion.jar")
    jvmArgs = listOf("-DIReallyKnowWhatIAmDoingISwear")
    args = listOf("nogui")
}
