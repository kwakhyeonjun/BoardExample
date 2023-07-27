package com.nts.board.exception;

public class BoardException extends RuntimeException {
    public static final String BOARD_NOT_FOUND = "존재하지 않는 게시물입니다.";
    public static final String BOARD_FORBIDDEN = "허용되지 않은 접근입니다.";
    public static final String BOARD_DELETED = "삭제된 게시물입니다.";

    public BoardException(String message) {
        super(message);
    }
}
