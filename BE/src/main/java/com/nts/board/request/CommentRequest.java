package com.nts.board.request;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private String content;
    private String writer;
    private Long boardId;
    private LocalDateTime createdDate;
    private String password;
}
