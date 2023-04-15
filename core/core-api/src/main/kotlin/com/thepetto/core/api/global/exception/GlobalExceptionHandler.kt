package com.thepetto.core.api.global.exception

import com.thepetto.core.api.global.dto.ErrorResponse
import com.thepetto.core.api.global.exception.custom.DuplicateMemberException
import com.thepetto.core.api.global.exception.custom.InvalidRefreshTokenException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {
    /**
     * Bean Validation에 실패했을 때, 에러메시지를 내보내기 위한 Exception Handler
     */
    @ExceptionHandler(BindException::class)
    protected fun handleBindException(ex: BindException): ResponseEntity<ErrorResponse> {
        val customCode = CustomExceptionCode.REQUEST_PARAMETER_BIND_FAILED
        val response = ErrorResponse(
            message = customCode.message
        )

        return ResponseEntity<ErrorResponse>(response, customCode.status)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val customCode = CustomExceptionCode.REQUEST_PARAMETER_BIND_FAILED
        val response = ErrorResponse(
            message = customCode.message
        )

        return ResponseEntity<ErrorResponse>(response, customCode.status)
    }

    @ExceptionHandler(DuplicateMemberException::class)
    protected fun handleDuplicateMemberException(ex: DuplicateMemberException): ResponseEntity<ErrorResponse> {
        val customCode = CustomExceptionCode.DUPLICATE_MEMBER_EXCEPTION
        val response = ErrorResponse(
            message = customCode.message
        )

        return ResponseEntity<ErrorResponse>(response, customCode.status)
    }

    @ExceptionHandler(InvalidRefreshTokenException::class)
    protected fun handleInvalidRefreshTokenException(ex: InvalidRefreshTokenException): ResponseEntity<ErrorResponse> {
        val customCode = CustomExceptionCode.INVALID_REFRESH_TOKEN
        val response = ErrorResponse(
            message = customCode.message
        )

        return ResponseEntity<ErrorResponse>(response, customCode.status)
    }
}