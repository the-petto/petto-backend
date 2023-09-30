package com.thepetto.core.api.board.application.dto

import com.thepetto.core.api.board.domain.entity.BoardStatus
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

data class RequestCreateAnimalWalkBoardDto(
    @field:NotNull
    val title: String,

    @field:NotNull
    val content: String,

    @Schema(title = "멀티파트 image File Array", description = "멀티 파트 image File Array")
    val images: List<MultipartFile>
)

data class RequestPatchBoardStatusDto(
    @field:NotNull
    val boardStatus: BoardStatus,
)