package com.nts.board.api;

import com.nts.board.domain.Board;
import com.nts.board.domain.Comment;
import com.nts.board.repository.BoardRepository;
import com.nts.board.repository.CommentRepository;
import com.nts.board.response.CountResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CountControllerTest {

    @Autowired
    CountController countController;

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CommentRepository commentRepository;

    @Test
    @Transactional
    @DisplayName("검색")
    void count() {
        String title1 = "제목1";
        String title2 = "제목2";
        String writer = "작성자";
        String content = "내용";
        String password = "password";

        Board board1 = Board.builder()
                .title(title1)
                .writer(writer)
                .content(content)
                .password(password)
                .build();

        Board board2 = Board.builder()
                .title(title2)
                .writer(writer)
                .content(content)
                .password(password)
                .build();

        boardRepository.save(board1);
        boardRepository.save(board2);

        String title = "제목";
        String content1 = "내용1";
        String content2 = "내용2";

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

        ResponseEntity<CountResponse> response = countController.count();

        CountResponse body = response.getBody();
        assert body != null;
        assertThat(body.getCommentCount()).isEqualTo(2);
        assertThat(body.getBoardCount()).isEqualTo(3);
    }
}