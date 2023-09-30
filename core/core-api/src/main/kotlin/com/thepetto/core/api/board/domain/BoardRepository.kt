package com.thepetto.core.api.board.domain

import com.thepetto.core.api.board.domain.entity.Board
import com.thepetto.core.api.board.domain.entity.BoardCategory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {
    fun findByCategory(pageable: Pageable, category: BoardCategory): Page<Board>
}