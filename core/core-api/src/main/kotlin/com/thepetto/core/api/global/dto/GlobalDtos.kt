package com.thepetto.core.api.global.dto

data class ErrorResponse(
    val message: String,
)

data class CommonResponse(
    val message: String? = null,
    val data: Any? = null,
)