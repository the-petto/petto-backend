package com.thepetto.core.api.oauth2account.application

import com.thepetto.core.api.oauth2account.application.dto.ResponseOAuth2UniqueDto
import com.thepetto.core.api.oauth2account.domain.entity.OAuth2Account
import com.thepetto.core.api.oauth2account.domain.OAuth2AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.HashMap

@Service
class OAuth2AccountServiceImpl(
    private val oAuth2AccountRepository: OAuth2AccountRepository,
) : DefaultOAuth2UserService(), OAuth2AccountService {

    /**
     * OAuth2 로그인을하고 OAuth2User 객체를 만들어준다.
     * 디비에 OAuth2Account가 존재하지 않으면 생성한다.
     */
    @Transactional
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val provider = userRequest.clientRegistration.registrationId

        // Role generate
        val authorities = AuthorityUtils.createAuthorityList("ROLE_MEMBER")

        // nameAttributeKey
        val userNameAttributeName = userRequest.clientRegistration
            .providerDetails
            .userInfoEndpoint
            .userNameAttributeName

        // 해당 식별자가 디비에 존재하지 않다면 oauth2account 생성
        // oauth2로그인 후 Account 계정이 없다면 Account 계정 회원가입 화면으로 이동하고, oauth2 식별자로 매핑해야한다.
        oAuth2AccountRepository.findByIdOrNull(provider + oAuth2User.name) ?: run {
            val oAuth2Account = OAuth2Account(
                id = provider + oAuth2User.name,
                provider = provider,
                providerId = oAuth2User.name,
                account = null,
            )
            oAuth2AccountRepository.save(oAuth2Account)
        }

        val customAttributes = HashMap(oAuth2User.attributes) // provider 정보 추가
        customAttributes["provider"] = provider
        return DefaultOAuth2User(authorities, customAttributes, userNameAttributeName)
    }

    /**
     * OAuth2 로그인에 성공하여 Authentication 객체가 만들어지면 OAuth2Account 식별자를 응답하는 서비스이다.
     */
    override fun getUniqueId(authentication: Authentication): ResponseOAuth2UniqueDto {
        val principal: OAuth2AuthenticatedPrincipal = authentication.principal as OAuth2AuthenticatedPrincipal
        val id = principal.attributes["provider"] as String + principal.name // oauth2 식별자임

        return ResponseOAuth2UniqueDto(
            oauth2AccountId = id,
        )
    }
}
