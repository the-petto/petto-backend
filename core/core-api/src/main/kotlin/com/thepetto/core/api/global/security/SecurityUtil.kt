package com.thepetto.core.api.global.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails


object SecurityUtil {
    fun currentUsername(): String {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication

        var username: String? = null
        if (authentication.principal is UserDetails) {
            val springSecurityUser = authentication.principal as UserDetails
            username = springSecurityUser.username
        } else if (authentication.principal is String) {
            username = authentication.principal.toString()
        }
        return username ?: throw IllegalStateException()
    }
}
