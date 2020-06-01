plugins {
    id("fabric-loom") version "0.2.7-SNAPSHOT"
    signing
}

group = "dev.sapphic"
version = "0.1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

minecraft {
    refmapName = "mixins/infinitymending/refmap.json"
}

dependencies {
    minecraft("com.mojang:minecraft:1.15.2")
    mappings("net.fabricmc:yarn:1.15.2+build.17:v2")
    modImplementation("net.fabricmc:fabric-loader:0.8.5+build.199")
    implementation("org.jetbrains:annotations:19.0.0")
    implementation("org.checkerframework:checker-qual:3.4.0")
}

tasks.withType<ProcessResources> {
    filesMatching("/fabric.mod.json") {
        expand("version" to version)
    }
}

tasks.withType<JavaCompile> {
    with(options) {
        isFork = true
        isVerbose = true
        encoding = "UTF-8"
    }
}

signing {
    isRequired = hasProperty("signing.gnupg.keyId")
    useGpgCmd()
    sign(configurations.archives.get())
}
