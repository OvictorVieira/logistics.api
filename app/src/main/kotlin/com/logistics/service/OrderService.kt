package com.logistics.service

import com.logistics.repository.OrderRepository
import com.logistics.dto.out.UserOrdersResponse
import com.logistics.dto.out.OrderResponse
import com.logistics.dto.out.ProductResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class OrderService @Autowired constructor(
    private val orderRepository: OrderRepository
) {

    fun getAllOrdersGroupedByUser(): List<UserOrdersResponse> {
        val results = orderRepository.findAllOrdersWithUserAndProduct()
        return mapResultsToUserOrders(results)
    }

    private fun mapResultsToUserOrders(results: List<Map<String, Any>>): List<UserOrdersResponse> {
        val userOrderMap = mutableMapOf<Long, UserOrdersResponse>()

        results.forEach { row ->
            val userId = extractUserId(row)
            val userName = extractUserName(row)
            val orderId = extractOrderId(row)
            val productResponse = createProductResponse(row)
            val purchaseDate = extractPurchaseDate(row)

            val userOrders = userOrderMap.getOrPut(userId) {
                UserOrdersResponse(userId, userName, mutableListOf())
            }

            val orderResponse = findOrCreateOrderResponse(userOrders, orderId, purchaseDate)
            addProductToOrder(orderResponse, productResponse)
            calculateOrderTotal(orderResponse)
        }

        return userOrderMap.values.toList()
    }

    private fun extractUserId(row: Map<String, Any>): Long {
        return (row["user_id"] as Number).toLong()
    }

    private fun extractUserName(row: Map<String, Any>): String {
        return row["name"] as String
    }

    private fun extractOrderId(row: Map<String, Any>): Long {
        return (row["order_id"] as Number).toLong()
    }

    private fun extractPurchaseDate(row: Map<String, Any>): LocalDate {
        return LocalDate.parse(row["purchase_date"].toString())
    }

    private fun createProductResponse(row: Map<String, Any>): ProductResponse {
        val productId = (row["product_id"] as Number).toLong()
        val productValue = (row["product_value"] as BigDecimal).toString()
        return ProductResponse(productId, productValue)
    }

    private fun findOrCreateOrderResponse(userOrders: UserOrdersResponse, orderId: Long, purchaseDate: LocalDate): OrderResponse {
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

    private fun addProductToOrder(orderResponse: OrderResponse, productResponse: ProductResponse) {
        (orderResponse.products as MutableList).add(productResponse)
    }

    private fun calculateOrderTotal(orderResponse: OrderResponse) {
        val total = orderResponse.products.map { BigDecimal(it.value) }.reduce(BigDecimal::add).toString()
        orderResponse.total = total
    }
}
