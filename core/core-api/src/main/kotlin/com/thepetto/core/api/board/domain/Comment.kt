package com.thepetto.core.api.board.domain

import com.thepetto.core.api.global.domain.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Comment(
    board: Board,
    content: String,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @ManyToOne
    @JoinColumn(name = "board_id")
    var board: Board = board

    @Column(name = "content")
    var content: String = content
        protected set
}