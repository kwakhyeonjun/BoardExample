package com.nts.board.api;

import com.nts.board.request.CommentRequest;
import com.nts.board.response.CommentResponse;
import com.nts.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request) {
        CommentResponse body = commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping("/comment/{boardId}")
    public ResponseEntity<List<CommentResponse>> getCommentList(@PathVariable Long boardId) {
        List<CommentResponse> body = commentService.getCommentList(boardId);
        return ResponseEntity.ok().body(body);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(@PathVariable Long commentId, @RequestBody CommentRequest request) {
        CommentResponse body = commentService.deleteComment(commentId, request);
        return ResponseEntity.ok().body(body);
    }

}
