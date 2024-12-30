package com.logistics.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.io.*

class UserTest {

    @Test
    fun `test User creation`() {
        val user = User(
            userId = 1L,
            name = "John Doe"
        )

        assertEquals(1L, user.userId)
        assertEquals("John Doe", user.name)
    }

    @Test
    fun `test User equality and hashcode`() {
        val user1 = User(
            userId = 1L,
            name = "John Doe"
        )

        val user2 = User(
            userId = 1L,
            name = "John Doe"
        )

        val user3 = User(
            userId = 2L,
            name = "Jane Doe"
        )

        assertEquals(user1, user2)
        assertNotEquals(user1, user3)
        assertEquals(user1.hashCode(), user2.hashCode())
        assertNotEquals(user1.hashCode(), user3.hashCode())
    }
}
