package com.logistics.dto.mapper

import java.math.BigDecimal
import java.time.LocalDate

data class OrderData(
    val userId: Long,
    val name: String,
    val orderId: Long,
    val productId: Long,
    val productValue: BigDecimal,
    val purchaseDate: LocalDate
)
