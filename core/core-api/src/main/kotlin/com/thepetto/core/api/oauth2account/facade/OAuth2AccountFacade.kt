package com.thepetto.core.api.oauth2account.facade

import com.thepetto.core.api.oauth2account.facade.dto.ResponseOAuth2LoginDto
import org.springframework.security.core.Authentication

// 파사드 레이어는 다른 서비스를 호출해야하는 경우 선택적 사용
interface OAuth2AccountFacade {

    /**
     * * Account 계정이 존재하지 않으면 OAUTH2 UniqueId를, Account 계정이 존재하면 토큰을 준다.
     */
    fun responseUniqueIdOrToken(authentication: Authentication): ResponseOAuth2LoginDto
}