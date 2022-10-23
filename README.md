# WireMock support for Micronaut Applications

[![Maven Central](https://img.shields.io/maven-central/v/com.rapatao.micronaut/micronaut-wiremock.svg?label=Maven%20Central)](https://search.maven.org/artifact/com.rapatao.micronaut/micronaut-wiremock)

The `micronaut-wiremock` makes simple the use of [WireMock](https://github.com/wiremock/wiremock) for testing a
Micronaut application.

## Dependency

To get started, add the following dependency to the project.

### Gradle

```groovy
testImplementation "com.rapatao.micronaut:micronaut-wiremock:$version"
```

### Maven

```xml

<dependency>
    <groupId>com.rapatao.micronaut</groupId>
    <artifactId>micronaut-wiremock</artifactId>
    <version>$version</version>
    <scope>test</scope>
</dependency>
```

## Configuration

By default, it will start the WireMockServer using port as `9090` and host as `localhost`. You can easily customize it
using the project configuration file (`application[-environment].yml`).

```yaml
wiremock:
    enabled: true
    port: 9090
    host: localhost
```

## Server Customization

When a custom server is required by any reason, you can customize the WireMockServer providing a bean of
type `com.github.tomakehurst.wiremock.core.WireMockConfiguration`. It's useful for testing scenarios which requires
different types of [extensions](http://wiremock.org/docs/extending-wiremock/)

**IMPORTANT:** The extension [`ResponseTemplateTransformer`](http://wiremock.org/docs/response-templating/) is globally
active by default

## Using the WireMock

The purpose of the library is simplifying the `WireMockServer` initialization, so, you can use all the usual methods
provided by WireMock. If required, you can inject an instance of the `WireMockServer` and manipulate it directly.

### Test using an injected instance

```kotlin
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@MicronautTest
internal class StubTest {

    @Inject
    lateinit var wireMockServer: WireMockServer

    @Test
    fun test() {
        wireMockServer.stubFor(WireMock.get("/").willReturn(WireMock.aResponse().withBody("ok")))
        ...
    }
}
```

## Test using static method

```kotlin
import com.github.tomakehurst.wiremock.client.WireMock
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test

@MicronautTest
internal class Stub2Test {

    @Test
    fun test() {
        WireMock.stubFor(WireMock.get("/").willReturn(WireMock.aResponse().withBody("ok")))
        ...
    }
}
```

## JUnit extension

It is possible to move the stub creation from the testing block by using the `JUnit 5` and the
annotation `@com.rapatao.micronaut.wiremock.junit.StubFor`.

This annotation extends the JUnit, invoking a class to run the stub definition.

The `@com.rapatao.micronaut.wiremock.junit.StubFor` has an argument that must be used to define
all `com.rapatao.micronaut.wiremock.junit.StubDefinition` that must be configured
before the test execution. It accepts a list, that are invoked in the declared order.

### Example of stub definition class

```kotlin
class ExampleStubDefinition : StubDefinition {
    override fun configure() {
        WireMock.stubFor(
            ...
        )
        stubFor(
            ...
        )
    }
}
```

### How to use the annotation?

It is possible to declare the annotation in the `class` or in the `method`, each one works differently, as below:

When declared in a `class`, it acts as a `@BeforeAll`, invoking the stub definition class before all test methods,
removing the identified mappings after all testes finish.
When declared in a `method`, it acts as a `@BeforeEach`, invoking the stub definition class before each test method
execution and remove the identified mappings after each execution.

### Known limitations

The annotation, when declared at class level, must be after the `@MicronautTest` annotation. The JUnit invokes the
extensions using the declared order, so, when the `@StubFor` is declared before the `@MicronautTest`, the Micronaut
context is not yet initialized, which means, that the WireMock instance is not yet configured.
