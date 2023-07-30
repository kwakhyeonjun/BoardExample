package com.nts.board.service;

import com.nts.board.domain.Board;
import com.nts.board.domain.Comment;
import com.nts.board.exception.BoardException;
import com.nts.board.exception.CommentException;
import com.nts.board.repository.BoardRepository;
import com.nts.board.repository.CommentRepository;
import com.nts.board.request.PasswordRequest;
import com.nts.board.response.PasswordResponse;
import com.nts.board.security.JwtUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.nts.board.exception.BoardException.BOARD_INVALID;
import static com.nts.board.exception.BoardException.BOARD_NOT_FOUND;
import static com.nts.board.exception.CommentException.COMMENT_INVALID;
import static com.nts.board.exception.CommentException.COMMENT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService{
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;

    @Override
    public PasswordResponse validateBoardPassword(Long boardId, PasswordRequest request) {
        String boardPassword = findBoardPassword(boardId);
        if(!passwordEncoder.matches(request.getPassword(), boardPassword)) throw new BoardException(BOARD_INVALID);
        String newToken = jwtUtilities.generateToken(request.getPassword());
        return PasswordResponse.from(newToken);
    }

    @Override
    public PasswordResponse validateCommentPassword(Long commentId, PasswordRequest request) {
        String commentPassword = findCommentPassword(commentId);
        if(!passwordEncoder.matches(request.getPassword(), commentPassword)) throw new CommentException(COMMENT_INVALID);
        String newToken = jwtUtilities.generateToken(request.getPassword());
        return PasswordResponse.from(newToken);
    }

    private String findBoardPassword(long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
        return board.getPassword();
    }

    private String findCommentPassword(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException(COMMENT_NOT_FOUND));
        return comment.getPassword();
    }
}
