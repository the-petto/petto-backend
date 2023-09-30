package com.thepetto.core.api.board.domain

import com.thepetto.core.api.board.domain.entity.BoardContent
import org.springframework.data.jpa.repository.JpaRepository

interface BoardContentRepository : JpaRepository<BoardContent, Long> {
}