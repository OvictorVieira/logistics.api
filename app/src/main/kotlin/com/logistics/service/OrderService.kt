package com.logistics.service

import com.logistics.dto.mapper.OrderMapper
import com.logistics.repository.OrderRepository
import com.logistics.dto.out.UserOrdersResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class OrderService @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val orderMapper: OrderMapper
) {

    fun getOrdersWithFilters(userId: Long?, fromDate: LocalDate?, toDate: LocalDate?): List<UserOrdersResponse> {
        val results = orderRepository.findOrdersWithFilters(userId, fromDate, toDate)
        return mapResultsToUserOrders(results)
    }

    private fun mapResultsToUserOrders(results: List<Map<String, Any>>): List<UserOrdersResponse> {
        val userOrderMap = mutableMapOf<Long, UserOrdersResponse>()

        results.forEach { row ->
            val userId = orderMapper.extractUserId(row)
            val userName = orderMapper.extractUserName(row)
            val orderId = orderMapper.extractOrderId(row)
            val productResponse = orderMapper.createProductResponse(row)
            val purchaseDate = orderMapper.extractPurchaseDate(row)

            val userOrders = userOrderMap.getOrPut(userId) {
                UserOrdersResponse(userId, userName, mutableListOf())
            }

            val orderResponse = orderMapper.findOrCreateOrderResponse(userOrders, orderId, purchaseDate)
            orderMapper.addProductToOrder(orderResponse, productResponse)
            orderMapper.calculateOrderTotal(orderResponse)
        }

        return userOrderMap.values.toList()
    }
}
