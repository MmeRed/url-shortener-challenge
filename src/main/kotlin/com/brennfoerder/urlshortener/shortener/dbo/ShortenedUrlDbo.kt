package com.brennfoerder.urlshortener.shortener.dbo

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class ShortenedUrlDbo(
    @Id
    val id: ObjectId = ObjectId.get(),
    val url: String
)
