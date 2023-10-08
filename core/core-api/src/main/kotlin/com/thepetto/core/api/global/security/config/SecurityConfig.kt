package com.thepetto.core.api.global.security.config

import com.thepetto.core.api.global.security.CustomJwtFilter
import com.thepetto.core.api.global.security.handler.JwtAccessDeniedHandler
import com.thepetto.core.api.global.security.handler.JwtAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter


// 추가적인 시큐리티 설정을 위해 WebSecurityConfigurer를 implements 하거나 WebSecurityConfigurerAdapter를 extends하는 방법이 있는데
// 여기서는 WebSecurityConfigurerAdapter를 extends 하는 방법 사용
@Configuration
@EnableWebSecurity // 기본적인 웹보안을 활성화하겠다
@EnableMethodSecurity // @PreAuthorize 어노테이션 사용을 위해 선언
class SecurityConfig(
    private val corsFilter: CorsFilter,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity, customJwtFilter: CustomJwtFilter): SecurityFilterChain {
        httpSecurity // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
            .csrf()
                .disable()

            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)

            .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 우리가 만든 클래스로 인증 실패 핸들링
                .accessDeniedHandler(jwtAccessDeniedHandler) // 커스텀 인가 실패 핸들링

            // enable h2-console // embedded h2를 위한 설정
            .and()
                .headers()
                .frameOptions()
                .sameOrigin()

            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않기 때문에 STATELESS로 설정
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/h2/**").permitAll()
            .requestMatchers("/favicon.ico").permitAll()
            .requestMatchers("/error").permitAll()

            .and()
            .authorizeHttpRequests()
                .requestMatchers("/docs/**").permitAll() // rest docs 문서 접근 권한을 설정합니다.
                .requestMatchers("/test").permitAll()
                .requestMatchers("/api/v1/hello").permitAll()
                .requestMatchers("/api/v1/accounts/token").permitAll() // 로그인 경로, 토큰 갱신 API 도 인증 없이 호출
                .requestMatchers("/api/v1/members").permitAll() // 회원가입 경로는 인증없이 호출 가능
                .requestMatchers(HttpMethod.GET, "/api/v1/boards/**").permitAll() // 게시글조회
            .anyRequest().authenticated() // 나머지 경로는 jwt 인증 해야함

            .and()
            .addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter::class.java) // 필터추가

        return httpSecurity.build()
    }

}