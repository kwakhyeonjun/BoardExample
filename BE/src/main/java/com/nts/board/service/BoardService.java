package com.nts.board.service;

import com.nts.board.request.BoardRequest;
import com.nts.board.response.BoardResponse;

public interface BoardService {
    BoardResponse findBoard(long boardId);
    BoardResponse saveBoard(BoardRequest request);
    BoardResponse updateBoard(long boardId, BoardRequest request);
    BoardResponse deleteBoard(long boardId);
}
