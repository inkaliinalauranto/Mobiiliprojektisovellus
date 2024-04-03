pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }

    }
}

rootProject.name = "CoolBox-mobiiliprojekti-App"
include(":app", "sample", "vico", "vico:compose", "vico:compose-m2", "vico:compose-m3", "vico:core", "vico:views")
 