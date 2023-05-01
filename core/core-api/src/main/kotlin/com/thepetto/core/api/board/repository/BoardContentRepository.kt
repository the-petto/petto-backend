package com.thepetto.core.api.board.repository

import com.thepetto.core.api.board.domain.BoardContent
import org.springframework.data.jpa.repository.JpaRepository

interface BoardContentRepository : JpaRepository<BoardContent, Long> {
}