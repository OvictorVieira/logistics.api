package com.logistics.service

import com.logistics.dto.mapper.OrderMapper
import com.logistics.repository.OrderRepository
import com.logistics.dto.out.UserOrdersResponse
import com.logistics.dto.out.OrderResponse
import com.logistics.dto.out.ProductResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.math.BigDecimal
import java.time.LocalDate

class OrderServiceTest {
    @Mock
    private lateinit var orderRepository: OrderRepository
    @Mock
    private lateinit var orderMapper: OrderMapper
    @InjectMocks
    private lateinit var orderService: OrderService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getOrdersWithFilters - no filters`() {
        val mockResults = listOf(
            mapOf(
                "user_id" to 1L,
                "name" to "Zarelli",
                "order_id" to 123L,
                "product_id" to 111L,
                "product_value" to BigDecimal("512.24"),
                "purchase_date" to "2021-12-01"
            ),
            mapOf(
                "user_id" to 2L,
                "name" to "Medeiros",
                "order_id" to 124L,
                "product_id" to 122L,
                "product_value" to BigDecimal("256.24"),
                "purchase_date" to "2021-11-01"
            )
        )

        val mockProductResponse1 = ProductResponse(111L, "512.24")
        val mockOrderResponse1 = OrderResponse(123L, "512.24", "2021-12-01", mutableListOf(mockProductResponse1))
        val mockUserOrdersResponse1 = UserOrdersResponse(1L, "Zarelli", mutableListOf(mockOrderResponse1))

        val mockProductResponse2 = ProductResponse(122L, "256.24")
        val mockOrderResponse2 = OrderResponse(124L, "256.24", "2021-11-01", mutableListOf(mockProductResponse2))
        val mockUserOrdersResponse2 = UserOrdersResponse(2L, "Medeiros", mutableListOf(mockOrderResponse2))

        Mockito.`when`(orderRepository.findOrdersWithFilters(null, null, null)).thenReturn(mockResults)

        Mockito.`when`(orderMapper.extractUserId(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            map["user_id"] as Long
        }
        Mockito.`when`(orderMapper.extractUserName(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            map["name"] as String
        }
        Mockito.`when`(orderMapper.extractOrderId(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            map["order_id"] as Long
        }
        Mockito.`when`(orderMapper.createProductResponse(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            ProductResponse(
                productId = map["product_id"] as Long,
                value = (map["product_value"] as BigDecimal).toString()
            )
        }
        Mockito.`when`(orderMapper.extractPurchaseDate(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            LocalDate.parse(map["purchase_date"].toString())
        }
        Mockito.`when`(orderMapper.findOrCreateOrderResponse(any(), any(), any())).thenAnswer { invocation ->
            val userOrders = invocation.arguments[0] as UserOrdersResponse
            val orderId = invocation.arguments[1] as Long
            val purchaseDate = invocation.arguments[2] as LocalDate
            userOrders.orders.find { it.orderId == orderId } ?: OrderResponse(
                orderId = orderId,
                total = BigDecimal.ZERO.toString(),
                date = purchaseDate.toString(),
                products = mutableListOf()
            )
        }
        Mockito.doNothing().`when`(orderMapper).addProductToOrder(any(), any())
        Mockito.doNothing().`when`(orderMapper).calculateOrderTotal(any())

        val result = orderService.getOrdersWithFilters(null, null, null)
        val expected = listOf(mockUserOrdersResponse1, mockUserOrdersResponse2)
        assertEquals(expected.size, result.size)
    }

    @Test
    fun `getOrdersWithFilters - userId filter`() {
        val mockResults = listOf(
            mapOf(
                "user_id" to 1L,
                "name" to "Zarelli",
                "order_id" to 123L,
                "product_id" to 111L,
                "product_value" to BigDecimal("512.24"),
                "purchase_date" to "2021-12-01"
            )
        )

        val mockProductResponse = ProductResponse(111L, "512.24")
        val mockOrderResponse = OrderResponse(123L, "512.24", "2021-12-01", mutableListOf(mockProductResponse))
        val mockUserOrdersResponse = UserOrdersResponse(1L, "Zarelli", mutableListOf(mockOrderResponse))

        Mockito.`when`(orderRepository.findOrdersWithFilters(1L, null, null)).thenReturn(mockResults)

        Mockito.`when`(orderMapper.extractUserId(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            map["user_id"] as Long
        }
        Mockito.`when`(orderMapper.extractUserName(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            map["name"] as String
        }
        Mockito.`when`(orderMapper.extractOrderId(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            map["order_id"] as Long
        }
        Mockito.`when`(orderMapper.createProductResponse(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            ProductResponse(
                productId = map["product_id"] as Long,
                value = (map["product_value"] as BigDecimal).toString()
            )
        }
        Mockito.`when`(orderMapper.extractPurchaseDate(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            LocalDate.parse(map["purchase_date"].toString())
        }
        Mockito.`when`(orderMapper.findOrCreateOrderResponse(any(), any(), any())).thenAnswer { invocation ->
            val userOrders = invocation.arguments[0] as UserOrdersResponse
            val orderId = invocation.arguments[1] as Long
            val purchaseDate = invocation.arguments[2] as LocalDate
            userOrders.orders.find { it.orderId == orderId } ?: OrderResponse(
                orderId = orderId,
                total = BigDecimal.ZERO.toString(),
                date = purchaseDate.toString(),
                products = mutableListOf()
            )
        }
        Mockito.doNothing().`when`(orderMapper).addProductToOrder(any(), any())
        Mockito.doNothing().`when`(orderMapper).calculateOrderTotal(any())

        val result = orderService.getOrdersWithFilters(1L, null, null)
        val expected = listOf(mockUserOrdersResponse)
        assertEquals(expected.size, result.size)
    }

    @Test
    fun `getOrdersWithFilters - date range filter`() {
        val mockResults = listOf(
            mapOf(
                "user_id" to 1L,
                "name" to "Zarelli",
                "order_id" to 123L,
                "product_id" to 111L,
                "product_value" to BigDecimal("512.24"),
                "purchase_date" to "2021-12-01"
            )
        )

        val mockProductResponse = ProductResponse(111L, "512.24")
        val mockOrderResponse = OrderResponse(123L, "512.24", "2021-12-01", mutableListOf(mockProductResponse))
        val mockUserOrdersResponse = UserOrdersResponse(1L, "Zarelli", mutableListOf(mockOrderResponse))

        Mockito.`when`(
            orderRepository.findOrdersWithFilters(
                null,
                LocalDate.of(2021, 5, 1),
                LocalDate.of(2021, 12, 31)
            )
        ).thenReturn(mockResults)

        Mockito.`when`(orderMapper.extractUserId(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            map["user_id"] as Long
        }
        Mockito.`when`(orderMapper.extractUserName(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            map["name"] as String
        }
        Mockito.`when`(orderMapper.extractOrderId(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            map["order_id"] as Long
        }
        Mockito.`when`(orderMapper.createProductResponse(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            ProductResponse(
                productId = map["product_id"] as Long,
                value = (map["product_value"] as BigDecimal).toString()
            )
        }
        Mockito.`when`(orderMapper.extractPurchaseDate(Mockito.anyMap())).thenAnswer { invocation ->
            val map = invocation.arguments[0] as Map<*, *>
            LocalDate.parse(map["purchase_date"].toString())
        }
        Mockito.`when`(orderMapper.findOrCreateOrderResponse(any(), any(), any())).thenAnswer { invocation ->
            val userOrders = invocation.arguments[0] as UserOrdersResponse
            val orderId = invocation.arguments[1] as Long
            val purchaseDate = invocation.arguments[2] as LocalDate
            userOrders.orders.find { it.orderId == orderId } ?: OrderResponse(
                orderId = orderId,
                total = BigDecimal.ZERO.toString(),
                date = purchaseDate.toString(),
                products = mutableListOf()
            )
        }
        Mockito.doNothing().`when`(orderMapper).addProductToOrder(any(), any())
        Mockito.doNothing().`when`(orderMapper).calculateOrderTotal(any())

        val result = orderService.getOrdersWithFilters(null, LocalDate.of(2021, 5, 1), LocalDate.of(2021, 12, 31))
        val expected = listOf(mockUserOrdersResponse)
        assertEquals(expected.size, result.size)
    }
}