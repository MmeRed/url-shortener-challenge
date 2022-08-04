package com.brennfoerder.urlshortener.shortener

import com.brennfoerder.urlshortener.fixtures.aShortenedUrlDbo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

@Testcontainers
@DataMongoTest(excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class])
internal class ShortenerRepositoryTest(
    @Autowired
    private val shortenerRepository: ShortenerRepository
) {

    @AfterEach
    fun cleanUp() {
        shortenerRepository.deleteAll()
    }

    @Test
    fun `can save ShortenedUrlDbo`() {
        val toSave = aShortenedUrlDbo()
        shortenerRepository.save(toSave)

        val result = shortenerRepository.findAll()

        expectThat(result).hasSize(1)
        expectThat(result.first()).isEqualTo(toSave)
    }

    @Test
    fun `can get ShortenedUrlDbo by id`() {
        val saved = aShortenedUrlDbo()
        shortenerRepository.save(saved)

        val result = shortenerRepository.findOneById(saved.id)

        expectThat(result).isEqualTo(saved)
    }

    companion object {
        @Container
        var mongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") { mongoDBContainer.replicaSetUrl }
        }
    }
}
