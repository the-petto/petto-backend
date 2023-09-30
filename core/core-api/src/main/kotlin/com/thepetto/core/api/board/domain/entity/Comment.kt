package com.thepetto.core.api.board.domain.entity

import com.thepetto.core.api.global.domain.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Comment(
    id: Long = 0L,
    board: Board,
    content: String,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id

    @ManyToOne
    @JoinColumn(name = "board_id")
    var board: Board = board
        protected set

    @Column(name = "content")
    var content: String = content
        protected set
}