package com.thepetto.core.api.account.domain

import com.thepetto.core.api.account.domain.entity.Account
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface AccountRepository : JpaRepository<Account, Long> {

    @EntityGraph(attributePaths = ["authorities"]) // 엔티티그래프 통해 EAGER로 가져온다.
    fun findOneWithAuthoritiesByUsername(username: String): Account? // user를 기준으로 유저를 조회할 때 권한정보도 가져온다.

    @EntityGraph(attributePaths = ["authorities"]) // 엔티티그래프 통해 EAGER로 가져온다.
    @Query("select a from Account a join a._oAuth2Accounts oa where oa.id = :oAuth2UniqueId")
    fun findOneWithAuthoritiesByOAuth2UniqueId(@Param("oAuth2UniqueId") oAuth2UniqueId: String): Account?
}
