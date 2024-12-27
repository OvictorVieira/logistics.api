package com.logistics.util

import com.logistics.dto.mapper.OrderData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class LineParserTest {

    @Test
    fun `test parseLine with valid input`() {
        val line1 = "0000000049                               Ken Wintheiser00000005230000000003      586.7420210903"
        val line2 = "0000000014                                 Clelia Hills00000001460000000001      673.4920211125"
        val line3 = "0000000057                          Elidia Gulgowski IV00000006200000000000     1417.2520210919"

        val expectedOrderData1 = OrderData(
            userId = 49L,
            name = "Ken Wintheiser",
            orderId = 523L,
            productId = 3L,
            productValue = BigDecimal("586.74"),
            purchaseDate = LocalDate.of(2021, 9, 3)
        )

        val expectedOrderData2 = OrderData(
            userId = 14L,
            name = "Clelia Hills",
            orderId = 146L,
            productId = 1L,
            productValue = BigDecimal("673.49"),
            purchaseDate = LocalDate.of(2021, 11, 25)
        )

        val expectedOrderData3 = OrderData(
            userId = 57L,
            name = "Elidia Gulgowski IV",
            orderId = 620L,
            productId = 0L,
            productValue = BigDecimal("1417.25"),
            purchaseDate = LocalDate.of(2021, 9, 19)
        )

        val actualOrderData1 = LineParser.parseLine(line1)
        val actualOrderData2 = LineParser.parseLine(line2)
        val actualOrderData3 = LineParser.parseLine(line3)

        assertEquals(expectedOrderData1, actualOrderData1)
        assertEquals(expectedOrderData2, actualOrderData2)
        assertEquals(expectedOrderData3, actualOrderData3)
    }
}
