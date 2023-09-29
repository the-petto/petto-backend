package com.thepetto.core.api.oauth2account.facade.dto

data class ResponseOAuth2LoginDto(
    val oauth2AccountId: String,
    val accessToken: String?,
    val refreshToken: String?,
)