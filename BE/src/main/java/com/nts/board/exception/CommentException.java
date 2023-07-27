package com.nts.board.exception;

public class CommentException extends RuntimeException{
    public static final String COMMENT_NOT_FOUND = "댓글을 찾을 수 없습니다.";
    public static final String COMMENT_INVALID = "허용되지 않은 접근입니다.";
    public static final String COMMENT_PASSWORD_FAIL = "올바르지 않은 비밀번호입니다.";

    public CommentException(String message) {
        super(message);
    }
}
