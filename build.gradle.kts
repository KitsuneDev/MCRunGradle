plugins {
    id("java-gradle-plugin")
    kotlin("jvm") version "1.3.72"
    id("com.gradle.plugin-publish") version "0.11.0"
}

group = "me.gabrieltk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.10")
    gradleApi()
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    gradlePlugin {
        plugins {
            create("mcrun-server") {
                id = "me.gabrieltk.mcrun"
                implementationClass = "me.gabrieltk.mcrun.MCRunPlugin"
            }
        }
    }

}

pluginBundle {
    // These settings are set for the whole plugin bundle
    website = "http://www.gradle.org/"
    vcsUrl = "https://github.com/gradle/gradle"

    // tags and description can be set for the whole bundle here, but can also
    // be set / overridden in the config for specific plugins
    description = "Prepares Minecraft to debug plugins."

    // The plugins block can contain multiple plugin entries.
    //
    // The name for each plugin block below (greetingsPlugin, goodbyePlugin)
    // does not affect the plugin configuration, but they need to be unique
    // for each plugin.

    // Plugin config blocks can set the id, displayName, version, description
    // and tags for each plugin.

    // id and displayName are mandatory.
    // If no version is set, the project version will be used.
    // If no tags or description are set, the tags or description from the
    // pluginBundle block will be used, but they must be set in one of the
    // two places.

    (plugins) {

        // first plugin
        "mcrun-server" {
            // id is captured from java-gradle-plugin configuration
            displayName = "Prepares Minecraft Servers for debugging plugins"
            tags = listOf("minecraft", "spigot", "paper")
            version = "1.0-SNAPSHOT"
        }

        // another plugin
    //    "goodbyePlugin" {
    //        // id is captured from java-gradle-plugin configuration
    //        displayName = "Gradle Goodbye plugin"
    //        description = "Override description for this plugin"
    //        tags = listOf("different", "for", "this", "one")
    //        version = "1.3"
        //}
    }

    // Optional overrides for Maven coordinates.
    // If you have an existing plugin deployed to Bintray and would like to keep
    // your existing group ID and artifact ID for continuity, you can specify
    // them here.
    //
    // As publishing to a custom group requires manual approval by the Gradle
    // team for security reasons, we recommend not overriding the group ID unless
    // you have an existing group ID that you wish to keep. If not overridden,
    // plugins will be published automatically without a manual approval process.
    //
    // You can also override the version of the deployed artifact here, though it
    // defaults to the project version, which would normally be sufficient.

    //mavenCoordinates {
    //    groupId = "me.gabrieltk"
    //    artifactId = "MCRunGradle"
    //    version = "1.0-Snaphot"
    //}
}