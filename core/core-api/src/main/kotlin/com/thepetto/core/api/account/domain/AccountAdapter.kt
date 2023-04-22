package com.thepetto.core.api.account.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class AccountAdapter(
    val account: Account
) : UserDetails {

    private fun authorities(authorities: Set<Authority>): MutableList<GrantedAuthority> {
        return authorities.map { authority: Authority ->
            SimpleGrantedAuthority(authority.authorityName)
        }.toMutableList()
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? = authorities(account.authorities)


    override fun getPassword(): String = account.password

    override fun getUsername(): String = account.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = account.activated
}