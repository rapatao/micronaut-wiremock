package com.rapatao.micronaut.wiremock.junit.helper

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("test")
interface StubForTestHttpClient {

    @Get("/{path}")
    fun get(@PathVariable("path") type: String): HttpResponse<String>
}
