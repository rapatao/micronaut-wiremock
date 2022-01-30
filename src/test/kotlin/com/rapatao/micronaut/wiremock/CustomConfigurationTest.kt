package com.rapatao.micronaut.wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test

@MicronautTest(environments = ["custom"])
internal class CustomConfigurationTest {

    @Test
    fun `should load on custom port`(server: WireMockServer) {
        assertThat(server, notNullValue())
        assertThat(server.port(), equalTo(9999))
    }
}
