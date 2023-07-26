package com.nts.board.response;

import com.nts.board.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
    List<String> hashtagList;

    public static BoardResponse from(Board board) {
        BoardResponse boardResponse = new BoardResponse();
        boardResponse.setId(board.getId());
        boardResponse.setTitle(board.getTitle());
        boardResponse.setContent(board.getContent());
        boardResponse.setWriter(board.getWriter());
        boardResponse.setCreatedDate(board.getCreatedDate());
        boardResponse.setLikeCount(board.getLikeCount());
        boardResponse.setViewCount(board.getViewCount());

        boardResponse.hashtagList = new ArrayList<>();
        if(board.getHashtag() != null)
            boardResponse.hashtagList.addAll(Arrays.asList(board.getHashtag().split(",")));

        return boardResponse;
    }
}
