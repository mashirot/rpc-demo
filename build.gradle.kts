import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.9.22"
}

repositories {
    mavenCentral()
}

subprojects {

    group = "io.sakurasou"
    version = "1.0-SNAPSHOT"

    apply(plugin = "kotlin")

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation("org.slf4j:slf4j-simple:2.0.12")
        implementation("io.github.oshai:kotlin-logging-jvm:6.0.3")
    }

    kotlin {
        jvmToolchain(21)
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "21" // 设置为适合你项目的JVM版本
        }
    }
}
