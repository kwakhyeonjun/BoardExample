package com.nts.board.service;

import com.nts.board.request.CommentRequest;
import com.nts.board.response.CommentResponse;

public interface CommentService {
    CommentResponse createComment(CommentRequest request);
}
