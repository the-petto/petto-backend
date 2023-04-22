package com.thepetto.core.api.global.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails


object SecurityUtil {
    private val logger: Logger = LoggerFactory.getLogger(SecurityUtil::class.java)

    fun currentUsername(): String? {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
            ?: null
        if (authentication == null) {
            logger.debug("Security Context에 인증 정보가 없습니다.")
            return null
        }

        var username: String? = null
        if (authentication.principal is UserDetails) {
            val springSecurityUser = authentication.principal as UserDetails
            username = springSecurityUser.username
        } else if (authentication.principal is String) {
            username = authentication.principal.toString()
        }
        return username
    }
}
