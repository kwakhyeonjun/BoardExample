package com.nts.board.service;

import com.nts.board.domain.Board;
import com.nts.board.exception.BoardException;
import com.nts.board.repository.BoardRepository;
import com.nts.board.repository.SearchQueryRepository;
import com.nts.board.request.BoardRequest;
import com.nts.board.response.BoardResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.nts.board.exception.BoardException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final SearchQueryRepository searchQueryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BoardResponse findBoard(long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
        if(board.isDeleted()) throw new BoardException(BOARD_DELETED);
        board.increaseViewCount();
        return BoardResponse.from(board);
    }

    @Override
    public BoardResponse saveBoard(BoardRequest request) {
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(request.getWriter())
                .password(passwordEncoder.encode(request.getPassword()))
                .hashtag(request.getHashtag())
                .build();
        Board saveBoard = boardRepository.save(board);

        return BoardResponse.from(saveBoard);
    }

    @Override
    public BoardResponse updateBoard(long boardId, BoardRequest request) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
        if(findBoard.isDeleted()) throw new BoardException(BOARD_DELETED);
        if(!passwordEncoder.matches(request.getPassword(), findBoard.getPassword()))
            throw new BoardException(BOARD_FORBIDDEN);
        findBoard.update(request.getTitle(), request.getContent());
        return BoardResponse.from(findBoard);
    }

    @Override
    public BoardResponse deleteBoard(long boardId, BoardRequest request) {
        Board findBoard = boardRepository.findById(boardId).orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
        if(findBoard.isDeleted()) throw new BoardException(BOARD_DELETED);
        if(!passwordEncoder.matches(request.getPassword(), findBoard.getPassword()))
            throw new BoardException(BOARD_FORBIDDEN);
        findBoard.delete();
        return BoardResponse.from(findBoard);
    }

    @Override
    public List<BoardResponse> findBoardList() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream()
                .filter(board -> !board.isDeleted())
                .map(BoardResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardResponse> searchBoardList(String title, String content, String writer, String hashtag) {
        List<Board> boardList = searchQueryRepository.findBoardContains(title, content, writer, hashtag);
        return boardList.stream()
                .filter(board -> !board.isDeleted())
                .map(BoardResponse::from)
                .collect(Collectors.toList());
    }
}
