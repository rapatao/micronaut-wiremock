package com.rapatao.micronaut.wiremock

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Requires

/**
 * Mapping of WireMock configuration
 */
@Requires(property = "wiremock.enabled", value = "true", defaultValue = "true")
@ConfigurationProperties("wiremock")
class Configuration {

    /**
     * Server port
     */
    @SuppressWarnings("MagicNumber")
    var port: Int = 9090

    /**
     * Server binding address
     */
    var host: String = "localhost"
}
