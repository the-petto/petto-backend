package com.thepetto.core.api

import com.thepetto.core.api.account.domain.Account
import com.thepetto.core.api.account.domain.AccountAdapter
import com.thepetto.core.api.account.domain.Authority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails


object AccountFixture {
    fun createRoleMember(id: Long = 0L, username: String) = AccountAdapter(
        Account(
            id = id,
            username = username,
            password = "member",
            tokenWeight = 1L,
            nickname = "test",
            activated = true,
            authorities = mutableSetOf(Authority("ROLE_MEMBER")),
        )
    )

    fun createRoleAdmin(id: Long = 0L, username: String)= AccountAdapter(
        Account(
            id = id,
            username = username,
            password = "admin",
            tokenWeight = 1L,
            nickname = "test",
            activated = true,
            authorities = mutableSetOf(Authority("ROLE_ADMIN")),
        )
    )

    fun createRoleMemberAndAdmin(id: Long, username: String) = AccountAdapter(
        Account(
            id = id,
            username = username,
            password = "admin",
            tokenWeight = 1L,
            nickname = "test",
            activated = true,
            authorities = mutableSetOf(Authority("ROLE_ADMIN"), Authority("ROLE_MEMBER")),
        )
    )

    fun convertUserDetailsToUser(userDetails: UserDetails): User {
        val username = userDetails.username
        val password = userDetails.password

        return User(
            username, password, userDetails.authorities
        )
    }
}