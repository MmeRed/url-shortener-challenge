package com.brennfoerder.urlshortener.shortener

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ShortenerController(
    private val shortenerService: ShortenerService
) {

    @GetMapping("/{toDecode}")
    fun decodeUrl(@PathVariable toDecode: String): DecodedUrlDto {
        return shortenerService.decodeUrl(toDecode)
    }

    @PostMapping("/shorten")
    fun shortenUrl(@RequestBody urlToShortenDto: UrlToShortenDto): ShortenedUrlDto {
        return shortenerService.shortenUrl(urlToShortenDto)
    }
}

data class DecodedUrlDto(
    val decodedUrl: String
)

data class UrlToShortenDto(
    val url: String
)

data class ShortenedUrlDto(
    val shortenedUrl: String
)