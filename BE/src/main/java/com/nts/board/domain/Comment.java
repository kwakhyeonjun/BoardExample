package com.nts.board.domain;

import com.nts.board.domain.defaults.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no")
    private Board board;

    String content;
    String writer;
    String password;
    boolean deleted;

    @Builder
    public Comment(long id, Board board, String content, String writer, String password, boolean deleted) {
        this.id = id;
        this.board = board;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.deleted = deleted;
    }

    public void delete() {
        this.deleted = true;
    }
}
