package com.thepetto.core.api.global.exception

import org.springframework.http.HttpStatus

enum class CustomExceptionCode(
    val status: HttpStatus,
    val code: String,
    val message: String,
) {
    REQUEST_PARAMETER_BIND_FAILED(HttpStatus.BAD_REQUEST, "REQ_001", "PARAMETER_BIND_FAILED"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH_002", "권한이 없습니다"),
    DUPLICATE_MEMBER_EXCEPTION(HttpStatus.CONFLICT, "AUTH_001", "회원 중복"),
    INVALID_REFRESH_TOKEN(HttpStatus.FORBIDDEN, "AUTH_002", "리프레시 토큰이 유효하지 않습니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "STATE_001", "찾을 수 없음"),
}