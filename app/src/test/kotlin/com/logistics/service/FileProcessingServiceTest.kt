package com.logistics.service

import com.logistics.domain.ProcessingStatus
import com.logistics.enum.ProcessingStatusEnum
import com.logistics.repository.OrderRepository
import com.logistics.repository.ProcessingStatusRepository
import com.logistics.repository.ProductRepository
import com.logistics.exception.RecordNotFoundException
import com.logistics.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.nio.file.Paths

@ExtendWith(MockitoExtension::class)
class FileProcessingServiceTest {

    @Mock
    private lateinit var processingStatusRepository: ProcessingStatusRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var orderRepository: OrderRepository

    @Mock
    private lateinit var multipartFile: MultipartFile

    @InjectMocks
    private lateinit var fileProcessingService: FileProcessingService

    @Captor private lateinit var statusCaptor: ArgumentCaptor<ProcessingStatus>

    private val uploadDir: Path = Paths.get("uploads")

    @Test
    fun `test getProcessingStatus - success`() {
        val processingStatus = ProcessingStatus(id = 1L, fileName = "testfile.csv", status = ProcessingStatusEnum.PROCESSING.code)
        Mockito.`when`(processingStatusRepository.findById(1L)).thenReturn(java.util.Optional.of(processingStatus))

        val result = fileProcessingService.getProcessingStatus(1L)
        assertEquals(processingStatus, result)
    }

    @Test
    fun `test getProcessingStatus - not found`() {
        Mockito.`when`(processingStatusRepository.findById(1L)).thenReturn(java.util.Optional.empty())

        val exception = assertThrows<RecordNotFoundException> {
            fileProcessingService.getProcessingStatus(1L)
        }

        assertEquals("Record not found", exception.message)
    }
}
