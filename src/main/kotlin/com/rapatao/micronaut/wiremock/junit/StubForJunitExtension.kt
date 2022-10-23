package com.rapatao.micronaut.wiremock.junit

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import kotlin.reflect.full.createInstance

class StubForJunitExtension : BeforeAllCallback, BeforeEachCallback, AfterAllCallback, AfterEachCallback {

    private val stubs = mutableMapOf<String, List<StubMapping>>()

    override fun beforeAll(context: ExtensionContext) {
        configureStub(context)
    }

    override fun beforeEach(context: ExtensionContext) {
        configureStub(context)
    }

    override fun afterAll(context: ExtensionContext) {
        removeUnidentifiedStubs(context)
    }

    override fun afterEach(context: ExtensionContext) {
        removeUnidentifiedStubs(context)
    }

    private fun configureStub(context: ExtensionContext) {
        context.element.filter {
            it.isAnnotationPresent(StubFor::class.java)
        }.ifPresent {
            it.getAnnotation(StubFor::class.java)
                .value
                .forEach { stubDefinitionClass ->
                    stubDefinitionClass.createInstance().configure()
                }

            registerCreatedStubs(context)
        }
    }

    private fun registerCreatedStubs(context: ExtensionContext) {
        val existingStubs = stubs.values.flatten()

        WireMock.listAllStubMappings().mappings
            .filter {
                !existingStubs.contains(it)
            }.groupBy {
                context.asKey()
            }.let {
                stubs.putAll(it)
            }
    }

    private fun removeUnidentifiedStubs(context: ExtensionContext) {
        stubs[context.asKey()]?.forEach {
            WireMock.removeStub(it)
        }
    }

    private fun ExtensionContext.asKey() = this.uniqueId
}
