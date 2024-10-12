dependencies {
    implementation(project(":common-utils"))
    implementation(project(":jerry-core"))
    testImplementation(testFixtures(project(":jerry-core")))

    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.springboot.starter.data.redis.reactive)
    implementation(libs.springboot.starter.webflux)
    implementation(libs.resilience4j.kotlin)
    implementation(libs.resilience4j.springboot)
    implementation(libs.caffeine)
    testImplementation(libs.springboot.starter.test)
}
