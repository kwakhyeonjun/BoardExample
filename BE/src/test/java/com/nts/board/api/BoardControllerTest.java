package com.nts.board.api;

import com.nts.board.domain.Board;
import com.nts.board.exception.BoardException;
import com.nts.board.repository.BoardRepository;
import com.nts.board.request.BoardRequest;
import com.nts.board.response.BoardResponse;
import com.nts.board.security.JwtUtilities;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

import static com.nts.board.exception.BoardException.BOARD_INVALID;
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
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtilities jwtUtilities;

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
            password = "password";
        }

        @Test
        @Transactional
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
            @Transactional
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
            @Transactional
            @DisplayName("존재하지 않는 게시물인 경우")
            void getBoardFail() {
                Exception exception = assertThrows(BoardException.class, () -> {
                    boardController.getBoard(2L);
                });
                assertThat(BOARD_NOT_FOUND).isEqualTo(exception.getMessage());
            }
        }
    }

//    @Nested
//    @DisplayName("게시물 수정하기")
//    class UpdateBoard {
//        private String title;
//        private String content;
//        private String writer;
//        private String password;
//
//        @BeforeEach
//        void setup() {
//            title = "제목";
//            content = "내용";
//            writer = "작성자";
//            password = "password";
//        }

//        @Nested
//        @DisplayName("정상 케이스")
//        class SuccessCase {
//            @Test
//            @Transactional
//            @DisplayName("기존에 존재하는 게시물을 수정함")
//            void updateBoardSuccess() {
//                Board board = Board.builder()
//                        .title(title)
//                        .content(content)
//                        .writer(writer)
//                        .password(passwordEncoder.encode(password))
//                        .build();
//                Board saveBoard = boardRepository.save(board);
//
//                String newTitle = "새제목";
//                String newContent = "새내용";
//                BoardRequest request = new BoardRequest();
//                request.setTitle(newTitle);
//                request.setContent(newContent);
//                request.setPassword(password);
//
//                String bearerToken = jwtUtilities.generateToken(password);
//
//                ResponseEntity<BoardResponse> response = boardController.updateBoard(bearerToken, saveBoard.getId(), request);
//
//                assertThat(newContent).isEqualTo(Objects.requireNonNull(response.getBody()).getContent());
//                assertThat(newTitle).isEqualTo(Objects.requireNonNull(response.getBody()).getTitle());
//            }
//        }
//
//        @Nested
//        @DisplayName("비정상 케이스")
//        class FailCase {
//            @Test
//            @Transactional
//            @DisplayName("존재하지 않는 게시물을 수정하려 함")
//            void updateBoardFail() {
//                String newTitle = "새제목";
//                String newContent = "새내용";
//                BoardRequest request = new BoardRequest();
//                request.setTitle(newTitle);
//                request.setContent(newContent);
//
//                String bearerToken = jwtUtilities.generateToken("password");
//
//                Exception exception = assertThrows(BoardException.class, () -> {
//                    boardController.updateBoard(bearerToken, 2L, request);
//                });
//
//                assertThat(BOARD_NOT_FOUND).isEqualTo(exception.getMessage());
//            }
//        }
//    }

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

//        @Nested
//        @DisplayName("정상 케이스")
//        class SuccessCase {
//            @Test
//            @Transactional
//            @DisplayName("저장된 케이스 삭제")
//            void deleteBoardSuccess() {
//                Board board = Board.builder()
//                        .title(title)
//                        .writer(writer)
//                        .content(content)
//                        .password(passwordEncoder.encode(password))
//                        .build();
//                Board saveboard = boardRepository.save(board);
//
//                BoardRequest request = new BoardRequest();
//                request.setPassword(password);
//                String bearerToken = jwtUtilities.generateToken(password);
//
//                ResponseEntity<BoardResponse> response = boardController.deleteBoard(bearerToken, saveboard.getId(), request);
//
//                assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(saveboard.getId());
//            }
//        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @Transactional
            @DisplayName("권한 없이 접근하는 경우")
            void deleteBoardFail() {
                long id = 0L;
                BoardRequest request = new BoardRequest();
                request.setPassword(password);
                String wrongToken = jwtUtilities.generateToken(password);

                Exception exception = assertThrows(BoardException.class, () -> boardController.deleteBoard(wrongToken, id));

                assertThat(BOARD_INVALID).isEqualTo(exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("게시물 목록")
    class BoardList {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @Transactional
            @DisplayName("전체 목록을 호출")
            void findBoardListAll() {
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

                ResponseEntity<List<BoardResponse>> response = boardController.findBoardAll();

                assertThat(Objects.requireNonNull(response.getBody()).size()).isEqualTo(2);
            }
        }
    }

    @Test
    @Transactional
    @DisplayName("검색")
    void search() {
        for (int i = 0; i < 11; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                sb.append("해시").append(i).append(",");
            }
            String hashtag = sb.deleteCharAt(sb.length() - 1).toString();
            Board board = Board.builder()
                    .title("제목" + i)
                    .content("내용" + i)
                    .writer("작성자" + i)
                    .hashtag(hashtag)
                    .build();
            boardRepository.save(board);
        }

        ResponseEntity<List<BoardResponse>> response = boardController.searchBoard("TITLE", "제목");

        assertThat(Objects.requireNonNull(response.getBody()).size()).isEqualTo(11);
    }
}