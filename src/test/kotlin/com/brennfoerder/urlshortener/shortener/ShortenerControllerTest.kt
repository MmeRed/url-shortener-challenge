package com.brennfoerder.urlshortener.shortener

import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest
internal class ShortenerControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    fun decodeUrl() {
        mockMvc.get("/codefactory")
            .andExpect {
                status { isOk() }
                jsonPath("decodedUrl", equalTo("https://www.dkbcodefactory.com"))
            }
    }
}