import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    alias(libs.plugins.springboot)
}

dependencies {
    implementation(project(":common-utils"))
    implementation(project(":jerry-core"))
    implementation(project(":jerry-infra"))
    testImplementation(testFixtures(project(":jerry-core")))

    implementation(libs.springboot.starter.webflux)
    implementation(libs.springboot.starter.aop)
    implementation(libs.springboot.starter.test)

    implementation(libs.blockhound)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.debug)
    implementation(libs.jackson.dataformat.yaml)

    val isMacOS: Boolean = System.getProperty("os.name").startsWith("Mac OS X")
    val architecture = System.getProperty("os.arch").lowercase()
    if (isMacOS && architecture == "aarch64") {
        developmentOnly(libs.netty.resolver.dns.native.macos) {
            artifact {
                classifier = "osx-aarch_64"
            }
        }
    }
}

val snippetsDir by extra { file("./build/generated-snippets") }

tasks.test {
    delete("./build/generated-snippets")
    useJUnitPlatform()
    outputs.dir(snippetsDir)
}

val moduleMainClass = "com.jerry.JerryApiApplication.kt"

springBoot {
    mainClass.set(moduleMainClass)
}

tasks.getByName<BootJar>("bootJar") {
    enabled = true
    mainClass.set(moduleMainClass)
}
