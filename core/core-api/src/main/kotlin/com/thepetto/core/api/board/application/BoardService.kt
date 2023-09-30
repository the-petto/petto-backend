package com.thepetto.core.api.board.application

import com.thepetto.core.api.board.application.dto.RequestCreateAnimalWalkBoardDto
import com.thepetto.core.api.board.application.dto.RequestPatchBoardStatusDto
import com.thepetto.core.api.board.application.dto.ResponseBoardListTypeAnimalWalkDto
import com.thepetto.core.api.board.application.dto.ResponseBoardTypeAnimalWalkDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.User

interface BoardService {
    fun createBoardTypeAnimalWalk(requestCreateAnimalWalkBoardDto: RequestCreateAnimalWalkBoardDto): Long

    fun getBoardTypeAnimalWalkByPageable(pageable: Pageable): Page<ResponseBoardListTypeAnimalWalkDto>
    fun getBoardTypeAnimalWalk(boardId: Long): ResponseBoardTypeAnimalWalkDto
    fun delete(boardId: Long, user: User): Boolean
    fun patchStatus(boardId: Long, user: User, requestPatchBoardStatusDto: RequestPatchBoardStatusDto)
}