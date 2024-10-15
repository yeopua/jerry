import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    `version-catalog`
    `groovy-gradle-plugin`
    alias(libs.plugins.springboot) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.spring) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.detekt)
}

group = "com"
version = "0.0.1-SNAPSHOT"

allprojects {
    apply {
        plugin(rootProject.libs.plugins.ktlint.get().pluginId)
        plugin(rootProject.libs.plugins.detekt.get().pluginId)
    }

    repositories {
        mavenCentral()
    }

    detekt {
        config.setFrom(file("$rootDir/config/detekt.yml"))
    }

    configure<KtlintExtension> {
        debug.set(true)
        verbose.set(true)
        coloredOutput.set(true)
        outputToConsole.set(true)
        outputColorName.set("RED")
        ignoreFailures.set(false)
        enableExperimentalRules.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin(rootProject.libs.plugins.kotlin.jvm.get().pluginId)
        plugin(rootProject.libs.plugins.kotlin.spring.get().pluginId)
        plugin(rootProject.libs.plugins.kotlin.serialization.get().pluginId)
    }

    dependencies {
        implementation(rootProject.libs.arrow.core)
        implementation(rootProject.libs.kotlinx.coroutines.core)
        implementation(rootProject.libs.kotlinx.coroutines.reactor)
        implementation(rootProject.libs.springboot.starter.log4j2)
        testImplementation(rootProject.libs.junit.jupiter)
        testImplementation(rootProject.libs.mockito.kotlin)
        testImplementation(rootProject.libs.mockk)
        testImplementation(rootProject.libs.kotest.runner.junit5.jvm)
        testImplementation(rootProject.libs.kotest.assertions.core)
        testImplementation(rootProject.libs.kotest.assertions.arrow)
        testImplementation(rootProject.libs.kotlinx.coroutines.test)
    }

    tasks.test {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "21"
        }
    }

    configurations {
        all {
            exclude("org.springframework.boot", "spring-boot-starter-logging")
        }
    }
}
