package com.thepetto.core.api.member.controller

import com.thepetto.core.api.global.dto.CommonResponse
import com.thepetto.core.api.member.dto.RequestRegisterMemberDto
import com.thepetto.core.api.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1")
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/members")
    fun signup(
        @Valid @RequestBody registerDto: RequestRegisterMemberDto
    ): ResponseEntity<CommonResponse> {
        val userInfo = memberService.signup(registerDto)

        return ResponseEntity<CommonResponse>(CommonResponse(
            message = "success",
            data = userInfo,
        ), HttpStatus.OK)
    }
}