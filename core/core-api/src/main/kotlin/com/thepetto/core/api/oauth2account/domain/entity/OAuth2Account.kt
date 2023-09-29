package com.thepetto.core.api.oauth2account.domain.entity

import com.thepetto.core.api.account.domain.entity.Account
import com.thepetto.core.api.global.domain.BaseTimeEntity
import jakarta.persistence.*

@Entity(name = "oauth2_account")
class OAuth2Account(
    id: String,
    providerId: String,
    provider: String,
    account: Account?,
) : BaseTimeEntity() {

    @Id
    @Column(name = "id")
    val id: String = id

    @Column(name = "provider_id")
    val providerId: String = providerId // 프로바이더에서 제공하는 식별값

    @Column(name = "provider")
    var provider: String = provider

    @ManyToOne
    @JoinColumn(name = "account_id")
    var account: Account? = account
        protected set
}