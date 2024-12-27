package com.logistics.enum

enum class ProcessingStatusEnum(val code: Int, val description: String) {
    PROCESSING(1, "Processing"),
    PROCESSED(2, "Processed"),
    FAILED(3, "Failed");

    companion object {
        fun fromCode(code: Int): ProcessingStatusEnum {
            return entries.find { it.code == code } ?: throw IllegalArgumentException("Invalid status code: $code")
        }
    }
}
