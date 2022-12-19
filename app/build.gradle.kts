import com.aliucord.gradle.ProjectType

plugins {
    id("com.android.library")
    id("com.aliucord.gradle")
}

aliucord {
    projectType.set(ProjectType.CORE)
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    discord("com.discord:discord:${findProperty("discord_version")}")
}

afterEvaluate {
    tasks.named<AbstractArchiveTask>("make") {
        archiveBaseName.set(rootProject.name)
    }
}
