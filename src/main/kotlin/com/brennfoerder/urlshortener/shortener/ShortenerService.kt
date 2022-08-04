package com.brennfoerder.urlshortener.shortener

import org.springframework.stereotype.Service

@Service
class ShortenerService {
    fun decodeUrl(toDecode: String): DecodedUrlDto {
        return DecodedUrlDto("https://www.dkbcodefactory.com")
    }

    fun shortenUrl(urlToShortenDto: UrlToShortenDto): ShortenedUrlDto {
        return ShortenedUrlDto("codefactory")
    }
}