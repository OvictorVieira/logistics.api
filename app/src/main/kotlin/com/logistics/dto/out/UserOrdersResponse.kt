package com.logistics.dto.out

data class UserOrdersResponse(
    val userId: Long,
    val name: String,
    val orders: List<OrderResponse>
)

data class OrderResponse(
    val orderId: Long,
    var total: String,
    val date: String,
    val products: List<ProductResponse>
)

data class ProductResponse(
    val productId: Long,
    val value: String
)
