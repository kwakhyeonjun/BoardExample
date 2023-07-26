package com.nts.board.exception;

public class CommentException extends RuntimeException{
    public static final String COMMENT_NOT_FOUND = "댓글을 찾을 수 없습니다.";

    public CommentException(String message) {
        super(message);
    }
}
