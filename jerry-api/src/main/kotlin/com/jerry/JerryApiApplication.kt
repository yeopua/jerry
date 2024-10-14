package com.jerry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class JerryApiApplication

fun main(args: Array<String>) {
    runApplication<JerryApiApplication>(*args)
}
