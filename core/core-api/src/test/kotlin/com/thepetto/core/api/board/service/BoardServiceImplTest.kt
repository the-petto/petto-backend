package com.thepetto.core.api.board.service

import com.thepetto.core.api.account.domain.Account
import com.thepetto.core.api.account.domain.Authority
import com.thepetto.core.api.account.repository.AccountRepository
import com.thepetto.core.api.board.domain.Board
import com.thepetto.core.api.board.domain.BoardCategory
import com.thepetto.core.api.board.domain.BoardContent
import com.thepetto.core.api.board.dto.RequestCreateAnimalWalkBoardDto
import com.thepetto.core.api.board.repository.BoardContentRepository
import com.thepetto.core.api.board.repository.BoardRepository
import com.thepetto.core.api.global.security.SecurityUtil
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class BoardServiceImplTest : BehaviorSpec({
    val boardRepository = mockk<BoardRepository>()
    val accountRepository = mockk<AccountRepository>()
    val boardContentRepository = mockk<BoardContentRepository>()
    val securityUtil = mockk<SecurityUtil>()

    val boardService = BoardServiceImpl(
            boardRepository = boardRepository,
            accountRepository = accountRepository,
            boardContentRepository = boardContentRepository,
            securityUtil = securityUtil,
    )

    Given("타이틀과 제목이 주어진다면") {
        val requestCreateAnimalWalkBoardDto = RequestCreateAnimalWalkBoardDto(
                title = "산책시켜주세요",
                content = "귀여운 시츄입니다.",
        )
        val account = Account(
                username = "hello",
                password = "hello",
                tokenWeight = 1L,
                nickname = "hello",
                activated = true,
                mutableSetOf(Authority("MEMBER")),
        )
        val boardContent = BoardContent(
                content = requestCreateAnimalWalkBoardDto.content,
        )
        val boardCategory = BoardCategory.ANIMAL_WALK

        every { boardContentRepository.save(any()) } returns boardContent

        every { boardRepository.save(any()) } returns Board(
                account = account,
                boardContent = boardContent,
                category = boardCategory,
                title = requestCreateAnimalWalkBoardDto.title,
        )

        every { securityUtil.currentUsername() } returns "hello"
        every { accountRepository.findOneWithAuthoritiesByUsername(any()) } returns account


        When("게시글을 작성했을 때") {
            val id = boardService.createBoardTypeAnimalWalk(requestCreateAnimalWalkBoardDto)
            then("해당 게시글의 고유값을 리턴한다.") {
                id shouldBe 0L
            }
        }
    }
})