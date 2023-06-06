package com.thepetto.core.api.board.controller

import com.thepetto.core.api.board.dto.RequestCreateAnimalWalkBoardDto
import com.thepetto.core.api.board.service.BoardService
import com.thepetto.core.api.global.dto.CommonResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class BoardController(
    private val boardService: BoardService,
) {

    @PreAuthorize("hasAnyRole('MEMBER')")
    @SecurityRequirement(name = "bearer-key")
    @PostMapping("/boards/animal-walks", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createBoardTypeAnimalWalk(
        @ModelAttribute @Valid requestDto: RequestCreateAnimalWalkBoardDto,
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
        @PageableDefault(sort = ["boardId"]) pageable: Pageable
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