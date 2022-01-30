package com.rapatao.micronaut.wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.Options
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Requires
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Factory
internal class WireMockFactory {

    private val log = LoggerFactory.getLogger(WireMockFactory::class.java)

    @Bean
    @Singleton
    @Requires(beans = [Configuration::class], missingBeans = [WireMockConfiguration::class])
    fun wireMockConfiguration(configuration: Configuration): WireMockConfiguration =
        WireMockConfiguration.wireMockConfig()
            .port(configuration.port)
            .bindAddress(configuration.host)
            .extensions(ResponseTemplateTransformer(true))

    @Context
    @Bean(preDestroy = "stop")
    @Requires(beans = [WireMockConfiguration::class], missingBeans = [WireMockServer::class])
    fun wireMockConfiguration(configuration: WireMockConfiguration): WireMockServer {
        val server = ServerDelegate(configuration)

        server.start()
        WireMock.configureFor(server.client())

        log.info(configuration.toString())

        log.info("WireMock listening at ${configuration.bindAddress()}:${configuration.portNumber()}")

        return server
    }

    private inner class ServerDelegate(options: Options) : WireMockServer(options) {
        fun client(): WireMock = client
    }
}
