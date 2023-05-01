package com.thepetto.core.api.board.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RequestCreateAnimalWalkBoardDto(
    @field:NotNull
    val title: String,

    @field:NotNull
    val content: String,
)