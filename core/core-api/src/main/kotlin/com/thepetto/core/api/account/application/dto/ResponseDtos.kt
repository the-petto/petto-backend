package com.thepetto.core.api.account.application.dto

data class ResponseTokenDto(
    val accessToken: String,
    val refreshToken: String,
)