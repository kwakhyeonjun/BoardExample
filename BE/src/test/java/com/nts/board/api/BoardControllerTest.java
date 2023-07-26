package com.nts.board.api;

import com.nts.board.domain.Board;
import com.nts.board.exception.BoardException;
import com.nts.board.repository.BoardRepository;
import com.nts.board.request.BoardRequest;
import com.nts.board.response.BoardResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static com.nts.board.exception.BoardException.BOARD_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    BoardController boardController;

    @Autowired
    BoardRepository boardRepository;

    @Nested
    @DisplayName("게시물 생성")
    class CreateBoard {

        private String title;
        private String content;
        private String writer;
        private String password;

        @BeforeEach
        void setup() {
            title = "제목";
            content = "내용";
            writer = "작성자";
            password = "비밀번호";
        }

        @Test
        @DisplayName("요청에 맞는 게시물 저장")
        void createBoardSuccess() {
            BoardRequest request = new BoardRequest();
            request.setTitle(title);
            request.setContent(content);
            request.setWriter(writer);
            request.setPassword(password);

            ResponseEntity<BoardResponse> response = boardController.createBoard(request);

            assertThat(content).isEqualTo(Objects.requireNonNull(response.getBody()).getContent());
        }
    }

    @Nested
    @DisplayName("게시물 찾기")
    class GetBoard {
        private String title;
        private String content;
        private String writer;

        @BeforeEach
        void setup() {
            title = "제목";
            content = "내용";
            writer = "작성자";
        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("게시물 번호로 찾기")
            void getBoardSuccess() {
                Board board = Board.builder()
                        .title(title)
                        .content(content)
                        .writer(writer)
                        .build();
                Board saveBoard = boardRepository.save(board);

                ResponseEntity<BoardResponse> response = boardController.getBoard(saveBoard.getId());

                assertThat(content).isEqualTo(Objects.requireNonNull(response.getBody()).getContent());
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("존재하지 않는 게시물인 경우")
            void getBoardFail() {
                Exception exception = assertThrows(BoardException.class, () -> {
                    boardController.getBoard(2);
                });
                assertThat(BOARD_NOT_FOUND).isEqualTo(exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("게시물 수정하기")
    class UpdateBoard {
        private String title;
        private String content;
        private String writer;

        @BeforeEach
        void setup() {
            title = "제목";
            content = "내용";
            writer = "작성자";
        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("기존에 존재하는 게시물을 수정함")
            void updateBoardSuccess() {
                Board board = Board.builder()
                        .title(title)
                        .content(content)
                        .writer(writer)
                        .build();
                Board saveBoard = boardRepository.save(board);

                String newTitle = "새제목";
                String newContent = "새내용";
                BoardRequest request = new BoardRequest();
                request.setTitle(newTitle);
                request.setContent(newContent);

                ResponseEntity<BoardResponse> response = boardController.updateBoard(saveBoard.getId(), request);

                assertThat(newContent).isEqualTo(Objects.requireNonNull(response.getBody()).getContent());
                assertThat(newTitle).isEqualTo(Objects.requireNonNull(response.getBody()).getTitle());
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("존재하지 않는 게시물을 수정하려 함")
            void updateBoardFail() {
                String newTitle = "새제목";
                String newContent = "새내용";
                BoardRequest request = new BoardRequest();
                request.setTitle(newTitle);
                request.setContent(newContent);

                Exception exception = assertThrows(BoardException.class, () -> {
                    boardController.updateBoard(2, request);
                });

                assertThat(BOARD_NOT_FOUND).isEqualTo(exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("게시물 삭제")
    class DeleteBoard {
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
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("저장된 케이스 삭제")
            void deleteBoardSuccess() {
                Board board = Board.builder()
                        .title(title)
                        .writer(writer)
                        .content(content)
                        .password(password)
                        .build();
                long id = boardRepository.save(board).getId();

                boardController.deleteBoard(id);

                Exception exception = assertThrows(BoardException.class, () -> boardRepository.findById(id));
                assertThat(BOARD_NOT_FOUND).isEqualTo(exception.getMessage());
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("삭제하려는 게시물이 존재하지 않는 경우")
            void deleteBoardFail() {
                long id = 0L;

                Exception exception = assertThrows(BoardException.class, () -> boardController.deleteBoard(id));

                assertThat(BOARD_NOT_FOUND).isEqualTo(exception.getMessage());
            }
        }
    }
}