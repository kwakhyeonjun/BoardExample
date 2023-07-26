package com.nts.board.api;

import com.nts.board.request.CommentRequest;
import com.nts.board.response.CommentResponse;
import com.nts.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request) {
        CommentResponse body = commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
