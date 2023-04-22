package com.thepetto.core.api.global.security.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties {
    lateinit var secret: String
    lateinit var refreshTokenSecret: String
    var accessTokenValidityInSeconds: Long = 0
    var refreshTokenValidityInSeconds: Long = 0
}