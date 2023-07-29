package com.nts.board.api;

import com.nts.board.exception.BoardException;
import com.nts.board.exception.CommentException;
import com.nts.board.request.PasswordRequest;
import com.nts.board.security.JwtUtilities;
import com.nts.board.service.BoardService;
import com.nts.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.nts.board.exception.BoardException.*;
import static com.nts.board.exception.CommentException.*;

@RestController
@RequiredArgsConstructor
public class PasswordController {

    private final JwtUtilities jwtUtilities;
    private final PasswordEncoder passwordEncoder;
    private final BoardService boardService;
    private final CommentService commentService;

    @PostMapping("/password/board/{boardId}")
    public ResponseEntity<String> boardPasswordValidate(@PathVariable Long boardId, @RequestBody PasswordRequest request) {
        String boardPassword = boardService.findBoardPassword(boardId);
        if(!passwordEncoder.matches(request.getPassword(), boardPassword)) throw new BoardException(BOARD_INVALID);
        String newToken = jwtUtilities.generateToken(request.getPassword());
        return ResponseEntity.ok().header("Authorization", newToken).body("ok");
    }

    @PostMapping("/password/comment/{commentId}")
    public ResponseEntity<String> commentPasswordValidate(@PathVariable Long commentId, @RequestBody PasswordRequest request) {
        String commentPassword = commentService.findCommentPassword(commentId);
        if(!passwordEncoder.matches(request.getPassword(), commentPassword)) throw new CommentException(COMMENT_INVALID);
        String newToken = jwtUtilities.generateToken(request.getPassword());
        return ResponseEntity.ok().header("Authorization", newToken).body("ok");
    }
}
