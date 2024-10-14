package com.jerry.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableR2dbcAuditing
@EnableR2dbcRepositories(basePackages = ["com.jerry.*.r2dbc.repository"])
@EnableTransactionManagement
class R2dbcConfiguration {
    @Bean
    fun transactionManager(connectionFactory: ConnectionFactory): R2dbcTransactionManager {
        return R2dbcTransactionManager(connectionFactory)
    }
}
