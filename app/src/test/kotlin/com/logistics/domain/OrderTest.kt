package com.logistics.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class OrderTest {

    @Test
    fun `test Order creation`() {
        val order = Order(
            orderId = 123L,
            userId = 1L,
            productId = 111L,
            purchaseDate = LocalDate.of(2021, 12, 1)
        )

        assertEquals(123L, order.orderId)
        assertEquals(1L, order.userId)
        assertEquals(111L, order.productId)
        assertEquals(LocalDate.of(2021, 12, 1), order.purchaseDate)
    }

    @Test
    fun `test Order equality and hashcode`() {
        val order1 = Order(
            orderId = 123L,
            userId = 1L,
            productId = 111L,
            purchaseDate = LocalDate.of(2021, 12, 1)
        )

        val order2 = Order(
            orderId = 123L,
            userId = 1L,
            productId = 111L,
            purchaseDate = LocalDate.of(2021, 12, 1)
        )

        val order3 = Order(
            orderId = 124L,
            userId = 2L,
            productId = 112L,
            purchaseDate = LocalDate.of(2021, 12, 2)
        )

        assertEquals(order1, order2)
        assertNotEquals(order1, order3)
        assertEquals(order1.hashCode(), order2.hashCode())
        assertNotEquals(order1.hashCode(), order3.hashCode())
    }

    @Test
    fun `test Order serialization and deserialization`() {
        val order = Order(
            orderId = 123L,
            userId = 1L,
            productId = 111L,
            purchaseDate = LocalDate.of(2021, 12, 1)
        )

        val json = serialize(order)
        val deserializedOrder = deserialize<Order>(json)

        assertEquals(order, deserializedOrder)
    }

    private fun serialize(order: Order): String {
        return """
            {
                "orderId": ${order.orderId},
                "userId": ${order.userId},
                "productId": ${order.productId},
                "purchaseDate": "${order.purchaseDate}"
            }
        """.trimIndent()
    }

    private inline fun <reified T> deserialize(json: String): T {
        val fields = json
            .trimIndent()
            .removeSurrounding("{", "}")
            .split(",")
            .map { it.trim().split(":").map(String::trim) }

        val orderId = fields.find { it[0] == "\"orderId\"" }!![1].toLong()
        val userId = fields.find { it[0] == "\"userId\"" }!![1].toLong()
        val productId = fields.find { it[0] == "\"productId\"" }!![1].toLong()
        val purchaseDate = LocalDate.parse(fields.find { it[0] == "\"purchaseDate\"" }!![1].removeSurrounding("\""))

        return Order(orderId, userId, productId, purchaseDate) as T
    }
}
