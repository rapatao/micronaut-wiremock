package com.rapatao.micronaut.wiremock

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Requires

@Requires(property = "wiremock.enabled", value = "true", defaultValue = "true")
@ConfigurationProperties("wiremock")
class Configuration {
    @SuppressWarnings("MagicNumber")
    var port: Int = 9090
    var host: String = "localhost"
}
