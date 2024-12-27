package com.logistics.util

object ErrorMessageGetter {

    fun getDetailMessage(errorMessage: String?): String? {
        val regex = """Detalhe:(.*?)]""".toRegex()
        val matchResult = errorMessage?.let { regex.find(it) }
        return matchResult?.groups?.get(1)?.value?.trim()
    }
}
