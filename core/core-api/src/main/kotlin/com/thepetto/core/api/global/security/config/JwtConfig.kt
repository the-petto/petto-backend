package com.thepetto.core.api.global.security.config

import com.thepetto.core.api.global.security.TokenProvider
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class JwtConfig {

    // 액세스 토큰 발급용, 리프레시 토큰 발급용은 각각 별도의 키와 유효기간을 갖는다.
    @Bean("tokenProvider")
    fun tokenProvider(jwtProperties: JwtProperties): TokenProvider {
        return TokenProvider(jwtProperties.secret, jwtProperties.accessTokenValidityInSeconds)
    }

    // 리프레시 토큰은 별도의 키를 가지기 떄문에 리프레시 토큰으로는 API 호출 불가
    // 액세스 토큰 재발급 시 검증용
    @Bean("refreshTokenProvider")
    fun refreshTokenProvider(jwtProperties: JwtProperties): TokenProvider {
        return TokenProvider(jwtProperties.refreshTokenSecret, jwtProperties.refreshTokenValidityInSeconds)
    }
}