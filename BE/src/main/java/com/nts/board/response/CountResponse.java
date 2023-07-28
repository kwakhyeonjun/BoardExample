package com.nts.board.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountResponse {
    Long boardCount;
    Long commentCount;

    public static CountResponse from(long boardCount, long commentCount) {
        CountResponse response = new CountResponse();
        response.boardCount = boardCount;
        response.commentCount = commentCount;
        return response;
    }
}
