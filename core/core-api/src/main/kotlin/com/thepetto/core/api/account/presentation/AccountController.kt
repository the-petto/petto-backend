package com.thepetto.core.api.account.presentation

import com.thepetto.core.api.account.application.dto.RequestAuthenticateDto
import com.thepetto.core.api.account.application.dto.RequestRefreshTokenDto
import com.thepetto.core.api.account.application.AccountService
import com.thepetto.core.api.global.dto.CommonResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "account", description = "계정 API")
@RestController
@RequestMapping("/api/v1")
class AccountController(
    private val accountService: AccountService
) {

    @Operation(summary = "토큰 생성", description = "액세스 토큰, 리프레시 토큰을 생성합니다.")
    @PostMapping("/accounts/token")
    fun authorize(@Valid @RequestBody loginDto: RequestAuthenticateDto): ResponseEntity<CommonResponse> {
        println(loginDto.username + " " + loginDto.password)
        val token = accountService.authenticate(loginDto)

        return ResponseEntity<CommonResponse>(
            CommonResponse(
                message = "success",
                data = token,
            ), HttpStatus.OK
        )
    }

    @Operation(summary = "토큰 갱신", description = "리프레시 토큰을 이용해서 토큰을 갱신합니다.")
    @PutMapping("/accounts/token")
    fun refreshToken(@Valid @RequestBody refreshTokenDto: RequestRefreshTokenDto): ResponseEntity<CommonResponse> {
        val token = accountService.refreshToken(refreshTokenDto)

        return ResponseEntity<CommonResponse>(
            CommonResponse(
                message = "success",
                data = token,
            ), HttpStatus.OK
        )
    }

    @Operation(summary = "토큰 삭제", description = "토큰을 삭제합니다. 어드민만 호출 가능합니다.")
    @DeleteMapping("/accounts/{username}/token")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun authorize(@PathVariable username: String): ResponseEntity<CommonResponse> {
        accountService.invalidateRefreshTokenByUsername(username)
        return ResponseEntity<CommonResponse>(
            CommonResponse(
                message = "success",
                data = username,
            ), HttpStatus.OK
        )
    }
}