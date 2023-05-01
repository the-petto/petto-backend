package com.thepetto.core.api.board.service

import com.thepetto.core.api.board.dto.RequestCreateAnimalWalkBoardDto
import com.thepetto.core.api.board.dto.ResponseBoardListTypeAnimalWalkDto
import com.thepetto.core.api.board.dto.ResponseBoardTypeAnimalWalkDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BoardService {
    fun createBoardTypeAnimalWalk(requestCreateAnimalWalkBoardDto: RequestCreateAnimalWalkBoardDto): Long
    fun getBoardTypeAnimalWalkByPageable(pageable: Pageable): Page<ResponseBoardListTypeAnimalWalkDto>
    fun getBoardTypeAnimalWalk(boardId: Long): ResponseBoardTypeAnimalWalkDto
}