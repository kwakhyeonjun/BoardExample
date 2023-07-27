package com.nts.board.service;

import com.nts.board.request.BoardRequest;
import com.nts.board.response.BoardResponse;

import java.util.List;

public interface BoardService {
    BoardResponse findBoard(long boardId);
    BoardResponse saveBoard(BoardRequest request);
    BoardResponse updateBoard(long boardId, BoardRequest request);
    BoardResponse deleteBoard(long boardId, BoardRequest request);
    List<BoardResponse> findBoardList();
    List<BoardResponse> searchBoardList(String title, String content, String writer, String hashtag);
    BoardResponse likeBoard(long boardId);
}
