package com.thepetto.core.api.global.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.thepetto.core.api.global.dto.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.security.access.AccessDeniedException

// 인가 실패
@Component
class JwtAccessDeniedHandler(
    val objectMapper: ObjectMapper
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        //필요한 권한이 없이 접근하려 할때 403
        val error = ErrorResponse(
            message = "FORBIDDEN"
        )

        response.contentType = "application/json";
        response.status = HttpServletResponse.SC_FORBIDDEN;
        response.outputStream.println(
            objectMapper.writeValueAsString(error)
        )
    }

}