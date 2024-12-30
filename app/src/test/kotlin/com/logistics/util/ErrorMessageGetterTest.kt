package com.logistics.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ErrorMessageGetterTest {

    @Test
    fun `getDetailMessage - valid error message`() {
        val errorMessage = "Some error occurred. Detalhe: This is the detailed message] End of error"
        val expectedDetail = "This is the detailed message"
        val actualDetail = ErrorMessageGetter.getDetailMessage(errorMessage)
        assertEquals(expectedDetail, actualDetail)
    }

    @Test
    fun `getDetailMessage - null error message`() {
        val errorMessage: String? = null
        val actualDetail = ErrorMessageGetter.getDetailMessage(errorMessage)
        assertNull(actualDetail)
    }

    @Test
    fun `getDetailMessage - error message without details`() {
        val errorMessage = "Some error occurred. No details here."
        val actualDetail = ErrorMessageGetter.getDetailMessage(errorMessage)
        assertNull(actualDetail)
    }

    @Test
    fun `getDetailMessage - empty error message`() {
        val errorMessage = ""
        val actualDetail = ErrorMessageGetter.getDetailMessage(errorMessage)
        assertNull(actualDetail)
    }

    @Test
    fun `getDetailMessage - error message with multiple matches`() {
        val errorMessage = "First part. Detalhe: First detail] Second part. Detalhe: Second detail] End."
        val expectedDetail = "First detail"
        val actualDetail = ErrorMessageGetter.getDetailMessage(errorMessage)
        assertEquals(expectedDetail, actualDetail)
    }
}
