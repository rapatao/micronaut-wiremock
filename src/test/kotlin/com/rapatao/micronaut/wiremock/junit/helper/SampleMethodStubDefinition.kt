package com.rapatao.micronaut.wiremock.junit.helper

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.rapatao.micronaut.wiremock.junit.StubDefinition
import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType

class SampleMethodStubDefinition : StubDefinition {

    override fun configure() {
        stubFor(
            get("/method-annotation")
                .willReturn(
                    aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN)
                        .withBody("using @stubFor method annotation")
                        .withStatus(200)
                )
        )
    }

}
