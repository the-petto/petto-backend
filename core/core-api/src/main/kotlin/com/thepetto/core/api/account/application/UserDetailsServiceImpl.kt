package com.thepetto.core.api.account.application

import com.thepetto.core.api.account.domain.entity.AccountAdapter
import com.thepetto.core.api.account.domain.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserDetailsServiceImpl(
    private val accountRepository: AccountRepository
) : UserDetailsService {

    // "인증 API 호출 시에만" 그 과정에서 재정의한 loadUserByUsername를 호출하여 디비에서 유저정보와 권한정보를 가져온다.
    // 내가 만든 커스텀 UserAdapter 는 org.springframework.security.core.userdetails.User 를 상속받았으므로 이걸 반환해도 된다.
    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val account = accountRepository.findOneWithAuthoritiesByUsername(username)
            ?: throw UsernameNotFoundException("$username -> 데이터베이스에서 찾을 수 없습니다.")

        if (!account.activated) throw RuntimeException(account.username + " -> 활성화되어 있지 않습니다.")

        return AccountAdapter(account = account)
    }
}