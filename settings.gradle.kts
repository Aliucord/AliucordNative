pluginManagement {
    plugins {
        val agpVersion = "7.3.1"
        id("com.android.library") version agpVersion apply false

        id("com.aliucord.gradle") version "main-SNAPSHOT" apply false
    }
    repositories {
        google()
        mavenCentral()
        maven("https://maven.aliucord.com/snapshots")
        maven("https://jitpack.io")
    }
}

rootProject.name = "AliucordNative"
include(":app")
