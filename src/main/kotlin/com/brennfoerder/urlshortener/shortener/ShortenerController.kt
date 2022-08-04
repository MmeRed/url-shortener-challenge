package com.brennfoerder.urlshortener.shortener

import com.brennfoerder.urlshortener.shortener.dbo.ShortenedUrlDbo
import com.brennfoerder.urlshortener.shortener.dto.DecodedUrlDto
import com.brennfoerder.urlshortener.shortener.dto.ShortenedUrlDto
import com.brennfoerder.urlshortener.shortener.dto.UrlToShortenDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class ShortenerController(
    private val shortenerService: ShortenerService
) {

    @GetMapping("/{toDecode}")
    fun decodeUrl(@PathVariable toDecode: String): DecodedUrlDto {
        return shortenerService.decodeUrl(toDecode)?.toDecodedUrlDto()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/shorten")
    fun shortenUrl(@RequestBody urlToShortenDto: UrlToShortenDto): ShortenedUrlDto {
        return shortenerService.shortenUrl(urlToShortenDto).toShortenedUrlDto()
    }
}

private fun ShortenedUrlDbo.toDecodedUrlDto() = DecodedUrlDto(url)
private fun ShortenedUrlDbo.toShortenedUrlDto() = ShortenedUrlDto(id.toHexString())
