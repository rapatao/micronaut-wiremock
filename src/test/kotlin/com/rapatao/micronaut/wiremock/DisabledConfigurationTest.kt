package com.rapatao.micronaut.wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import io.micronaut.context.ApplicationContext
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

@MicronautTest(environments = ["disabled"])
internal class DisabledConfigurationTest {

    @Inject
    lateinit var context: ApplicationContext

    @Test
    fun `WireMock server should not be available in Micronaut context`() {
        assertThat(context.containsBean(WireMockServer::class.java), equalTo(false))
    }
}
