package com.thepetto.core.api.member.application

import com.thepetto.core.api.member.application.dto.RequestRegisterMemberDto
import com.thepetto.core.api.member.application.dto.ResponseRegisterMemberDto

interface MemberService {
    fun signup(registerDto: RequestRegisterMemberDto): ResponseRegisterMemberDto
    fun getUserWithAuthorities(username: String): ResponseRegisterMemberDto
    fun myUserWithAuthorities(): ResponseRegisterMemberDto
}