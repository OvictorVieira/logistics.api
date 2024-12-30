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
import org.mockito.kotlin.any
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files
import org.springframework.mock.web.MockMultipartFile

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

    @Test
    fun `test initiateFileProcessing - success`() {
        val processingStatus = ProcessingStatus(
            id = 1L,
            fileName = "testfile.csv",
            status = ProcessingStatusEnum.PROCESSING.code
        )

        val fileContent = """
            0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308
            0000000075                                  Bobbie Batz00000007980000000002     1578.5720211116
            0000000049                               Ken Wintheiser00000005230000000003      586.7420210903
            0000000014                                 Clelia Hills00000001460000000001      673.4920211125
            0000000057                          Elidia Gulgowski IV00000006200000000000     1417.2520210919
            0000000080                                 Tabitha Kuhn00000008770000000003      817.1320210612
            0000000023                                  Logan Lynch00000002530000000002      322.1220210523
            0000000015                                   Bonny Koss00000001530000000004        80.820210701
            0000000017                              Ethan Langworth00000001690000000000      865.1820210409
        """.trimIndent().toByteArray()

        val multipartFile = MockMultipartFile(
            "file",
            "testfile.csv",
            "text/csv",
            fileContent
        )

        Mockito.`when`(processingStatusRepository.save(any())).thenReturn(processingStatus)

        val result = fileProcessingService.initiateFileProcessing(multipartFile)

        assertEquals(processingStatus, result)

        Mockito.verify(processingStatusRepository).save(any())

        assertTrue(Files.exists(uploadDir))
    }
}
