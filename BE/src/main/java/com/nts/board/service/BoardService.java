package com.nts.board.service;

import com.nts.board.domain.Board;
import com.nts.board.request.BoardSaveRequest;
import com.nts.board.response.BoardResponse;

public interface BoardService {
    BoardResponse findBoard(long boardId);
    BoardResponse saveBoard(BoardSaveRequest request);
}
