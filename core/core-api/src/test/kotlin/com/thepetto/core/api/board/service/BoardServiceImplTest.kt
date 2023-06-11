package com.thepetto.core.api.board.service

import com.thepetto.core.api.AccountFixture
import com.thepetto.core.api.account.repository.AccountRepository
import com.thepetto.core.api.board.domain.Board
import com.thepetto.core.api.board.domain.BoardCategory
import com.thepetto.core.api.board.domain.BoardContent
import com.thepetto.core.api.board.dto.RequestCreateAnimalWalkBoardDto
import com.thepetto.core.api.board.repository.BoardContentRepository
import com.thepetto.core.api.board.repository.BoardRepository
import com.thepetto.core.api.createNormalImageFixture
import com.thepetto.core.api.global.security.SecurityUtil
import com.thepetto.core.api.global.util.S3Uploader
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull

class BoardServiceImplTest : BehaviorSpec({
    val boardRepository = mockk<BoardRepository>()
    val accountRepository = mockk<AccountRepository>()
    val boardContentRepository = mockk<BoardContentRepository>()
    val securityUtil = mockk<SecurityUtil>()
    val s3Uploader = mockk<S3Uploader>()

    val boardService = BoardServiceImpl(
        boardRepository = boardRepository,
        accountRepository = accountRepository,
        boardContentRepository = boardContentRepository,
        securityUtil = securityUtil,
        s3Uploader = s3Uploader,
    )

    Given("타이틀과 제목이 주어졌을 때") {
        val requestCreateAnimalWalkBoardDto = RequestCreateAnimalWalkBoardDto(
            title = "산책시켜주세요",
            content = "귀여운 시츄입니다.",
            images = listOf(createNormalImageFixture()),
        )
        val member = AccountFixture.createRoleMember(username = "member")

        val boardContent = BoardContent(
            content = requestCreateAnimalWalkBoardDto.content,
        )
        val boardCategory = BoardCategory.ANIMAL_WALK

        every { boardContentRepository.save(any()) } returns boardContent

        every { boardRepository.save(any()) } returns Board(
            account = member.account,
            boardContent = boardContent,
            category = boardCategory,
            title = requestCreateAnimalWalkBoardDto.title,
        )

        every { securityUtil.currentUsername() } returns "hello"
        every { accountRepository.findOneWithAuthoritiesByUsername(any()) } returns member.account

        every { s3Uploader.upload(any(), any()) } returns "http://ok"

        When("게시글을 작성한다면") {
            val id = boardService.createBoardTypeAnimalWalk(requestCreateAnimalWalkBoardDto)
            then("해당 게시글의 고유값을 리턴한다.") {
                id shouldBe 0L
            }
        }
    }

    Given("멤버는 삭제할 게시글 식별자가 주어졌을 때") {
        val member = AccountFixture.createRoleMember(username = "member")
        val anotherMember = AccountFixture.createRoleMember(id = -1L, username = "member2")
        val memberUser = AccountFixture.convertUserDetailsToUser(member)
        val targetBoardId = 1L

        When("본인의 게시글을 삭제하는 것이라면") {
            every { accountRepository.findOneWithAuthoritiesByUsername(any()) } returns member.account
            every { boardRepository.findByIdOrNull(any()) } returns Board(
                account = member.account,
                boardContent = BoardContent(content = "test"),
                category = BoardCategory.ANIMAL_WALK,
                title = "test",
            )
            every { boardRepository.delete(any()) } returns Unit

            Then("삭제에 성공한다") {
                boardService.delete(targetBoardId, memberUser) shouldBe true
            }
        }

        When("남이 작성한 게시글을 삭제하는 것이라면") {
            every { accountRepository.findOneWithAuthoritiesByUsername(any()) } returns member.account
            every { boardRepository.findByIdOrNull(any()) } returns Board(
                account = anotherMember.account,
                boardContent = BoardContent(content = "test"),
                category = BoardCategory.ANIMAL_WALK,
                title = "test",
            )


            Then("예외가 발생한다") {
                shouldThrow<org.springframework.security.access.AccessDeniedException> {
                    boardService.delete(targetBoardId, memberUser)
                }
            }
        }
    }

    Given("어드민은 삭제할 게시글 식별자가 주어졌을 때") {
        val admin = AccountFixture.createRoleAdmin(username = "admin")
        val anotherMember = AccountFixture.createRoleMember(id = -1L, username = "member2")
        val adminUser = AccountFixture.convertUserDetailsToUser(admin)
        val targetBoardId = 1L

        When("남이 작성한 게시글을 삭제하는 것이라면") {
            every { accountRepository.findOneWithAuthoritiesByUsername(any()) } returns admin.account
            every { boardRepository.findByIdOrNull(any()) } returns Board(
                account = anotherMember.account,
                boardContent = BoardContent(content = "test"),
                category = BoardCategory.ANIMAL_WALK,
                title = "test",
            )

            Then("삭제에 성공한다") {
                boardService.delete(targetBoardId, adminUser) shouldBe true
            }
        }
    }
})