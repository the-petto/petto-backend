package com.thepetto.core.api.board.dto

import com.thepetto.core.api.board.domain.Board
import com.thepetto.core.api.board.domain.BoardCategory
import com.thepetto.core.api.board.domain.BoardStatus

data class ResponseBoardListTypeAnimalWalkDto(
    val boardId: Long,
    val username: String,
    val nickname: String,
    val boardCategory: BoardCategory,
    val boardStatus: BoardStatus,
    val title: String,
    val images: List<String>,
) {
    companion object {
        fun of(board: Board): ResponseBoardListTypeAnimalWalkDto {
            val images = board.images.map { it.url }.toList()

            return ResponseBoardListTypeAnimalWalkDto(
                boardId = board.id,
                username = board.account.username,
                nickname = board.account.nickname,
                boardCategory = board.category,
                boardStatus = board.boardStatus,
                title = board.title,
                images = images,
            )
        }
    }
}

data class ResponseBoardTypeAnimalWalkDto(
    val boardId: Long,
    val username: String,
    val nickname: String,
    val boardCategory: BoardCategory,
    val boardStatus: BoardStatus,
    val title: String,
    val content: String,
    val images: List<String>,
) {
    companion object {
        fun of(board: Board): ResponseBoardTypeAnimalWalkDto {
            val images = board.images.map { it.url }.toList()

            return ResponseBoardTypeAnimalWalkDto(
                boardId = board.id,
                username = board.account.username,
                nickname = board.account.nickname,
                boardCategory = board.category,
                boardStatus = board.boardStatus,
                title = board.title,
                content = board.boardContent.content,
                images = images,
            )
        }
    }
}