package com.thepetto.core.api.account.repository

import com.thepetto.core.api.account.domain.Account
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository


interface AccountRepository : JpaRepository<Account, Long> {
    @EntityGraph(attributePaths = ["authorities"]) // 엔티티그래프 통해 EAGER로 가져온다.
    fun findOneWithAuthoritiesByUsername(username: String): Account? // user를 기준으로 유저를 조회할 때 권한정보도 가져온다.
}
