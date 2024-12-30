package com.logistics.dto.out

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class UserOrdersResponseTest {

    @Test
    fun `test UserOrdersResponse creation`() {
        val product = ProductResponse(111L, "512.24")
        val order = OrderResponse(123L, "512.24", "2021-12-01", listOf(product))
        val userOrdersResponse = UserOrdersResponse(1L, "Zarelli", listOf(order))

        assertEquals(1L, userOrdersResponse.userId)
        assertEquals("Zarelli", userOrdersResponse.name)
        assertEquals(1, userOrdersResponse.orders.size)
        assertEquals(order, userOrdersResponse.orders[0])
    }

    @Test
    fun `test UserOrdersResponse equality and hashcode`() {
        val product = ProductResponse(111L, "512.24")
        val order = OrderResponse(123L, "512.24", "2021-12-01", listOf(product))

        val userOrdersResponse1 = UserOrdersResponse(1L, "Zarelli", listOf(order))
        val userOrdersResponse2 = UserOrdersResponse(1L, "Zarelli", listOf(order))
        val userOrdersResponse3 = UserOrdersResponse(2L, "Medeiros", listOf(order))

        assertEquals(userOrdersResponse1, userOrdersResponse2)
        assertNotEquals(userOrdersResponse1, userOrdersResponse3)
        assertEquals(userOrdersResponse1.hashCode(), userOrdersResponse2.hashCode())
        assertNotEquals(userOrdersResponse1.hashCode(), userOrdersResponse3.hashCode())
    }
}
