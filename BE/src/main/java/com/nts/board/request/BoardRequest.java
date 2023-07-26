package com.nts.board.request;

import com.nts.board.domain.Hashtag;
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
    List<Hashtag> hashtagList;
}
