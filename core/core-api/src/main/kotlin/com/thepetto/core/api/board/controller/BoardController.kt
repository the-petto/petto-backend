package com.thepetto.core.api.board.controller

import com.thepetto.core.api.board.dto.RequestCreateAnimalWalkBoardDto
import com.thepetto.core.api.board.dto.RequestPatchBoardStatusDto
import com.thepetto.core.api.board.service.BoardService
import com.thepetto.core.api.global.dto.CommonResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@Tag(name = "board", description = "게시판 API")
@RestController
@RequestMapping("/api/v1")
class BoardController(
    private val boardService: BoardService,
) {

    @Operation(summary = "산책해주세요 게시글 생성", description = "산책해주세요 게시글을 작성합니다.")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/boards/animal-walks", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createBoardTypeAnimalWalk(
        @ModelAttribute @Valid requestDto: RequestCreateAnimalWalkBoardDto,
    ): ResponseEntity<CommonResponse> {
        val boardId = boardService.createBoardTypeAnimalWalk(requestDto)

        return ResponseEntity<CommonResponse>(
            CommonResponse(
                message = "success",
                data = boardId,
            ), HttpStatus.CREATED
        )
    }

    @Operation(summary = "산책해주세요 게시글 조회", description = "산책해주세요 게시글을 조회합니다. 페이징을 수행합니다.")
    @GetMapping("/boards/animal-walks")
    fun getBoardTypeAnimalWalk(
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): ResponseEntity<CommonResponse> {
        val boards = boardService.getBoardTypeAnimalWalkByPageable(pageable)

        return ResponseEntity<CommonResponse>(
            CommonResponse(
                message = "success",
                data = boards,
            ), HttpStatus.OK
        )
    }

    @Operation(summary = "산책해주세요 게시글 상세 조회", description = "산책해주세요 게시글을 상세조회 합니다. 보드 식별값이 필요합니다.")
    @GetMapping("/boards/animal-walks/{boardId}")
    fun getBoardTypeAnimalWalk(
        @PathVariable boardId: Long
    ): ResponseEntity<CommonResponse> {
        val board = boardService.getBoardTypeAnimalWalk(boardId)

        return ResponseEntity<CommonResponse>(
            CommonResponse(
                message = "success",
                data = board,
            ), HttpStatus.OK
        )
    }

    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다. 본인 게시글만 삭제하거나 어드민만 호출 가능합니다.")
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
    @DeleteMapping("/boards/{boardId}")
    fun deleteBoard(
        @PathVariable boardId: Long,
        @AuthenticationPrincipal user: User,
    ): ResponseEntity<CommonResponse> {
        boardService.delete(boardId, user)

        return ResponseEntity<CommonResponse>(
            CommonResponse(
                message = "success",
            ), HttpStatus.OK
        )
    }

    @Operation(summary = "게시글 상태 변경", description = "게시글 상태를 변경합니다.")
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
    @PatchMapping("/boards/{boardId}/status")
    fun patchBoard(
        @PathVariable boardId: Long,
        @AuthenticationPrincipal user: User,
        @RequestBody @Valid requestPatchBoardStatusDto: RequestPatchBoardStatusDto,
    ): ResponseEntity<CommonResponse> {
        boardService.patchStatus(boardId, user, requestPatchBoardStatusDto)

        return ResponseEntity<CommonResponse>(
            CommonResponse(
                message = "success",
            ), HttpStatus.OK
        )
    }
}