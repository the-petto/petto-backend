package com.thepetto.core.api.account.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RequestAuthenticateDto(
    @field:NotNull
    @field:Size(min = 3, max = 50)
    val username: String,

    @field:NotNull
    @field:Size(min = 5, max = 100)
    val password: String,
)

data class RequestRefreshTokenDto(
    @field:NotNull
    val refreshToken: String,
)