package com.thepetto.core.api.board.domain

import com.thepetto.core.api.global.domain.BaseTimeEntity
import jakarta.persistence.*

@Entity
class BoardImage(
    board: Board,
    url: String,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne
    @JoinColumn(name = "board_id")
    var board: Board = board
        protected set

    @Column(name = "url")
    var url: String = url
        protected set

    fun updateBoard(board: Board) {
        this.board = board
    }
}