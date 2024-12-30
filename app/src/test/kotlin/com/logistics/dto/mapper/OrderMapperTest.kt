package com.logistics.dto.mapper

import com.logistics.dto.out.UserOrdersResponse
import com.logistics.dto.out.OrderResponse
import com.logistics.dto.out.ProductResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import java.math.BigDecimal
import java.time.LocalDate

class OrderMapperTest {

    private lateinit var orderMapper: OrderMapper

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        orderMapper = OrderMapper()
    }

    @Test
    fun `test extractUserId`() {
        val row = mapOf("user_id" to 1L)
        val userId = orderMapper.extractUserId(row)
        assertEquals(1L, userId)
    }

    @Test
    fun `test extractUserName`() {
        val row = mapOf("name" to "John Doe")
        val userName = orderMapper.extractUserName(row)
        assertEquals("John Doe", userName)
    }

    @Test
    fun `test extractOrderId`() {
        val row = mapOf("order_id" to 123L)
        val orderId = orderMapper.extractOrderId(row)
        assertEquals(123L, orderId)
    }

    @Test
    fun `test extractPurchaseDate`() {
        val row = mapOf("purchase_date" to "2021-12-01")
        val purchaseDate = orderMapper.extractPurchaseDate(row)
        assertEquals(LocalDate.of(2021, 12, 1), purchaseDate)
    }

    @Test
    fun `test createProductResponse`() {
        val row = mapOf("product_id" to 111L, "product_value" to BigDecimal("512.24"))
        val productResponse = orderMapper.createProductResponse(row)
        assertEquals(ProductResponse(111L, "512.24"), productResponse)
    }

    @Test
    fun `test findOrCreateOrderResponse - create new`() {
        val product = ProductResponse(111L, "512.24")
        val order = OrderResponse(123L, "512.24", "2021-12-01", listOf(product))
        val userOrdersResponse = UserOrdersResponse(1L, "John Doe", mutableListOf())

        val orderResponse = orderMapper.findOrCreateOrderResponse(userOrdersResponse, 123L, LocalDate.of(2021, 12, 1))
        assertEquals(orderResponse.orderId, 123L)
        assertEquals(orderResponse.date, "2021-12-01")
        assertTrue(userOrdersResponse.orders.contains(orderResponse))
    }

    @Test
    fun `test findOrCreateOrderResponse - find existing`() {
        val product = ProductResponse(111L, "512.24")
        val order = OrderResponse(123L, "512.24", "2021-12-01", mutableListOf(product))
        val userOrdersResponse = UserOrdersResponse(1L, "John Doe", mutableListOf(order))

        val orderResponse = orderMapper.findOrCreateOrderResponse(userOrdersResponse, 123L, LocalDate.of(2021, 12, 1))
        assertEquals(order, orderResponse)
    }

    @Test
    fun `test addProductToOrder`() {
        val product1 = ProductResponse(111L, "512.24")
        val product2 = ProductResponse(112L, "256.12")
        val order = OrderResponse(123L, "512.24", "2021-12-01", mutableListOf(product1))

        orderMapper.addProductToOrder(order, product2)
        assertTrue(order.products.contains(product2))
    }

    @Test
    fun `test calculateOrderTotal`() {
        val product1 = ProductResponse(111L, "512.24")
        val product2 = ProductResponse(112L, "256.12")
        val order = OrderResponse(123L, "0", "2021-12-01", mutableListOf(product1, product2))

        orderMapper.calculateOrderTotal(order)
        assertEquals("768.36", order.total)
    }
}
