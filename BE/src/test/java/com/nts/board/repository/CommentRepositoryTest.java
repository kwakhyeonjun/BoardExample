package com.nts.board.repository;

import com.nts.board.domain.Board;
import com.nts.board.domain.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("댓글 작성")
    void save() {
        String title = "제목";
        String content = "내용";
        String writer = "작성자";
        String password = "password";

        Board board = Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .password(password)
                .build();
        Board saveBoard = boardRepository.save(board);

        Comment comment = Comment.builder()
                .content(content)
                .writer(writer)
                .password(password)
                .board(saveBoard)
                .build();

        Comment savedComment = commentRepository.save(comment);

        assertThat(content).isEqualTo(savedComment.getContent());
    }

    @Test
    @DisplayName("댓글 목록")
    void find() {
        String title = "제목";
        String content1 = "내용1";
        String content2 = "내용2";
        String writer = "작성자";
        String password = "password";

        Board board = Board.builder()
                .title(title)
                .password(password)
                .writer(writer)
                .build();
        Board saveBoard = boardRepository.save(board);

        Comment comment1 = Comment.builder()
                .content(content1)
                .writer(writer)
                .password(password)
                .board(saveBoard)
                .build();
        Comment comment2 = Comment.builder()
                .content(content2)
                .writer(writer)
                .password(password)
                .board(saveBoard)
                .build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        List<Comment> commentList = commentRepository.findByBoard_Id(saveBoard.getId());

        assertThat(commentList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("댓글 개수 세기")
    void countComment() {
        String title = "제목";
        String content1 = "내용1";
        String content2 = "내용2";
        String writer = "작성자";
        String password = "password";

        Board board = Board.builder()
                .title(title)
                .password(password)
                .writer(writer)
                .build();
        Board saveBoard = boardRepository.save(board);

        Comment comment1 = Comment.builder()
                .content(content1)
                .writer(writer)
                .password(password)
                .board(saveBoard)
                .build();
        Comment comment2 = Comment.builder()
                .content(content2)
                .writer(writer)
                .password(password)
                .board(saveBoard)
                .build();
        Comment comment3 = Comment.builder()
                .content(content1)
                .writer(writer)
                .password(password)
                .deleted(true)
                .board(board)
                .build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);

        Long count = commentRepository.countByDeletedIsFalse();

        assertThat(count).isEqualTo(2);
    }
}