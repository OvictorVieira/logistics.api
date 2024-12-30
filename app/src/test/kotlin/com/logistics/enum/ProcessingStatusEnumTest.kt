package com.logistics.enum

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProcessingStatusEnumTest {

    @Test
    fun `test enum values`() {
        val processing = ProcessingStatusEnum.PROCESSING
        val processed = ProcessingStatusEnum.PROCESSED
        val failed = ProcessingStatusEnum.FAILED

        assertEquals(1, processing.code)
        assertEquals("Processing", processing.description)
        assertEquals(2, processed.code)
        assertEquals("Processed", processed.description)
        assertEquals(3, failed.code)
        assertEquals("Failed", failed.description)
    }

    @Test
    fun `test fromCode method`() {
        assertEquals(ProcessingStatusEnum.PROCESSING, ProcessingStatusEnum.fromCode(1))
        assertEquals(ProcessingStatusEnum.PROCESSED, ProcessingStatusEnum.fromCode(2))
        assertEquals(ProcessingStatusEnum.FAILED, ProcessingStatusEnum.fromCode(3))
    }

    @Test
    fun `test fromCode method with invalid code`() {
        val exception = assertThrows<IllegalArgumentException> {
            ProcessingStatusEnum.fromCode(999)
        }
        assertEquals("Invalid status code: 999", exception.message)
    }
}
