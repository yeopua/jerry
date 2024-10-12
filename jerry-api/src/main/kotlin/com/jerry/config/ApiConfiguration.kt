package com.jerry.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(
    value = [
        InfraConfiguration::class
    ]
)
class ApiConfiguration
