package com.thepetto.core.api.member.facade

import com.thepetto.core.api.member.facade.dto.RequestRegisterMemberDto
import com.thepetto.core.api.member.facade.dto.ResponseRegisterMemberDto

interface MemberFacade {

    fun signup(registerDto: RequestRegisterMemberDto): ResponseRegisterMemberDto
}