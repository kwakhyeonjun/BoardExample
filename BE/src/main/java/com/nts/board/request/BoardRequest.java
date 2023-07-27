package com.nts.board.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardRequest {
    String title;
    String writer;
    String content;
    String password;
    List<String> hashtagList;

    public String getHashtag() {
        if(hashtagList == null) return null;
        StringBuilder sb = new StringBuilder();
        for (String hashtag : hashtagList) {
            sb.append(hashtag).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
