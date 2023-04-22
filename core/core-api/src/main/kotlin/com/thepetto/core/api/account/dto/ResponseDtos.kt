package com.thepetto.core.api.account.dto

data class ResponseTokenDto(
    val accessToken: String,
    val refreshToken: String,
)