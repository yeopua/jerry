package com.jerry

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JerryApiApplication

fun main(args: Array<String>) {
    runApplication<JerryApiApplication>(*args)
}
