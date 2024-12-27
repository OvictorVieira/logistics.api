package com.logistics.repository

import com.logistics.domain.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    @Query(
        """ 
        SELECT 
            o.order_id,
            o.user_id,
            u.name,
            o.product_id,
            p.value AS product_value,
            o.purchase_date
        FROM
            logistics.orders o 
        JOIN logistics.users u ON o.user_id = u.user_id
        JOIN logistics.products p ON o.product_id = p.product_id
        """,
        nativeQuery = true
    )
    fun findAllOrdersWithUserAndProduct(): List<Map<String, Any>>
}
