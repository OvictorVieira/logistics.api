package com.logistics.domain

import java.io.Serializable

data class OrderId(
    val orderId: Long = 0,
    val userId: Long = 0,
    val productId: Long = 0
) : Serializable
