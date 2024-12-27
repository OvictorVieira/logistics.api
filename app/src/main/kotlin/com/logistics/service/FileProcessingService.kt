package com.logistics.service

import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import com.logistics.repository.ProcessingStatusRepository
import com.logistics.domain.ProcessingStatus
import com.logistics.enum.ProcessingStatusEnum

@Service
class FileProcessingService @Autowired constructor(
    private val processingStatusRepository: ProcessingStatusRepository
) {

    private val uploadDir: Path = Paths.get("uploads")

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

    private suspend fun processFile(filePath: Path, statusId: Long) {
        // TODO
    }
}
