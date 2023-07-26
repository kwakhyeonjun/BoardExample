package com.nts.board.api;

import com.nts.board.domain.Board;
import com.nts.board.domain.Comment;
import com.nts.board.repository.BoardRepository;
import com.nts.board.repository.CommentRepository;
import com.nts.board.request.CommentRequest;
import com.nts.board.response.CommentResponse;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    CommentController commentController;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    CommentRepository commentRepository;

    private String title;
    private String content;
    private String writer;
    private String password;

    @BeforeEach
    void setup() {
        title = "제목";
        content = "내용";
        writer = "작성자";
        password = "password";
    }

    @Nested
    @Transactional
    @DisplayName("댓글 생성")
    class CreateComment {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("댓글 작성됨")
            void createCommentSuccess() {
                Board board = Board.builder()
                        .title(title)
                        .writer(writer)
                        .content(content)
                        .build();
                Board savedBoard = boardRepository.save(board);

                CommentRequest request = new CommentRequest();
                request.setContent(content);
                request.setWriter(writer);
                request.setBoardId(savedBoard.getId());
                request.setPassword(password);

                ResponseEntity<CommentResponse> response = commentController.createComment(request);

                Assertions.assertThat(Objects.requireNonNull(response.getBody()).getContent()).isEqualTo(content);
            }
        }
    }

    @Nested
    @Transactional
    @DisplayName("댓글 목록")
    class findCommentList {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("댓글 목록 확인")
            void findCommentList() {
                Board board = Board.builder()
                        .title(title)
                        .writer(writer)
                        .content(content)
                        .build();
                Board saveBoard = boardRepository.save(board);

                String content1 = "댓글1";
                String content2 = "댓글2";

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

                ResponseEntity<List<CommentResponse>> commentList = commentController.getCommentList(saveBoard.getId());

                Assertions.assertThat(Objects.requireNonNull(commentList.getBody()).size()).isEqualTo(2);
            }
        }
    }
}