package com.brennfoerder.urlshortener.shortener

import com.brennfoerder.urlshortener.shortener.dbo.ShortenedUrlDbo
import com.brennfoerder.urlshortener.shortener.dto.UrlToShortenDto
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class ShortenerService(
    private val shortenerRepository: ShortenerRepository
) {
    fun decodeUrl(toDecode: String): ShortenedUrlDbo? {
        return shortenerRepository.findOneById(ObjectId(toDecode))
    }

    fun shortenUrl(urlToShortenDto: UrlToShortenDto): ShortenedUrlDbo {
        return shortenerRepository.findOneByUrl(urlToShortenDto.url)
            ?: shortenerRepository.save(createShortenedUrlDbo(urlToShortenDto))
    }

    private fun createShortenedUrlDbo(urlToShortenDto: UrlToShortenDto): ShortenedUrlDbo {
        return ShortenedUrlDbo(
            id = ObjectId.get(),
            url = urlToShortenDto.url
        )
    }
}
