package com.logistics.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "orders", schema = "logistics")
@IdClass(OrderId::class)
data class Order(
    @Id
    val orderId: Long = 0,
    @Id
    val userId: Long = 0,
    @Id
    val productId: Long = 0,
    val purchaseDate: LocalDate = LocalDate.now()
)
