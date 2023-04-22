package com.thepetto.core.api.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.thepetto.core.api.global.dto.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

// 인증 실패 시
@Component
class JwtAuthenticationEntryPoint(
    val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401(인증 실패)
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        val error = ErrorResponse(
            message = "UNAUTHORIZED"
        )

        response.contentType = "application/json";
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.outputStream.println(
            objectMapper.writeValueAsString(error)
        );
    }
}