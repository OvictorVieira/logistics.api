package com.logistics.dto.out

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.io.*

class ApiResponseTest {

    @Test
    fun `test ApiResponse creation`() {
        val apiResponse = ApiResponse(
            success = true,
            message = "Operation successful",
            data = "Sample data"
        )

        assertEquals(true, apiResponse.success)
        assertEquals("Operation successful", apiResponse.message)
        assertEquals("Sample data", apiResponse.data)
    }

    @Test
    fun `test ApiResponse equality and hashcode`() {
        val apiResponse1 = ApiResponse(
            success = true,
            message = "Operation successful",
            data = "Sample data"
        )

        val apiResponse2 = ApiResponse(
            success = true,
            message = "Operation successful",
            data = "Sample data"
        )

        val apiResponse3 = ApiResponse(
            success = false,
            message = "Operation failed",
            data = "Error data"
        )

        assertEquals(apiResponse1, apiResponse2)
        assertNotEquals(apiResponse1, apiResponse3)
        assertEquals(apiResponse1.hashCode(), apiResponse2.hashCode())
        assertNotEquals(apiResponse1.hashCode(), apiResponse3.hashCode())
    }
}
