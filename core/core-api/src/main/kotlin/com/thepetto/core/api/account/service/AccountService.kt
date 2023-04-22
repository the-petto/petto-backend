package com.thepetto.core.api.account.service

import com.thepetto.core.api.account.dto.RequestAuthenticateDto
import com.thepetto.core.api.account.dto.RequestRefreshTokenDto
import com.thepetto.core.api.account.dto.ResponseTokenDto

interface AccountService {
    fun authenticate(requestAuthenticateDto: RequestAuthenticateDto): ResponseTokenDto
    fun refreshToken(requestRefreshTokenDto: RequestRefreshTokenDto): ResponseTokenDto
    fun invalidateRefreshTokenByUsername(username: String)
}