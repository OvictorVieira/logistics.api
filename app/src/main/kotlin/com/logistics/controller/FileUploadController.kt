package com.logistics.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import com.logistics.service.FileProcessingService
import com.logistics.dto.out.FileUploadResponse
import com.logistics.enum.ProcessingStatusEnum

@RestController
@RequestMapping("/api/files")
class FileUploadController @Autowired constructor(
    private val fileProcessingService: FileProcessingService
) {

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<FileUploadResponse> {
        return try {
            val savedFileStatus = fileProcessingService.initiateFileProcessing(file)
            val response = FileUploadResponse(id = savedFileStatus.id, status = ProcessingStatusEnum.PROCESSING.description)
            ResponseEntity(response, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
