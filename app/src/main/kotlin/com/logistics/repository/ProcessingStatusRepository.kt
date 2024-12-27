package com.logistics.repository

import com.logistics.domain.ProcessingStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProcessingStatusRepository : JpaRepository<ProcessingStatus, Long>
