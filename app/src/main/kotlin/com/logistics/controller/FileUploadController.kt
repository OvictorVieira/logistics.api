package com.logistics.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import com.logistics.service.FileProcessingService
import com.logistics.dto.out.FileUploadResponse
import com.logistics.enum.ProcessingStatusEnum
import com.logistics.exception.RecordNotFoundException
import com.logistics.util.ApiResponse

@RestController
@RequestMapping("/api/files")
class FileUploadController @Autowired constructor(
    private val fileProcessingService: FileProcessingService
) {

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<ApiResponse<FileUploadResponse>> {
        return try {
            val savedFileStatus = fileProcessingService.initiateFileProcessing(file)
            val fileUploadResponse = FileUploadResponse(id = savedFileStatus.id, status = ProcessingStatusEnum.PROCESSING.description)
            val response = ApiResponse( success = true, message = "Your file will be processed asap", data = fileUploadResponse)

            ResponseEntity(response, HttpStatus.OK)
        } catch (e: Exception) {
            val response = ApiResponse<FileUploadResponse>( success = false, message = "An unexpected error occurred: ${e.message}" )
            ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/processing-status/{id}")
    fun getProcessingStatus(@PathVariable id: Long): ResponseEntity<ApiResponse<FileUploadResponse>> {
        return try {
            val processFile = fileProcessingService.getProcessingStatus(id)
            val fileUploadResponse = FileUploadResponse(id = processFile.id, status = ProcessingStatusEnum.fromCode(processFile.status).description)
            val response = ApiResponse( success = true, message = "Processing status retrieved successfully", data = fileUploadResponse)

            ResponseEntity(response, HttpStatus.OK)
        } catch (e: RecordNotFoundException) {
            val response = ApiResponse<FileUploadResponse>( success = false, message = "Record not found" )
            ResponseEntity(response, HttpStatus.NOT_FOUND)
        } catch (e: Exception) {
            val response = ApiResponse<FileUploadResponse>( success = false, message = "An unexpected error occurred: ${e.message}" )
            ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
