dependencies {
    implementation(project(":common-utils"))
    implementation(project(":jerry-core"))
    testImplementation(testFixtures(project(":jerry-core")))

    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.springboot.starter.data.redis.reactive)
    implementation(libs.springboot.starter.data.r2dbc)
    implementation(libs.springboot.starter.webflux)
    implementation(libs.springboot.starter.web)
    implementation(libs.resilience4j.kotlin)
    implementation(libs.resilience4j.springboot)
    implementation(libs.caffeine)
    implementation(libs.h2.database)
    implementation(libs.h2.database.r2dbc)
    implementation(libs.redis)
    testImplementation(libs.springboot.starter.test)

    val isMacOS: Boolean = System.getProperty("os.name").startsWith("Mac OS X")
    val architecture = System.getProperty("os.arch").lowercase()
    if (isMacOS && architecture == "aarch64") {
        testImplementation(libs.netty.resolver.dns.native.macos) {
            artifact {
                classifier = "osx-aarch_64"
            }
        }
    }
}
