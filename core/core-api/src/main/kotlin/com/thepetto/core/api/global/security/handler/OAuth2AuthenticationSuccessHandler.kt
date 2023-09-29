package com.thepetto.core.api.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.thepetto.core.api.global.dto.CommonResponse
import com.thepetto.core.api.oauth2account.facade.OAuth2AccountFacade
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.PrintWriter
import java.nio.charset.StandardCharsets

/**
 * OAuth2 로그인을 시도하여 authentication 객체가 성공적으로 만들어진 경우 성공핸들러
 */
@Component
class OAuth2AuthenticationSuccessHandler(
    private val objectMapper: ObjectMapper,
    private val oAuth2AccountFacade: OAuth2AccountFacade,
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {

        // responseUniqueIdOrToken는 Account가 이미 존재한다면 토큰을 응답한다.
        // OAuth 로그인을 하더라도 Account 계정이 무조건 생성되도록 해야한다.
        // Account가 존재하지 않다면, oauth2 unique 식별자를 클라이언트에게 주고, 프론트는 Account 회원가입 페이지로 유도한다.
        // 프론트에서는 Account 회원가입 시 해당 식별값을 넘겨주도록 하여 Account 와 OAuth2Account 를 매핑한다.
        val responseOAuth2LoginDto = oAuth2AccountFacade.responseUniqueIdOrToken(authentication!!)
        val responseBody = CommonResponse(
            message = "success",
            data = responseOAuth2LoginDto
        )

        // view
        response!!.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()

        val writer: PrintWriter = response.writer
        writer.println(objectMapper.writeValueAsString(responseBody))
        writer.flush()
    }
}