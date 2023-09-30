package com.thepetto.core.api.member.presentation

import com.thepetto.core.api.global.dto.CommonResponse
import com.thepetto.core.api.member.application.dto.RequestRegisterMemberDto
import com.thepetto.core.api.member.application.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "멤버", description = "멤버 API")
@RestController
@RequestMapping("/api/v1")
class MemberController(
    private val memberService: MemberService
) {

    @Operation(summary = "멤버 생성", description = "멤버 권한을 가진 사용자를 생성합니다.")
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