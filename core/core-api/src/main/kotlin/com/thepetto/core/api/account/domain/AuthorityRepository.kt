package com.thepetto.core.api.account.domain

import com.thepetto.core.api.account.domain.entity.Authority
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository : JpaRepository<Authority, String>