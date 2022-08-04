package com.brennfoerder.urlshortener.shortener

import com.brennfoerder.urlshortener.shortener.dto.DecodedUrlDto
import com.brennfoerder.urlshortener.shortener.dto.ShortenedUrlDto
import com.brennfoerder.urlshortener.shortener.dto.UrlToShortenDto
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
