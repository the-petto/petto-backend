package com.thepetto.core.api.member.facade

import com.thepetto.core.api.account.application.AccountService
import com.thepetto.core.api.account.application.dto.RequestRegisterMemberAccountDto
import com.thepetto.core.api.member.facade.dto.RequestRegisterMemberDto
import com.thepetto.core.api.member.facade.dto.ResponseRegisterMemberDto
import org.springframework.stereotype.Service

@Service
class MemberFacadeImpl(
    private val accountService: AccountService,
) : MemberFacade {

    override fun signup(registerDto: RequestRegisterMemberDto): ResponseRegisterMemberDto {
        val response = accountService.registerMemberAccount(RequestRegisterMemberAccountDto(
            username = registerDto.username,
            password = registerDto.password,
            nickname = registerDto.nickname
        ))

        return ResponseRegisterMemberDto(
            username = response.username,
            nickname = response.nickname,
            tokenWeight = response.tokenWeight,
            authoritySet = response.authoritySet,
        )
    }
}