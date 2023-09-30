package com.thepetto.core.api.board.domain.entity

import com.thepetto.core.api.global.domain.BaseTimeEntity
import jakarta.persistence.*

@Entity
class BoardContent(
    id: Long = 0L,
    content: String,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id

    @Column(name = "content")
    var content: String = content
        protected set
}