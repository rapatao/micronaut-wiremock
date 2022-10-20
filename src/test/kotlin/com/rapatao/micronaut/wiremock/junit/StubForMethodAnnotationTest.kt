package com.rapatao.micronaut.wiremock.junit

import com.rapatao.micronaut.wiremock.junit.helper.SampleMethodStubDefinition
import com.rapatao.micronaut.wiremock.junit.helper.StubForTestHttpClient
import io.micronaut.http.HttpStatus.OK
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@MicronautTest(environments = ["random"])
internal class StubForMethodAnnotationTest {

    @Inject
    lateinit var client: StubForTestHttpClient

    @Test
    @StubFor([SampleMethodStubDefinition::class])
    fun `should register wiremock stub using method annotation support`() {
        val response = client.get("method-annotation")

        assertThat(response.status, equalTo(OK))
        assertThat(response.body(), equalTo("using @stubFor method annotation"))
    }
}
