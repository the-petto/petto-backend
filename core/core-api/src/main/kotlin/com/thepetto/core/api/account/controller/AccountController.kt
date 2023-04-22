package com.thepetto.core.api.account.controller

import com.thepetto.core.api.account.dto.RequestAuthenticateDto
import com.thepetto.core.api.account.dto.RequestRefreshTokenDto
import com.thepetto.core.api.account.service.AccountService
import com.thepetto.core.api.global.dto.CommonResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1")
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping("/accounts/token")
    fun authorize(@Valid @RequestBody loginDto: RequestAuthenticateDto): ResponseEntity<CommonResponse> {
        val token = accountService.authenticate(loginDto)

        return ResponseEntity<CommonResponse>(CommonResponse(
            message = "success",
            data = token,
        ), HttpStatus.OK)
    }

    @PutMapping("/accounts/token")
    fun refreshToken(@Valid @RequestBody refreshTokenDto: RequestRefreshTokenDto): ResponseEntity<CommonResponse> {
        val token = accountService.refreshToken(refreshTokenDto)

        return ResponseEntity<CommonResponse>(CommonResponse(
            message = "success",
            data = token,
        ), HttpStatus.OK)
    }

    @DeleteMapping("/accounts/{username}/token")
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun authorize(@PathVariable username: String): ResponseEntity<CommonResponse> {
        accountService.invalidateRefreshTokenByUsername(username)
        return ResponseEntity<CommonResponse>(CommonResponse(
            message = "success",
            data = username,
        ), HttpStatus.OK)
    }
}