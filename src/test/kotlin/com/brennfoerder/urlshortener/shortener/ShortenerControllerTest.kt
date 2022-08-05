package com.brennfoerder.urlshortener.shortener

import com.brennfoerder.urlshortener.exception.InvalidObjectIdException
import com.brennfoerder.urlshortener.fixtures.aShortenedUrlDbo
import com.brennfoerder.urlshortener.shortener.dto.UrlToShortenDto
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.bson.types.ObjectId
import org.hamcrest.CoreMatchers.equalTo
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
internal class ShortenerControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockkBean
    private lateinit var shortenerService: ShortenerService

    @Test
    fun `decode url - success`() {
        val objectId = ObjectId.get()
        val shortenedUrlDbo = aShortenedUrlDbo(objectId)
        every { shortenerService.decodeUrl(objectId.toHexString()) } returns shortenedUrlDbo

        mockMvc.get("/${objectId.toHexString()}")
            .andExpect {
                status { isOk() }
                jsonPath("decodedUrl", equalTo("https://www.dkbcodefactory.com"))
            }
    }

    @Test
    fun `decode url - not found`() {
        every { shortenerService.decodeUrl(any()) } returns null

        mockMvc.get("/notfound")
            .andExpect {
                status { isNotFound() }
            }
    }

    @Test
    fun `decode url - invalid ObjectId hexString`() {
        every { shortenerService.decodeUrl(any()) } throws InvalidObjectIdException()

        // this took me way too long: there seems to be an issue with the mockMvc Kotlin DSL
        // when using mockMvc.get("/invalidObjectId") it just threw the error from the ShortenerService
        // and the ControllerAdvice/ExceptionHandler did not catch that
        // Therefore I'm using for this test the "old" approach

        mockMvc.perform(get("/invalidObjectId"))
            .andExpect(status().isBadRequest)
            .andExpect(
                content().json(
                    """
                {
                    "status": 400,
                    "message": "Invalid decode variable."
                }
                    """.trimIndent()
                )
            )
    }

    @Test
    fun `encode url - success`() {
        val objectId = ObjectId.get()
        val shortenedUrlDbo = aShortenedUrlDbo(objectId)
        every { shortenerService.shortenUrl(UrlToShortenDto("https://www.dkbcodefactory.com")) } returns shortenedUrlDbo

        @Language("JSON")
        val request = """
            {
              "url": "https://www.dkbcodefactory.com"
            }
        """.trimIndent()

        mockMvc.post("/shorten") {
            contentType = MediaType.APPLICATION_JSON
            content = request
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            jsonPath("shortenedUrl", equalTo(objectId.toHexString()))
        }
    }
}
