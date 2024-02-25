plugins {
    kotlin("jvm")
}

group = "io.sakurasou"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":model"))
    implementation(project(":rpc"))
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}