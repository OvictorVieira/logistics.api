package com.logistics.service

import com.logistics.domain.Order
import com.logistics.domain.ProcessingStatus
import com.logistics.domain.Product
import com.logistics.domain.User
import com.logistics.dto.mapper.OrderData
import com.logistics.enum.ProcessingStatusEnum
import com.logistics.repository.OrderRepository
import com.logistics.repository.ProcessingStatusRepository
import com.logistics.repository.ProductRepository
import com.logistics.repository.UserRepository
import com.logistics.util.ErrorMessageGetter
import com.logistics.util.LineParser
import com.logistics.exception.RecordNotFoundException
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import org.springframework.dao.DataIntegrityViolationException

@Service
open class FileProcessingService @Autowired constructor(
    private val processingStatusRepository: ProcessingStatusRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) {

    private val uploadDir: Path = Paths.get("uploads")

    fun getProcessingStatus(id: Long): ProcessingStatus =
        processingStatusRepository.findById(id).orElseThrow { RecordNotFoundException("Record not found") }

    fun initiateFileProcessing(file: MultipartFile): ProcessingStatus {
        createUploadDirectoryIfNeeded()
        val savedFileStatus = saveProcessingStatus(file.originalFilename!!)
        val filePath = saveFile(file, savedFileStatus.id, file.originalFilename!!)
        startProcessing(filePath, savedFileStatus.id)
        return savedFileStatus
    }

    private fun createUploadDirectoryIfNeeded() {
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir)
        }
    }

    private fun saveProcessingStatus(fileName: String): ProcessingStatus {
        val processingStatus = ProcessingStatus(fileName = fileName, status = ProcessingStatusEnum.PROCESSING.code)
        return processingStatusRepository.save(processingStatus)
    }

    private fun saveFile(file: MultipartFile, id: Long, originalFilename: String): Path {
        val tempFileName = "${id}_${originalFilename}"
        val filePath = uploadDir.resolve(tempFileName)
        Files.copy(file.inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)
        return filePath
    }

    private fun startProcessing(filePath: Path, statusId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            processFile(filePath, statusId)
        }
    }

    open suspend fun processFile(filePath: Path, statusId: Long) {
        updateStatus(statusId, ProcessingStatusEnum.PROCESSING.code, ProcessingStatusEnum.PROCESSING.description)
        try {
            withContext(Dispatchers.IO) {
                Files.newBufferedReader(filePath)
            }.use { reader ->
                reader.lineSequence().forEach { line ->
                    val data = LineParser.parseLine(line)
                    saveToDatabase(data)
                }
            }
            updateStatus(statusId, ProcessingStatusEnum.PROCESSED.code, ProcessingStatusEnum.PROCESSED.description)
        } catch (e: DataIntegrityViolationException) {
            val detailMessage = ErrorMessageGetter.getDetailMessage(e.cause?.message)
            updateStatus(statusId, ProcessingStatusEnum.FAILED.code, "$detailMessage")
        } catch (e: Exception) {
            updateStatus(statusId, ProcessingStatusEnum.FAILED.code, "${e.message}")
        }
    }

    private suspend fun saveToDatabase(orderData: OrderData) = coroutineScope {
        launch {
            userRepository.findByUserId(orderData.userId) ?:
                userRepository.save(User(userId = orderData.userId, name = orderData.name))

            productRepository.findByProductId(orderData.productId) ?:
                productRepository.save(Product(productId = orderData.productId, value = orderData.productValue))

            val order = Order(
                orderId = orderData.orderId,
                userId = orderData.userId,
                productId = orderData.productId,
                purchaseDate = orderData.purchaseDate
            )

            orderRepository.save(order)
        }
    }

    private fun updateStatus(id: Long, status: Int, description: String? = null) {
        val processingStatus = processingStatusRepository.findById(id).orElseThrow { IllegalArgumentException("Invalid status ID") }
        val updatedStatus = processingStatus.copy(status = status, description = description)
        processingStatusRepository.save(updatedStatus)
    }
}
