package com.thepetto.core.api.global.security

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import java.security.Key
import java.util.*
import java.util.stream.Collectors


// 토큰 생성, 검증
class TokenProvider(
    secret: String,
    tokenValidityInSeconds: Long,
) {
    private var tokenValidityInMilliseconds: Long
    private val logger: Logger = LoggerFactory.getLogger(TokenProvider::class.java)
    private val key: Key

    init {
        tokenValidityInMilliseconds = tokenValidityInSeconds * 1000

        //시크릿 값을 decode해서 키 변수에 할당
        val keyBytes = Decoders.BASE64.decode(secret)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    // 토큰 생성
    fun createToken(authentication: Authentication, tokenWeight: Long): String {
        val authorities: String = authentication.getAuthorities().stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))
        val now: Long = Date().getTime()
        val validity = Date(now + tokenValidityInMilliseconds)
        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .claim(WEIGHT_KEY, tokenWeight)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    // 토큰을 받아 클레임을 만들고 권한정보를 빼서 시큐리티 유저객체를 만들어 Authentication 객체 반환
    fun getAuthentication(token: String?): Authentication {
        val claims: Claims = Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
        val authorities: Collection<GrantedAuthority> =
            Arrays.stream(claims[AUTHORITIES_KEY].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray())
                .map { role: String? -> SimpleGrantedAuthority(role) }
                .collect(Collectors.toList())

        // 디비를 거치지 않고 토큰에서 값을 꺼내 바로 시큐리티 유저 객체를 만들어 Authentication을 만들어 반환하기에 유저네임, 권한 외 정보는 알 수 없다.
        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    // 토큰 유효성 검사
    fun validateToken(token: String?): Boolean {
        try {
            val claims: Claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
            return true
        } catch (e: SecurityException) {
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: MalformedJwtException) {
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            logger.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            logger.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            logger.info("JWT 토큰이 잘못되었습니다.")
        }
        return false
    }

    fun getTokenWeight(token: String?): Long {
        // 토큰에서 가중치를 꺼내 반환한다.
        val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        return java.lang.Long.valueOf(claims[WEIGHT_KEY].toString())
    }

    companion object {
        const val AUTHORITIES_KEY = "auth"
        const val WEIGHT_KEY = "token-weight"
    }
}