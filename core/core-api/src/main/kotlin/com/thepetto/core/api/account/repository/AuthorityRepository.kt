package com.thepetto.core.api.account.repository

import com.thepetto.core.api.account.domain.Authority
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository : JpaRepository<Authority, String>