package com.logistics.dto.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class OrderDataTest {

    @Test
    fun `test OrderData creation`() {
        val orderData = OrderData(
            userId = 1L,
            name = "John Doe",
            orderId = 123L,
            productId = 111L,
            productValue = BigDecimal("512.24"),
            purchaseDate = LocalDate.of(2021, 12, 1)
        )

        assertEquals(1L, orderData.userId)
        assertEquals("John Doe", orderData.name)
        assertEquals(123L, orderData.orderId)
        assertEquals(111L, orderData.productId)
        assertEquals(BigDecimal("512.24"), orderData.productValue)
        assertEquals(LocalDate.of(2021, 12, 1), orderData.purchaseDate)
    }

    @Test
    fun `test OrderData equality and hashcode`() {
        val orderData1 = OrderData(
            userId = 1L,
            name = "John Doe",
            orderId = 123L,
            productId = 111L,
            productValue = BigDecimal("512.24"),
            purchaseDate = LocalDate.of(2021, 12, 1)
        )

        val orderData2 = OrderData(
            userId = 1L,
            name = "John Doe",
            orderId = 123L,
            productId = 111L,
            productValue = BigDecimal("512.24"),
            purchaseDate = LocalDate.of(2021, 12, 1)
        )

        val orderData3 = OrderData(
            userId = 2L,
            name = "Jane Doe",
            orderId = 124L,
            productId = 112L,
            productValue = BigDecimal("256.12"),
            purchaseDate = LocalDate.of(2021, 12, 2)
        )

        assertEquals(orderData1, orderData2)
        assertNotEquals(orderData1, orderData3)
        assertEquals(orderData1.hashCode(), orderData2.hashCode())
        assertNotEquals(orderData1.hashCode(), orderData3.hashCode())
    }
}
