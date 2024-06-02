package com.rapatao.micronaut.wiremock.junit

import com.rapatao.micronaut.wiremock.junit.helper.SampleClassStubDefinition
import com.rapatao.micronaut.wiremock.junit.helper.SampleMethodStubDefinition
import com.rapatao.micronaut.wiremock.junit.helper.StubForTestHttpClient
import io.micronaut.http.HttpStatus.OK
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

@MicronautTest(environments = ["random"])
@StubFor([SampleClassStubDefinition::class])
internal class StubForClassAnnotationTest {

    @Inject
    lateinit var client: StubForTestHttpClient

    @Test
    fun `should register wiremock stub using class annotation support`() {
        val response = client.get("class-annotation")

        assertThat(response.status, equalTo(OK))
        assertThat(response.body(), equalTo("using @stubFor class annotation"))
    }

    @Test
    @StubFor([SampleMethodStubDefinition::class])
    fun `should register wiremock stubs for class and method`() {
        val classResponse = client.get("class-annotation")

        assertThat(classResponse.status, equalTo(OK))
        assertThat(classResponse.body(), equalTo("using @stubFor class annotation"))

        val methodResponse = client.get("method-annotation")

        assertThat(methodResponse.status, equalTo(OK))
        assertThat(methodResponse.body(), equalTo("using @stubFor method annotation"))
    }

    @Test
    @StubFor([SampleMethodStubDefinition::class])
    fun `should register wiremock stubs for class and method2`() {
        val classResponse = client.get("class-annotation")

        assertThat(classResponse.status, equalTo(OK))
        assertThat(classResponse.body(), equalTo("using @stubFor class annotation"))

        val methodResponse = client.get("method-annotation")

        assertThat(methodResponse.status, equalTo(OK))
        assertThat(methodResponse.body(), equalTo("using @stubFor method annotation"))
    }
}
