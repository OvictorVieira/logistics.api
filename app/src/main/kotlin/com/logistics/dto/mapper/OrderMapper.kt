package com.logistics.dto.mapper

import com.logistics.dto.out.UserOrdersResponse
import com.logistics.dto.out.OrderResponse
import com.logistics.dto.out.ProductResponse
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class OrderMapper {

    fun extractUserId(row: Map<String, Any>): Long {
        return (row["user_id"] as Number).toLong()
    }

    fun extractUserName(row: Map<String, Any>): String {
        return row["name"] as String
    }

    fun extractOrderId(row: Map<String, Any>): Long {
        return (row["order_id"] as Number).toLong()
    }

    fun extractPurchaseDate(row: Map<String, Any>): LocalDate {
        return LocalDate.parse(row["purchase_date"].toString())
    }

    fun createProductResponse(row: Map<String, Any>): ProductResponse {
        val productId = (row["product_id"] as Number).toLong()
        val productValue = (row["product_value"] as BigDecimal).toString()
        return ProductResponse(productId, productValue)
    }

    fun findOrCreateOrderResponse(
        userOrders: UserOrdersResponse,
        orderId: Long,
        purchaseDate: LocalDate
    ): OrderResponse {
        return (userOrders.orders as MutableList).find { it.orderId == orderId } ?: run {
            val newOrder = OrderResponse(
                orderId = orderId,
                total = BigDecimal.ZERO.toString(),
                date = purchaseDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                products = mutableListOf()
            )
            userOrders.orders.add(newOrder)
            newOrder
        }
    }

    fun addProductToOrder(orderResponse: OrderResponse, productResponse: ProductResponse) {
        (orderResponse.products as MutableList).add(productResponse)
    }

    fun calculateOrderTotal(orderResponse: OrderResponse) {
        val total = orderResponse.products.map { BigDecimal(it.value) }.reduce(BigDecimal::add).toString()
        orderResponse.total = total
    }
}
