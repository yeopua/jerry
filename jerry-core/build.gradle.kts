plugins {
    `java-test-fixtures`
}

dependencies {
    implementation(project(":common-utils"))
    testFixturesImplementation(libs.arrow.core)
}
