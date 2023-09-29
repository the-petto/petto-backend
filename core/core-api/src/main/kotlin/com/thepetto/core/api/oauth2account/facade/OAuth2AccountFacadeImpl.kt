package com.thepetto.core.api.oauth2account.facade

import com.thepetto.core.api.account.application.AccountService
import com.thepetto.core.api.oauth2account.application.OAuth2AccountService
import com.thepetto.core.api.oauth2account.facade.dto.ResponseOAuth2LoginDto
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class OAuth2AccountFacadeImpl(
    private val oAuth2AccountService: OAuth2AccountService,
    private val accountService: AccountService,
) : OAuth2AccountFacade {

    override fun responseUniqueIdOrToken(authentication: Authentication): ResponseOAuth2LoginDto {
        val responseOAuth2UniqueDto = oAuth2AccountService.getUniqueId(authentication)

        return try {
            val responseTokenDto = accountService.authenticate(responseOAuth2UniqueDto.oauth2AccountId)

            ResponseOAuth2LoginDto(
                oauth2AccountId = responseOAuth2UniqueDto.oauth2AccountId,
                accessToken = responseTokenDto.accessToken,
                refreshToken = responseTokenDto.refreshToken,
            )
        } catch (e: Exception) { // TODO : 에러 명확
            ResponseOAuth2LoginDto(
                oauth2AccountId = responseOAuth2UniqueDto.oauth2AccountId,
                accessToken = null,
                refreshToken = null,
            )
        }
    }
}