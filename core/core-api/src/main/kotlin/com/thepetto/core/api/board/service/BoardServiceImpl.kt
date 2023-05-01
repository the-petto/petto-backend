package com.thepetto.core.api.board.service

import com.thepetto.core.api.account.repository.AccountRepository
import com.thepetto.core.api.board.domain.Board
import com.thepetto.core.api.board.domain.BoardCategory
import com.thepetto.core.api.board.domain.BoardContent
import com.thepetto.core.api.board.dto.RequestCreateAnimalWalkBoardDto
import com.thepetto.core.api.board.dto.ResponseBoardListTypeAnimalWalkDto
import com.thepetto.core.api.board.dto.ResponseBoardTypeAnimalWalkDto
import com.thepetto.core.api.board.repository.BoardContentRepository
import com.thepetto.core.api.board.repository.BoardRepository
import com.thepetto.core.api.global.exception.custom.NotFoundAccountException
import com.thepetto.core.api.global.exception.custom.NotFoundBoardException
import com.thepetto.core.api.global.security.SecurityUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardServiceImpl(
    private val boardRepository: BoardRepository,
    private val accountRepository: AccountRepository,
    private val boardContentRepository: BoardContentRepository,
) : BoardService {

    @Transactional
    override fun createBoardTypeAnimalWalk(requestCreateAnimalWalkBoardDto: RequestCreateAnimalWalkBoardDto): Long {
        val username = SecurityUtil.currentUsername()
        val account = accountRepository.findOneWithAuthoritiesByUsername(username)
            ?: throw NotFoundAccountException()

        val boardContent = boardContentRepository.save(
            BoardContent(
                content = requestCreateAnimalWalkBoardDto.content,
            )
        )

        val board = boardRepository.save(
            Board(
                account = account,
                category = BoardCategory.ANIMAL_WALK,
                title = requestCreateAnimalWalkBoardDto.title,
                boardContent = boardContent,
            )
        )

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
}