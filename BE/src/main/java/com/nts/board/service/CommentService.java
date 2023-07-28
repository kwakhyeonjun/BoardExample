package com.nts.board.service;

import com.nts.board.request.CommentRequest;
import com.nts.board.response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(CommentRequest request);
    List<CommentResponse> getCommentList(long boardId);
    public CommentResponse deleteComment(String header, long commentId, CommentRequest request);
    String findCommentPassword(Long commentId);
}
