package com.logistics.controller

import com.logistics.dto.out.ApiResponse
import com.logistics.dto.out.UserOrdersResponse
import com.logistics.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController @Autowired constructor(
    private val orderService: OrderService
) {

    @GetMapping("/all")
    fun getAllOrdersGroupedByUser(): ResponseEntity<ApiResponse<List<UserOrdersResponse>>> {
        return try {
            val allOrders = orderService.getAllOrdersGroupedByUser()

            val response = ApiResponse( success = true, message = "Get all orders successfully", data = allOrders)

            ResponseEntity(response, HttpStatus.OK)
        } catch (e: Exception) {
            val response = ApiResponse<List<UserOrdersResponse>>( success = false, message = "An unexpected error occurred: ${e.message}" )
            ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}
