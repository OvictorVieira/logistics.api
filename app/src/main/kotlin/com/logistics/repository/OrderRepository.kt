package com.logistics.repository

import com.logistics.domain.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface OrderRepository : JpaRepository<Order, Long> {

    @Query(
        """
        SELECT 
            o.order_id, o.user_id, u.name, p.product_id, p.value AS product_value, o.purchase_date
        FROM 
            logistics.orders o
        JOIN 
            logistics.users u ON o.user_id = u.user_id
        JOIN 
            logistics.products p ON o.product_id = p.product_id
        WHERE  
            (:userId IS NULL OR o.user_id = :userId )
          AND ( Cast(:fromDate AS DATE) IS NULL OR o.purchase_date >= Cast(:fromDate AS DATE) )
          AND ( Cast(:toDate AS DATE) IS NULL OR o.purchase_date <= Cast(:toDate AS DATE) )
        """,
        nativeQuery = true
    )
    fun findOrdersWithFilters(
        @Param("userId") userId: Long?,
        @Param("fromDate") fromDate: LocalDate?,
        @Param("toDate") toDate: LocalDate?
    ): List<Map<String, Any>>
}
