package com.logistics.util

import com.logistics.dto.mapper.OrderData
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LineParser {

    fun parseLine(line: String): OrderData {
        val userId = parseLong(line, 0, 10)
        val userName = parseString(line, 10, 55)
        val orderId = parseLong(line, 55, 65)
        val productId = parseLong(line, 65, 75)
        val value = parseBigDecimal(line, 75, 87)
        val purchaseDate = parseDate(line, 87, 95)

        return OrderData(
            userId = userId,
            name = userName,
            orderId = orderId,
            productId = productId,
            productValue = value,
            purchaseDate = purchaseDate
        )
    }

    private fun parseLong(line: String, start: Int, end: Int): Long {
        val value = line.substring(start, end).trimStart('0')
        return if (value.isEmpty()) 0L else value.toLong()
    }

    private fun parseString(line: String, start: Int, end: Int): String {
        return line.substring(start, end).trim()
    }

    private fun parseBigDecimal(line: String, start: Int, end: Int): BigDecimal {
        return line.substring(start, end).trim().toBigDecimal()
    }

    private fun parseDate(line: String, start: Int, end: Int): LocalDate {
        val date = line.substring(start, end)
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"))
    }
}
