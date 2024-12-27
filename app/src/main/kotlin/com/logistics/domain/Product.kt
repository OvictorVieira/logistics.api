package com.logistics.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "products", schema = "logistics")
data class Product(
    @Id
    val productId: Long = 0,
    var value: BigDecimal = BigDecimal.ZERO
)
