package com.logistics.domain

import com.logistics.enum.ProcessingStatusEnum
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "processing_status", schema = "logistics")
data class ProcessingStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val fileName: String = "",
    val status: Int = ProcessingStatusEnum.PROCESSING.code,
    val description: String? = null
)
