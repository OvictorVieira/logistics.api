package com.logistics.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.io.*

class OrderIdTest {

    @Test
    fun `test OrderId creation`() {
        val orderId = OrderId(
            orderId = 123L,
            userId = 1L,
            productId = 111L
        )

        assertEquals(123L, orderId.orderId)
        assertEquals(1L, orderId.userId)
        assertEquals(111L, orderId.productId)
    }

    @Test
    fun `test OrderId equality and hashcode`() {
        val orderId1 = OrderId(
            orderId = 123L,
            userId = 1L,
            productId = 111L
        )

        val orderId2 = OrderId(
            orderId = 123L,
            userId = 1L,
            productId = 111L
        )

        val orderId3 = OrderId(
            orderId = 124L,
            userId = 2L,
            productId = 112L
        )

        assertEquals(orderId1, orderId2)
        assertNotEquals(orderId1, orderId3)
        assertEquals(orderId1.hashCode(), orderId2.hashCode())
        assertNotEquals(orderId1.hashCode(), orderId3.hashCode())
    }

    @Test
    fun `test OrderId serialization and deserialization`() {
        val orderId = OrderId(
            orderId = 123L,
            userId = 1L,
            productId = 111L
        )

        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(orderId)
        objectOutputStream.flush()
        objectOutputStream.close()

        val byteArrayInputStream = ByteArrayInputStream(byteArrayOutputStream.toByteArray())
        val objectInputStream = ObjectInputStream(byteArrayInputStream)
        val deserializedOrderId = objectInputStream.readObject() as OrderId

        assertEquals(orderId, deserializedOrderId)
    }
}
