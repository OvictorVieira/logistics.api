package com.logistics.domain

import com.logistics.enum.ProcessingStatusEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.io.*

class ProcessingStatusTest {

    @Test
    fun `test ProcessingStatus creation`() {
        val processingStatus = ProcessingStatus(
            id = 1L,
            fileName = "test_file.txt",
            status = ProcessingStatusEnum.PROCESSING.code,
            description = ProcessingStatusEnum.PROCESSING.description
        )

        assertEquals(1L, processingStatus.id)
        assertEquals("test_file.txt", processingStatus.fileName)
        assertEquals(ProcessingStatusEnum.PROCESSING.code, processingStatus.status)
        assertEquals(ProcessingStatusEnum.PROCESSING.description, processingStatus.description)
    }

    @Test
    fun `test ProcessingStatus equality and hashcode`() {
        val processingStatus1 = ProcessingStatus(
            id = 1L,
            fileName = "test_file.txt",
            status = ProcessingStatusEnum.PROCESSING.code,
            description = ProcessingStatusEnum.PROCESSING.description
        )

        val processingStatus2 = ProcessingStatus(
            id = 1L,
            fileName = "test_file.txt",
            status = ProcessingStatusEnum.PROCESSING.code,
            description = ProcessingStatusEnum.PROCESSING.description
        )

        val processingStatus3 = ProcessingStatus(
            id = 2L,
            fileName = "another_file.txt",
            status = ProcessingStatusEnum.PROCESSED.code,
            description = ProcessingStatusEnum.PROCESSED.description
        )

        assertEquals(processingStatus1, processingStatus2)
        assertNotEquals(processingStatus1, processingStatus3)
        assertEquals(processingStatus1.hashCode(), processingStatus2.hashCode())
        assertNotEquals(processingStatus1.hashCode(), processingStatus3.hashCode())
    }
}
