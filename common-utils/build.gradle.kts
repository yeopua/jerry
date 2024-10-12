dependencies {
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.springboot.starter.webflux)
    implementation(libs.resilience4j.kotlin)
    implementation(libs.resilience4j.springboot)
    testImplementation(libs.springboot.starter.test)
}
