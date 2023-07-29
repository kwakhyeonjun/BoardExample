package com.nts.board.service;

import com.nts.board.domain.Board;
import com.nts.board.domain.Comment;
import com.nts.board.exception.BoardException;
import com.nts.board.exception.CommentException;
import com.nts.board.repository.BoardRepository;
import com.nts.board.repository.CommentRepository;
import com.nts.board.request.CommentRequest;
import com.nts.board.response.CommentResponse;
import com.nts.board.security.JwtUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.nts.board.exception.BoardException.BOARD_INVALID;
import static com.nts.board.exception.CommentException.*;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtilities;

    @Override
    @Transactional
    public CommentResponse createComment(CommentRequest request) {
        Board board = boardRepository.findById(request.getBoardId()).orElseThrow(() -> new BoardException(BoardException.BOARD_NOT_FOUND));
        Comment comment = Comment.builder()
                .content(request.getContent())
                .writer(request.getWriter())
                .password(passwordEncoder.encode(request.getPassword()))
                .board(board)
                .build();
        Comment savedComment = commentRepository.save(comment);
        return CommentResponse.from(savedComment);
    }

    @Override
    public List<CommentResponse> getCommentList(long boardId) {
        List<Comment> commentList = commentRepository.findByBoard_Id(boardId);
        return commentList.stream()
                .sorted((c1, c2) -> c2.getCreatedDate().compareTo(c1.getCreatedDate()))
                .map(CommentResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentResponse deleteComment(String header, long commentId, CommentRequest request) {
        String token = jwtUtilities.getToken(header);
        if(token == null || !jwtUtilities.validateToken(token)) throw new BoardException(BOARD_INVALID);

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException(COMMENT_NOT_FOUND));
        if(!passwordEncoder.matches(comment.getPassword(), request.getPassword()))
            throw new CommentException(COMMENT_PASSWORD_FAIL);
        comment.delete();
        return CommentResponse.from(comment);
    }

    @Override
    public String findCommentPassword(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException(COMMENT_NOT_FOUND));
        return comment.getPassword();
    }
}
