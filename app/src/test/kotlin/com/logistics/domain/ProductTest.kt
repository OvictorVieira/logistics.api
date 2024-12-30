package com.logistics.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductTest {

    @Test
    fun `test Product creation`() {
        val product = Product(
            productId = 123L,
            value = BigDecimal("512.24")
        )

        assertEquals(123L, product.productId)
        assertEquals(BigDecimal("512.24"), product.value)
    }

    @Test
    fun `test Product equality and hashcode`() {
        val product1 = Product(
            productId = 123L,
            value = BigDecimal("512.24")
        )

        val product2 = Product(
            productId = 123L,
            value = BigDecimal("512.24")
        )

        val product3 = Product(
            productId = 124L,
            value = BigDecimal("256.12")
        )

        assertEquals(product1, product2)
        assertNotEquals(product1, product3)
        assertEquals(product1.hashCode(), product2.hashCode())
        assertNotEquals(product1.hashCode(), product3.hashCode())
    }
}
