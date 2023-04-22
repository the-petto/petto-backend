package com.thepetto.core.api.member.service

import com.thepetto.core.api.account.domain.Account
import com.thepetto.core.api.account.domain.Authority
import com.thepetto.core.api.account.repository.AccountRepository
import com.thepetto.core.api.global.exception.custom.DuplicateMemberException
import com.thepetto.core.api.global.exception.custom.NotFoundAccountException
import com.thepetto.core.api.member.dto.RequestRegisterMemberDto
import com.thepetto.core.api.member.dto.ResponseRegisterMemberDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class MemberServiceImpl(
    val accountRepository: AccountRepository,
    val passwordEncoder: PasswordEncoder,
) : MemberService {

    @Transactional
    override fun signup(registerDto: RequestRegisterMemberDto): ResponseRegisterMemberDto {
        accountRepository.findOneWithAuthoritiesByUsername(registerDto.username)?.let {
            throw DuplicateMemberException()
        }

        val authority = Authority(authorityName = "ROLE_MEMBER")

        val user = Account(
            username = registerDto.username,
            password = passwordEncoder.encode(
                registerDto.password
            ),
            nickname = registerDto.nickname,
            authorities = Collections.singleton(authority),
            activated = true,
            tokenWeight = 1
        )

        return ResponseRegisterMemberDto.of(
            accountRepository.save(user)
        )
    }

    @Transactional(readOnly = true)
    override fun getUserWithAuthorities(username: String): ResponseRegisterMemberDto {
        val user = accountRepository.findOneWithAuthoritiesByUsername(username)
            ?: throw NotFoundAccountException()

        return ResponseRegisterMemberDto.of(user)
    }

    override fun myUserWithAuthorities(): ResponseRegisterMemberDto {
        TODO("Not yet implemented")
    }
}