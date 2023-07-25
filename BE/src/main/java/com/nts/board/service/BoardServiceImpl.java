package com.nts.board.service;

import com.nts.board.domain.Board;
import com.nts.board.exception.BoardException;
import com.nts.board.repository.BoardRepository;
import com.nts.board.request.BoardSaveRequest;
import com.nts.board.response.BoardResponse;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.nts.board.exception.BoardException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public BoardResponse findBoard(long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
        return BoardResponse.from(board);
    }

    @Override
    public BoardResponse saveBoard(BoardSaveRequest request) {
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(request.getWriter())
                .password(request.getPassword())
                .build();
        Board saveBoard = boardRepository.save(board);
        return BoardResponse.from(saveBoard);
    }

}
