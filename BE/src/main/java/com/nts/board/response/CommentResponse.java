package com.nts.board.response;

import com.nts.board.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private long id;
    private String content;
    private String writer;
    private LocalDateTime createdDate;
    private boolean deleted;

    public static CommentResponse from(Comment comment) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.id = comment.getId();
        commentResponse.content = comment.getContent();
        commentResponse.writer = comment.getWriter();
        commentResponse.createdDate = comment.getCreatedDate();
        commentResponse.deleted = comment.isDeleted();

        return commentResponse;
    }
}
