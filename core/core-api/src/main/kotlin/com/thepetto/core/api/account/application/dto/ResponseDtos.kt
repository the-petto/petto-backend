package com.thepetto.core.api.account.application.dto

import com.thepetto.core.api.account.domain.entity.Account

data class ResponseTokenDto(
    val accessToken: String,
    val refreshToken: String,
)

data class ResponseRegisterMemberAccountDto(
    val username: String,
    val nickname: String,
    val tokenWeight: Long,
    val authoritySet: Set<String>,
) {

    companion object Factory {
        fun of(account: Account): ResponseRegisterMemberAccountDto {
            return ResponseRegisterMemberAccountDto(
                username = account.username,
                nickname = account.nickname,
                tokenWeight = account.tokenWeight,
                authoritySet = account.authorities.map { authority ->
                    authority.authorityName
                }.toSet(),
            )
        }
    }
}