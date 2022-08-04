package com.brennfoerder.urlshortener.shortener

import org.hamcrest.CoreMatchers.equalTo
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest
internal class ShortenerControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    fun `decode url - success`() {
        mockMvc.get("/codefactory")
            .andExpect {
                status { isOk() }
                jsonPath("decodedUrl", equalTo("https://www.dkbcodefactory.com"))
            }
    }

    @Test
    fun `encode url - success`() {
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
            jsonPath("shortenedUrl", equalTo("codefactory"))
        }
    }
}