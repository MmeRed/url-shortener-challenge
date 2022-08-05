package com.brennfoerder.urlshortener.fixtures

import com.brennfoerder.urlshortener.shortener.dbo.ShortenedUrlDbo
import org.bson.types.ObjectId

fun aShortenedUrlDbo(
    id: ObjectId = ObjectId.get(),
    url: String = "https://www.dkbcodefactory.com"
) = ShortenedUrlDbo(
    id, url
)
