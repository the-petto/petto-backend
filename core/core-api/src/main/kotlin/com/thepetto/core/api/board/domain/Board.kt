package com.thepetto.core.api.board.domain

import com.thepetto.core.api.account.domain.Account
import com.thepetto.core.api.global.domain.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Board(
    account: Account,
    category: BoardCategory,
    title: String,
    boardContent: BoardContent,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @OneToOne
    @JoinColumn(name = "account_id")
    var account: Account = account

    @OneToOne
    @JoinColumn(name = "board_content_id")
    var boardContent: BoardContent = boardContent

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    var category: BoardCategory = category
        protected set

    @Column(name = "title")
    var title: String = title
        protected set
}