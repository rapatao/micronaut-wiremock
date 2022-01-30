package com.rapatao.micronaut.wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.rapatao.micronaut.wiremock.client.TestHttpClient
import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

@MicronautTest(environments = ["enabled"])
internal class EnabledConfigurationTest {

    @Inject
    lateinit var context: ApplicationContext

    @Inject
    lateinit var testHttpClient: TestHttpClient

    @Inject
    lateinit var wireMockServer: WireMockServer

    @Test
    fun `WireMock server should be available in Micronaut context`() {
        assertThat(context.containsBean(WireMockServer::class.java), equalTo(true))
    }

    @Test
    fun `should load using default configuration`(server: WireMockServer) {
        assertThat(server.port(), equalTo(9090))
    }

    @Test
    fun `should support stub creation without usage of a server instance`() {
        stubFor(
            get("/")
                .willReturn(
                    aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                        .withBody("default instance")
                        .withStatus(200)
                )
        )

        val httpResponse = testHttpClient.get()

        assertThat(httpResponse.status(), equalTo(HttpStatus.OK))
        assertThat(httpResponse.body(), equalTo("default instance"))
    }

    @Test
    fun `should support stub creation with server instance`() {
        wireMockServer.stubFor(
            get("/")
                .willReturn(
                    aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                        .withBody("server instance")
                        .withStatus(200)
                )
        )

        val httpResponse = testHttpClient.get()

        assertThat(httpResponse.status(), equalTo(HttpStatus.OK))
        assertThat(httpResponse.body(), equalTo("server instance"))
    }
}
