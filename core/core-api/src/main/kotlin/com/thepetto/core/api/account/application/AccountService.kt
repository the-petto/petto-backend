package com.thepetto.core.api.account.application

import com.thepetto.core.api.account.application.dto.*

interface AccountService {
    fun authenticate(requestAuthenticateDto: RequestAuthenticateDto): ResponseTokenDto
    fun refreshToken(requestRefreshTokenDto: RequestRefreshTokenDto): ResponseTokenDto
    fun invalidateRefreshTokenByUsername(username: String)
    fun registerMemberAccount(registerDto: RequestRegisterMemberAccountDto): ResponseRegisterMemberAccountDto
    fun getAccountWithAuthorities(username: String): ResponseRegisterMemberAccountDto
}