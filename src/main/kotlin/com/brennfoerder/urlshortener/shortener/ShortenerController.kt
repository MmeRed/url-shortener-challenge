package com.brennfoerder.urlshortener.shortener

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ShortenerController {

    @GetMapping("/{toDecode}")
    fun decodeUrl(@PathVariable toDecode: String): DecodedUrlDto {
        return DecodedUrlDto("https://www.dkbcodefactory.com")
    }
}

data class DecodedUrlDto(
    val decodedUrl: String
)
