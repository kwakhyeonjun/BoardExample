package com.nts.board.response;

import com.nts.board.domain.Board;
import com.nts.board.domain.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {
    long id;
    String title;
    String content;
    String writer;
    LocalDateTime createdDate;
    int likeCount;
    long viewCount;
    List<Hashtag> hashtagList;

    public static BoardResponse from(Board board) {
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setId(board.getId());
        boardResponse.setTitle(board.getTitle());
        boardResponse.setContent(board.getContent());
        boardResponse.setWriter(board.getWriter());
        boardResponse.setCreatedDate(board.getCreatedDate());
        boardResponse.setLikeCount(board.getLikeCount());
        boardResponse.setViewCount(board.getViewCount());

        return boardResponse;
    }
}