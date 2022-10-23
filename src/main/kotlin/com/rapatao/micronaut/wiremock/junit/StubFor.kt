package com.rapatao.micronaut.wiremock.junit

import org.junit.jupiter.api.extension.ExtendWith
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@ExtendWith(StubForJunitExtension::class)
annotation class StubFor(
    val value: Array<KClass<out StubDefinition>>
)
