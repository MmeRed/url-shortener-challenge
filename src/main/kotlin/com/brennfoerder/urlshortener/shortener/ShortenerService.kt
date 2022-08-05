package com.brennfoerder.urlshortener.shortener

import com.brennfoerder.urlshortener.exception.InvalidObjectIdException
import com.brennfoerder.urlshortener.shortener.dbo.ShortenedUrlDbo
import com.brennfoerder.urlshortener.shortener.dto.UrlToShortenDto
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class ShortenerService(
    private val shortenerRepository: ShortenerRepository
) {
    fun decodeUrl(toDecode: String): ShortenedUrlDbo? {
        val objectId = determineObjectId(toDecode)
        return shortenerRepository.findOneById(objectId)
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

    private fun determineObjectId(toDecode: String): ObjectId {
        try {
            return ObjectId(toDecode)
        } catch (exception: IllegalArgumentException) {
            throw InvalidObjectIdException()
        }
    }
}
