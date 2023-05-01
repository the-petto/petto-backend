package com.thepetto.core.api.board.controller

import com.thepetto.core.api.board.dto.RequestCreateAnimalWalkBoardDto
import com.thepetto.core.api.board.service.BoardService
import com.thepetto.core.api.global.dto.CommonResponse
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class BoardController(
    private val boardService: BoardService,
) {

    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/boards/animal-walks")
    fun createBoardTypeAnimalWalk(
        @Valid @RequestBody requestDto: RequestCreateAnimalWalkBoardDto
    ): ResponseEntity<CommonResponse> {
        val boardId = boardService.createBoardTypeAnimalWalk(requestDto)

        return ResponseEntity<CommonResponse>(
            CommonResponse(
            message = "success",
            data = boardId,
        ), HttpStatus.CREATED)
    }

    @GetMapping("/boards/animal-walks")
    fun getBoardTypeAnimalWalk(
        pageable: Pageable
    ): ResponseEntity<CommonResponse> {
        val boards = boardService.getBoardTypeAnimalWalkByPageable(pageable)

        return ResponseEntity<CommonResponse>(
            CommonResponse(
                message = "success",
                data = boards,
            ), HttpStatus.OK)
    }

    @GetMapping("/boards/animal-walks/{boardId}")
    fun getBoardTypeAnimalWalk(
        @PathVariable boardId: Long
    ): ResponseEntity<CommonResponse> {
        val board = boardService.getBoardTypeAnimalWalk(boardId)

        return ResponseEntity<CommonResponse>(
            CommonResponse(
                message = "success",
                data = board,
            ), HttpStatus.OK)
    }
}