package com.rapatao.micronaut.wiremock.client

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("test")
interface TestHttpClient {

    @Get("/")
    fun get(): HttpResponse<String>
}
