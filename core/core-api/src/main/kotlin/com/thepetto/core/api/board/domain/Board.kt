package com.thepetto.core.api.board.domain

import com.thepetto.core.api.account.domain.Account
import com.thepetto.core.api.global.domain.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Board(
    id: Long = 0L,
    account: Account,
    category: BoardCategory,
    title: String,
    boardContent: BoardContent,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = id

    @OneToOne
    @JoinColumn(name = "account_id")
    var account: Account = account
        protected set

    @OneToOne
    @JoinColumn(name = "board_content_id")
    var boardContent: BoardContent = boardContent
        protected set

    @OneToMany(mappedBy = "board", cascade = [CascadeType.PERSIST])
    private var _images: MutableList<BoardImage> = mutableListOf()

    val images: List<BoardImage>
        get() = _images

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    var category: BoardCategory = category
        protected set

    @Column(name = "title")
    var title: String = title
        protected set

    fun addImage(boardImage: BoardImage) {
        _images.add(boardImage)
        boardImage.updateBoard(this)
    }
}