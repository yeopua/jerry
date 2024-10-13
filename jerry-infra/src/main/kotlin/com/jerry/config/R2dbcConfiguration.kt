package com.jerry.config

import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@EnableR2dbcAuditing
@EnableR2dbcRepositories(basePackages = ["com.jerry.*.r2dbc.repository"])
class R2dbcConfiguration
