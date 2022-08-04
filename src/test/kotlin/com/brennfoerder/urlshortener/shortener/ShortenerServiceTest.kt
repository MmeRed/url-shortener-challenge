package com.brennfoerder.urlshortener.shortener

import com.brennfoerder.urlshortener.shortener.dto.DecodedUrlDto
import com.brennfoerder.urlshortener.shortener.dto.ShortenedUrlDto
import com.brennfoerder.urlshortener.shortener.dto.UrlToShortenDto
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class ShortenerServiceTest {

    private val shortenerService = ShortenerService()

    @Test
    fun decodeUrl() {
        val result = shortenerService.decodeUrl("codefactory")

        expectThat(result).isEqualTo(DecodedUrlDto("https://www.dkbcodefactory.com"))
    }

    @Test
    fun shortenUrl() {
        val result = shortenerService.shortenUrl(UrlToShortenDto("https://www.dkbcodefactory.com"))

        expectThat(result).isEqualTo(ShortenedUrlDto("codefactory"))
    }
}
