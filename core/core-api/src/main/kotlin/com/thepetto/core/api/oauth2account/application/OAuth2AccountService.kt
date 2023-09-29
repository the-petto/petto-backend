package com.thepetto.core.api.oauth2account.application

import com.thepetto.core.api.oauth2account.application.dto.ResponseOAuth2UniqueDto
import org.springframework.security.core.Authentication

interface OAuth2AccountService {

    fun getUniqueId(authentication: Authentication): ResponseOAuth2UniqueDto
}