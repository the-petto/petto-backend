package com.thepetto.core.api.oauth2account.domain

import com.thepetto.core.api.oauth2account.domain.entity.OAuth2Account
import org.springframework.data.jpa.repository.JpaRepository

interface OAuth2AccountRepository : JpaRepository<OAuth2Account, String> {
}