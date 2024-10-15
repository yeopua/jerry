plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "jerry"
include(
    "jerry-api",
    "jerry-core",
    "jerry-infra",
    "common-utils"
)
