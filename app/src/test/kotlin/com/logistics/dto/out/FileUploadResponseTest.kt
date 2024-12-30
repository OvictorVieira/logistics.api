package com.logistics.dto.out

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.io.*

class FileUploadResponseTest {

    @Test
    fun `test FileUploadResponse creation`() {
        val fileUploadResponse = FileUploadResponse(
            id = 1L,
            status = "Uploaded"
        )

        assertEquals(1L, fileUploadResponse.id)
        assertEquals("Uploaded", fileUploadResponse.status)
    }

    @Test
    fun `test FileUploadResponse equality and hashcode`() {
        val fileUploadResponse1 = FileUploadResponse(
            id = 1L,
            status = "Uploaded"
        )

        val fileUploadResponse2 = FileUploadResponse(
            id = 1L,
            status = "Uploaded"
        )

        val fileUploadResponse3 = FileUploadResponse(
            id = 2L,
            status = "Failed"
        )

        assertEquals(fileUploadResponse1, fileUploadResponse2)
        assertNotEquals(fileUploadResponse1, fileUploadResponse3)
        assertEquals(fileUploadResponse1.hashCode(), fileUploadResponse2.hashCode())
        assertNotEquals(fileUploadResponse1.hashCode(), fileUploadResponse3.hashCode())
    }
}
