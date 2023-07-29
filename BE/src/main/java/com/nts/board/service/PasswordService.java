package com.nts.board.service;

import com.nts.board.request.PasswordRequest;
import com.nts.board.response.PasswordResponse;

public interface PasswordService {
    PasswordResponse validateBoardPassword(Long boardId, PasswordRequest request);

    PasswordResponse validateCommentPassword(Long commentId, PasswordRequest request);
}
