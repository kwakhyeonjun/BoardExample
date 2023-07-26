package com.nts.board.repository;

import com.nts.board.domain.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 작성")
    void save() {
        String content = "내용";
        String writer = "작성자";
        String password = "password";

        Comment comment = Comment.builder()
                .content(content)
                .writer(writer)
                .password(password)
                .build();

        Comment savedComment = commentRepository.save(comment);

        assertThat(content).isEqualTo(savedComment.getContent());
    }
}