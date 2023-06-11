package com.thepetto.core.api.board.service

import com.thepetto.core.api.account.repository.AccountRepository
import com.thepetto.core.api.board.domain.Board
import com.thepetto.core.api.board.domain.BoardCategory
import com.thepetto.core.api.board.domain.BoardContent
import com.thepetto.core.api.board.domain.BoardImage
import com.thepetto.core.api.board.dto.RequestCreateAnimalWalkBoardDto
import com.thepetto.core.api.board.dto.ResponseBoardListTypeAnimalWalkDto
import com.thepetto.core.api.board.dto.ResponseBoardTypeAnimalWalkDto
import com.thepetto.core.api.board.repository.BoardContentRepository
import com.thepetto.core.api.board.repository.BoardRepository
import com.thepetto.core.api.global.exception.custom.FailedUploadImageException
import com.thepetto.core.api.global.exception.custom.NotFoundAccountException
import com.thepetto.core.api.global.exception.custom.NotFoundBoardException
import com.thepetto.core.api.global.security.SecurityUtil
import com.thepetto.core.api.global.util.S3Uploader
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.access.AccessDeniedException

@Service
class BoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val accountRepository: AccountRepository,
    private val boardContentRepository: BoardContentRepository,
    private val securityUtil: SecurityUtil,
    private val s3Uploader: S3Uploader,
) : BoardService {

    @Transactional
    override fun createBoardTypeAnimalWalk(requestCreateAnimalWalkBoardDto: RequestCreateAnimalWalkBoardDto): Long {
        val username = securityUtil.currentUsername()
        val account = accountRepository.findOneWithAuthoritiesByUsername(username)
            ?: throw NotFoundAccountException()

        val boardContent = boardContentRepository.save(
            BoardContent(
                content = requestCreateAnimalWalkBoardDto.content,
            )
        )

        var board = Board(
            account = account,
            category = BoardCategory.ANIMAL_WALK,
            title = requestCreateAnimalWalkBoardDto.title,
            boardContent = boardContent,
        )

        requestCreateAnimalWalkBoardDto.images.forEach { it ->
            val url: String = s3Uploader.upload(it, "animal-walk") ?: throw FailedUploadImageException()
            board.addImage(BoardImage(board = board, url = url))
        }

        board = boardRepository.save(board)
        return board.id
    }

    @Transactional(readOnly = true)
    override fun getBoardTypeAnimalWalkByPageable(pageable: Pageable): Page<ResponseBoardListTypeAnimalWalkDto> {
        val boards = boardRepository.findByCategory(pageable, BoardCategory.ANIMAL_WALK)
        return boards.map {
            ResponseBoardListTypeAnimalWalkDto.of(it)
        }
    }

    @Transactional(readOnly = true)
    override fun getBoardTypeAnimalWalk(boardId: Long): ResponseBoardTypeAnimalWalkDto {
        val board = boardRepository.findByIdOrNull(boardId)
            ?: throw NotFoundBoardException()

        return ResponseBoardTypeAnimalWalkDto.of(board)
    }

    @Transactional
    override fun delete(boardId: Long, user: User): Boolean {
        val account = accountRepository.findOneWithAuthoritiesByUsername(username = user.username)
            ?: throw NotFoundAccountException()
        val board = boardRepository.findByIdOrNull(boardId)
            ?: throw NotFoundBoardException()

        if (account.isMember() && account.id != board.account.id) {
            throw AccessDeniedException("본인의 게시글만 삭제할 수 있습니다")
        }

        boardRepository.delete(board)
        return true
    }
}