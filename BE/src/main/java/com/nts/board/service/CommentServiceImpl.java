package com.nts.board.service;

import com.nts.board.domain.Board;
import com.nts.board.domain.Comment;
import com.nts.board.exception.BoardException;
import com.nts.board.repository.BoardRepository;
import com.nts.board.repository.CommentRepository;
import com.nts.board.request.CommentRequest;
import com.nts.board.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public CommentResponse createComment(CommentRequest request) {
        Board board = boardRepository.findById(request.getBoardId()).orElseThrow(() -> new BoardException(BoardException.BOARD_NOT_FOUND));
        Comment comment = Comment.builder()
                .content(request.getContent())
                .writer(request.getWriter())
                .password(request.getPassword())
                .board(board)
                .build();
        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.from(savedComment);
    }
}
