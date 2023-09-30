package com.thepetto.core.api.member.application.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RequestRegisterMemberDto(
    @field:NotNull
    @field:Size(min = 3, max = 50)
    val username: String,

    @field:NotNull
    @field:Size(min = 5, max = 100)
    val password: String,

    @field:NotNull
    @field:Size(min = 5, max = 100)
    val nickname: String,
)