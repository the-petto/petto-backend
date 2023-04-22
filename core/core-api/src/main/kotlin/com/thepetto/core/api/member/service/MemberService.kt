package com.thepetto.core.api.member.service

import com.thepetto.core.api.member.dto.RequestRegisterMemberDto
import com.thepetto.core.api.member.dto.ResponseRegisterMemberDto

interface MemberService {
    fun signup(registerDto: RequestRegisterMemberDto): ResponseRegisterMemberDto
    fun getUserWithAuthorities(username: String): ResponseRegisterMemberDto
    fun myUserWithAuthorities(): ResponseRegisterMemberDto
}