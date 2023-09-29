package com.thepetto.core.api.account.application

import com.thepetto.core.api.account.application.dto.*

interface AccountService {

    fun authenticate(requestAuthenticateDto: RequestAuthenticateDto): ResponseTokenDto

    /**
     * oauth2UniqueId 식별자를 이용해 Account 를 찾고, 그 Account 기준으로 토큰을 만들어준다.
     */
    fun authenticate(oauth2UniqueId: String): ResponseTokenDto

    fun refreshToken(requestRefreshTokenDto: RequestRefreshTokenDto): ResponseTokenDto

    fun invalidateRefreshTokenByUsername(username: String)
    fun registerMemberAccount(registerDto: RequestRegisterMemberAccountDto): ResponseRegisterMemberAccountDto
    fun getAccountWithAuthorities(username: String): ResponseRegisterMemberAccountDto
}