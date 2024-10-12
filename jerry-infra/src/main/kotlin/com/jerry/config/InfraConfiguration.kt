package com.jerry.config

import org.springframework.context.annotation.Import

@Import(
    value = [
        WebClientConfiguration::class
    ]
)
class InfraConfiguration
