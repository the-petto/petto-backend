package com.thepetto.core.api.member.dto

import com.thepetto.core.api.account.domain.Account

data class ResponseRegisterMemberDto(
    val username: String,
    val nickname: String,
    val tokenWeight: Long,
    val authoritySet: Set<String>,
) {

    companion object Factory {
        fun of(account: Account): ResponseRegisterMemberDto {
            return ResponseRegisterMemberDto(
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