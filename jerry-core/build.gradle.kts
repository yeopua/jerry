plugins {
    `java-test-fixtures`
}

dependencies {
    implementation(project(":common-utils"))
    testFixturesImplementation(libs.arrow.core)
    testFixturesImplementation(libs.kotest.assertions.core)
    testFixturesImplementation(libs.kotest.assertions.arrow)
}
