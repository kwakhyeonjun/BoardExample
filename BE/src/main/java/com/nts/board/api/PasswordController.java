package com.nts.board.api;

import com.nts.board.request.PasswordRequest;
import com.nts.board.response.PasswordResponse;
import com.nts.board.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @PostMapping("/password/board/{boardId}")
    public ResponseEntity<PasswordResponse> boardPasswordValidate(@PathVariable Long boardId, @RequestBody PasswordRequest request) {
        PasswordResponse body = passwordService.validateBoardPassword(boardId, request);
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/password/comment/{commentId}")
    public ResponseEntity<PasswordResponse> commentPasswordValidate(@PathVariable Long commentId, @RequestBody PasswordRequest request) {
        PasswordResponse body = passwordService.validateCommentPassword(commentId, request);
        return ResponseEntity.ok().body(body);
    }
}
