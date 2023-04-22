package com.thepetto.core.api.account.service

import com.thepetto.core.api.account.domain.Account
import com.thepetto.core.api.account.domain.AccountAdapter
import com.thepetto.core.api.account.dto.RequestAuthenticateDto
import com.thepetto.core.api.account.dto.RequestRefreshTokenDto
import com.thepetto.core.api.account.dto.ResponseTokenDto
import com.thepetto.core.api.account.repository.AccountRepository
import com.thepetto.core.api.global.exception.custom.InvalidRefreshTokenException
import com.thepetto.core.api.global.security.TokenProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class AccountServiceImpl(
    @Qualifier("tokenProvider") val tokenProvider: TokenProvider,
    @Qualifier("refreshTokenProvider") val refreshTokenProvider: TokenProvider,
    val authenticationManagerBuilder : AuthenticationManagerBuilder,
    val accountRepository: AccountRepository,
) : AccountService {

    override fun authenticate(requestAuthenticateDto: RequestAuthenticateDto): ResponseTokenDto {
        // 받아온 유저네임과 패스워드를 이용해 UsernamePasswordAuthenticationToken 객체 생성
        val authenticationToken =
            UsernamePasswordAuthenticationToken(requestAuthenticateDto.username, requestAuthenticateDto.password)

        val authentication: Authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)

        // 그 객체를 시큐리티 컨텍스트에 저장
        SecurityContextHolder.getContext().authentication = authentication

        val tokenWeight: Long = (authentication.principal as AccountAdapter).account.tokenWeight
        // 인증 정보를 기준으로 jwt access 토큰 생성
        val accessToken: String = tokenProvider.createToken(authentication, tokenWeight)
        val refreshToken: String = refreshTokenProvider.createToken(authentication, tokenWeight)

        return ResponseTokenDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    @Transactional(readOnly = true)
    override fun refreshToken(requestRefreshTokenDto: RequestRefreshTokenDto): ResponseTokenDto {

        if (!refreshTokenProvider.validateToken(requestRefreshTokenDto.refreshToken)) {
                throw InvalidRefreshTokenException()
        }

        val authentication = refreshTokenProvider.getAuthentication(requestRefreshTokenDto.refreshToken)
        val account: Account = accountRepository.findOneWithAuthoritiesByUsername(authentication.name)
            ?: throw UsernameNotFoundException(authentication.name + "을 찾을 수 없습니다")

        if (account.tokenWeight > refreshTokenProvider.getTokenWeight(requestRefreshTokenDto.refreshToken))
            throw InvalidRefreshTokenException()


        val accessToken = tokenProvider.createToken(authentication, account.tokenWeight)
        val refreshToken = refreshTokenProvider.createToken(authentication, account.tokenWeight)

        return ResponseTokenDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    @Transactional
    override fun invalidateRefreshTokenByUsername(username: String) {
        val account: Account = accountRepository.findOneWithAuthoritiesByUsername(username)
            ?: throw UsernameNotFoundException("$username 을 찾을 수 없습니다")
        account.increaseTokenWeight() // 더티체킹에 의해 엔티티 변화 반영
    }
}