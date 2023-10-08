package com.thepetto.core.api.member.facade.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RequestRegisterMemberDto(
    val username: String,
    val password: String,
    val nickname: String,
)