rootProject.name = "VanillaDisable"

pluginManagement {
    repositories {
        maven { url = uri("https://maven.fabricmc.net/") }
        maven { url = uri("https://files.minecraftforge.net/maven/") }
        maven { url = uri("https://maven.neoforged.net/releases/") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.parchmentmc.org/") }
        gradlePluginPortal()
        mavenCentral()
    }
}

include("common")
include("fabric")
include("neoforge")