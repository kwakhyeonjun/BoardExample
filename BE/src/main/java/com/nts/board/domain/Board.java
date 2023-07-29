package com.nts.board.domain;

import com.nts.board.domain.defaults.BaseTimeEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private String writer;
    private String password;
    private int likeCount;
    private int unlikeCount;
    private long viewCount;
    private boolean deleted;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();
    @Nullable
    private String hashtag;

    @Builder
    public Board(long id, String title, String content, String writer, String password, String hashtag) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
        this.hashtag = hashtag;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void delete() {
        this.deleted = true;
    }

    public void increaseLike() {
        this.likeCount++;
    }
}
