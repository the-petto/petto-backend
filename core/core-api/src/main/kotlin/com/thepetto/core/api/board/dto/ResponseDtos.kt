package com.thepetto.core.api.board.dto

import com.thepetto.core.api.board.domain.Board
import com.thepetto.core.api.board.domain.BoardCategory

data class ResponseBoardListTypeAnimalWalkDto(
    val boardId: Long,
    val username: String,
    val nickname: String,
    val boardCategory: BoardCategory,
    val title: String,
) {
    companion object {
        fun of(board: Board): ResponseBoardListTypeAnimalWalkDto {
            return ResponseBoardListTypeAnimalWalkDto(
                boardId = board.id,
                username = board.account.username,
                nickname = board.account.nickname,
                boardCategory = board.category,
                title = board.title,
            )
        }
    }
}

data class ResponseBoardTypeAnimalWalkDto(
    val boardId: Long,
    val username: String,
    val nickname: String,
    val boardCategory: BoardCategory,
    val title: String,
    val content: String,
) {
    companion object {
        fun of(board: Board): ResponseBoardTypeAnimalWalkDto {
            return ResponseBoardTypeAnimalWalkDto(
                boardId = board.id,
                username = board.account.username,
                nickname = board.account.nickname,
                boardCategory = board.category,
                title = board.title,
                content = board.boardContent.content,
            )
        }
    }
}