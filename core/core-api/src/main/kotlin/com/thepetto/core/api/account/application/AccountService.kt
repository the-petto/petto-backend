package com.thepetto.core.api.account.application

import com.thepetto.core.api.account.application.dto.RequestAuthenticateDto
import com.thepetto.core.api.account.application.dto.RequestRefreshTokenDto
import com.thepetto.core.api.account.application.dto.ResponseTokenDto

interface AccountService {
    fun authenticate(requestAuthenticateDto: RequestAuthenticateDto): ResponseTokenDto
    fun refreshToken(requestRefreshTokenDto: RequestRefreshTokenDto): ResponseTokenDto
    fun invalidateRefreshTokenByUsername(username: String)
}