package com.brennfoerder.urlshortener.shortener

import com.brennfoerder.urlshortener.fixtures.aShortenedUrlDbo
import com.brennfoerder.urlshortener.shortener.dto.UrlToShortenDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNull

internal class ShortenerServiceTest {

    private val shortenerRepository: ShortenerRepository = mockk()

    private val shortenerService = ShortenerService(shortenerRepository)

    @Test
    fun `decodeUrl returns ShortenedUrlDbo when existent`() {
        val objectId = ObjectId.get()
        val shortenedUrlDbo = aShortenedUrlDbo(objectId)
        every { shortenerRepository.findOneById(objectId) } returns shortenedUrlDbo

        val result = shortenerService.decodeUrl(objectId.toHexString())

        expectThat(result).isEqualTo(shortenedUrlDbo)
    }

    @Test
    fun `decodeUrl returns null when not found`() {
        every { shortenerRepository.findOneById(any()) } returns null

        val result = shortenerService.decodeUrl(ObjectId.get().toHexString())

        expectThat(result).isNull()
    }

    @Test
    fun `shortenUrl - creates new ShortenedUrlDbo when url not found in repository`() {
        every { shortenerRepository.findOneByUrl(any()) } returns null
        every { shortenerRepository.save(any()) } answers { firstArg() }

        val result = shortenerService.shortenUrl(UrlToShortenDto("https://www.dkbcodefactory.com"))

        expectThat(result.url).isEqualTo("https://www.dkbcodefactory.com")
        verify { shortenerRepository.save(any()) }
    }

    @Test
    fun `shortenUrl - returns existing ShortenedUrlDbo when url found in repository`() {
        val savedShortenedUrlDbo = aShortenedUrlDbo()
        every { shortenerRepository.findOneByUrl(any()) } returns savedShortenedUrlDbo

        val result = shortenerService.shortenUrl(UrlToShortenDto("https://www.dkbcodefactory.com"))

        expectThat(result).isEqualTo(savedShortenedUrlDbo)
        verify(exactly = 0) { shortenerRepository.save(any()) }
    }
}
