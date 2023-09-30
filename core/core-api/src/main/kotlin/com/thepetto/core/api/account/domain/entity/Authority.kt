package com.thepetto.core.api.account.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Authority(
    authorityName: String
) {
    @Id
    @Column(name = "authority_name", length = 50)
    val authorityName: String = authorityName
}