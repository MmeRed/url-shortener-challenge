package com.brennfoerder.urlshortener.shortener.dto

data class DecodedUrlDto(
    val decodedUrl: String
)

data class UrlToShortenDto(
    val url: String
)

data class ShortenedUrlDto(
    val shortenedUrl: String
)
