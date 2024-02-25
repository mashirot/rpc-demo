plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "rpc-demo"
include("publisher")
include("subscriber")
include("rpc")
include("model")
