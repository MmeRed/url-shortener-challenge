package com.brennfoerder.urlshortener.shortener

import com.brennfoerder.urlshortener.fixtures.aShortenedUrlDbo
import com.brennfoerder.urlshortener.shortener.dto.UrlToShortenDto
import io.mockk.every
import io.mockk.mockk
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class ShortenerServiceTest {

    private val shortenerRepository: ShortenerRepository = mockk()

    private val shortenerService = ShortenerService(shortenerRepository)

    @Test
    fun decodeUrl() {
        val objectId = ObjectId.get()
        val shortenedUrlDbo = aShortenedUrlDbo(objectId)
        every { shortenerRepository.findOneById(objectId) } returns shortenedUrlDbo

        val result = shortenerService.decodeUrl(objectId.toHexString())

        expectThat(result).isEqualTo(shortenedUrlDbo)
    }

    @Test
    fun shortenUrl() {
        every { shortenerRepository.save(any()) } answers { firstArg() }

        val result = shortenerService.shortenUrl(UrlToShortenDto("https://www.dkbcodefactory.com"))

        expectThat(result.url).isEqualTo("https://www.dkbcodefactory.com")
    }
}
