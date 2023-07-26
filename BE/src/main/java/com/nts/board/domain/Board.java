package com.nts.board.domain;

import com.nts.board.domain.defaults.BaseTimeEntity;
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
    @OneToMany(mappedBy = "board")
    private List<Hashtag> hashtagList = new ArrayList<>();

    @Builder
    public Board(long id, String title, String content, String writer, String password) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.password = password;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
