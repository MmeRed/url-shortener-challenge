package com.brennfoerder.urlshortener.shortener

import com.brennfoerder.urlshortener.shortener.dbo.ShortenedUrlDbo
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ShortenerRepository : MongoRepository<ShortenedUrlDbo, String> {
    fun findOneById(id: ObjectId): ShortenedUrlDbo
    fun save(shortenedUrlDbo: ShortenedUrlDbo): ShortenedUrlDbo
}