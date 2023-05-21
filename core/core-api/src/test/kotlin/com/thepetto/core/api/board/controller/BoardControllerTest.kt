package com.thepetto.core.api.board.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.thepetto.core.api.board.dto.RequestCreateAnimalWalkBoardDto
import com.thepetto.core.api.board.service.BoardService
import com.thepetto.core.api.global.security.CustomJwtFilter
import io.kotest.core.spec.style.BehaviorSpec
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BoardController::class)
@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc(addFilters = false) // security 무시
@MockBean(JpaMetamodelMappingContext::class)
class BoardControllerTest(
        @Autowired val mockMvc: MockMvc,
        @Autowired val objectMapper: ObjectMapper,
        @MockBean val boardService: BoardService,
        @MockBean val customJwtFilter: CustomJwtFilter,
) : BehaviorSpec({
    Given("타이틀과 제목이 주어졌을 때") {
        val requestDto = RequestCreateAnimalWalkBoardDto(
                title = "시츄 산책시켜주세요",
                content = "귀여운 시츄랍니다."
        )

        When("로그인한 상태에서 작성을 시도한다면") {
            val resultActions: ResultActions = mockMvc.perform(post("/api/v1/boards/animal-walks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(requestDto))
            )


            Then("게시글을 작성할 수 있다") {
                resultActions.andExpect(status().isCreated)
            }
        }
    }
})