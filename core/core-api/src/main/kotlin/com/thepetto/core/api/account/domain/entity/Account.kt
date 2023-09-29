package com.thepetto.core.api.account.domain.entity

import com.thepetto.core.api.global.domain.BaseTimeEntity
import com.thepetto.core.api.oauth2account.domain.entity.OAuth2Account
import jakarta.persistence.*


@Entity
class Account(
    id: Long = 0L,
    username: String,
    password: String,
    tokenWeight: Long,
    nickname: String,
    activated: Boolean,
    authorities: MutableSet<Authority>,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    val id: Long = id

    @Column(name = "username", length = 50, unique = true)
    var username: String = username
        protected set

    @Column(name = "password", length = 100)
    var password: String = password
        protected set

    @Column(name = "token_weight")
    var tokenWeight: Long = tokenWeight // 리프레시 토큰 가중치, // 리프레시 토큰 안에 기입된 가중치가 tokenWeight 보다 작을경우 해당 토큰은 유효하지않음
        protected set


    @Column(name = "nickname", length = 50)
    var nickname: String = nickname
        protected set

    @Column(name = "activated")
    var activated = activated
        protected set

    @ManyToMany
    @JoinTable(
        name = "account_authority",
        joinColumns = [JoinColumn(name = "account_id", referencedColumnName = "account_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "authority_name")]
    )
    var authorities: MutableSet<Authority> = authorities
        protected set

    @OneToMany(mappedBy = "account", cascade = [CascadeType.PERSIST])
    private var _oAuth2Accounts: MutableList<OAuth2Account> = mutableListOf()

    val oAuth2Accounts: List<OAuth2Account>
        get() = _oAuth2Accounts

    fun increaseTokenWeight() {
        tokenWeight++
    }

    fun isMember(): Boolean {
        return authorities.any {
            it.authorityName == "ROLE_MEMBER"
        }
    }
}