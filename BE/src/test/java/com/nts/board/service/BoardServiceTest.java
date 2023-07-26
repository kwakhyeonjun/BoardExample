package com.nts.board.service;

import com.nts.board.domain.Board;
import com.nts.board.exception.BoardException;
import com.nts.board.repository.BoardRepository;
import com.nts.board.request.BoardRequest;
import com.nts.board.response.BoardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static com.nts.board.exception.BoardException.BOARD_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
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
            password = "password";
        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {

            @Test
            @DisplayName("새로운 게시물 정상 저장")
            void createBoardSuccess() {
                Board board = Board.builder()
                        .title(title)
                        .content(content)
                        .writer(writer)
                        .password(password)
                        .build();
                when(boardRepository.save(any(Board.class))).thenReturn(board);

                BoardRequest request = new BoardRequest();
                request.setTitle(title);
                request.setContent(content);
                request.setWriter(writer);
                request.setPassword(password);

                BoardService boardService = new BoardServiceImpl(boardRepository);
                BoardResponse response = boardService.saveBoard(request);

                assertThat(response.getTitle()).isEqualTo(request.getTitle());
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {

            @Test
            @DisplayName("게시물이 없는 경우")
            void createBoardFail() {
                when(boardRepository.save(any(Board.class))).thenThrow(new BoardException(BOARD_NOT_FOUND));

                BoardRequest request = new BoardRequest();
                request.setTitle(title);
                request.setContent(content);
                request.setWriter(writer);
                request.setPassword(password);

                BoardService boardService = new BoardServiceImpl(boardRepository);

                Exception exception = assertThrows(BoardException.class, () -> {
                    boardService.saveBoard(request);
                });

                assertThat(exception.getMessage()).isEqualTo(BOARD_NOT_FOUND);
            }
        }
    }

    @Nested
    @DisplayName("게시물 호출")
    class BoardFind {

        private Long id;
        private String title;
        private String content;
        private String writer;
        private String password;

        @BeforeEach
        void setup() {
            id = 1L;
            title = "제목";
            content = "내용";
            writer = "작성자";
            password = "password";
        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {

            @Test
            @DisplayName("저장된 게시물 호출")
            public void findBoardSuccess() {
                Board board = Board.builder()
                        .id(id)
                        .title(title)
                        .content(content)
                        .writer(writer)
                        .password(password)
                        .build();
                when(boardRepository.save(any(Board.class))).thenReturn(board);
                when(boardRepository.findById(id)).thenReturn(Optional.of(board));

                Board saveBoard = boardRepository.save(board);

                BoardService boardService = new BoardServiceImpl(boardRepository);
                BoardResponse findBoard = boardService.findBoard(saveBoard.getId());

                assertThat(findBoard.getTitle()).isEqualTo(saveBoard.getTitle());
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {

            @Test
            @DisplayName("호출할 게시물이 없는 경우")
            public void findBoardFail() {
                when(boardRepository.findById(id)).thenThrow(new BoardException(BOARD_NOT_FOUND));

                BoardService boardService = new BoardServiceImpl(boardRepository);

                Exception exception = assertThrows(BoardException.class, () -> {
                    boardService.findBoard(id);
                });

                assertThat(exception.getMessage()).isEqualTo(BOARD_NOT_FOUND);
            }
        }
    }

    @Nested
    @DisplayName("게시물 수정")
    class BoardUpdate {

        private Long id;
        private String title;
        private String content;
        private String writer;

        @BeforeEach
        void setup() {
            id = 1L;
            title = "제목";
            content = "내용";
            writer = "작성자";
        }

        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {

            @Test
            @DisplayName("기존 제시물의 내용 수정")
            public void updateBoardSuccess() {
                Board board = Board.builder()
                        .id(id)
                        .title(title)
                        .content(content)
                        .writer(writer)
                        .build();
                when(boardRepository.findById(id)).thenReturn(Optional.of(board));

                BoardService boardService = new BoardServiceImpl(boardRepository);
                String newContent = "새로운 내용";

                BoardRequest request = new BoardRequest();
                request.setTitle(title);
                request.setContent(newContent);


                BoardResponse boardResponse = boardService.updateBoard(id, request);
                assertThat(boardResponse.getContent()).isEqualTo(newContent);
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {

            @Test
            @DisplayName("수정할 게시물을 찾지 못한 경우")
            void updateBoardFail() {
                when(boardRepository.findById(id)).thenThrow(new BoardException(BOARD_NOT_FOUND));

                BoardService boardService = new BoardServiceImpl(boardRepository);
                String newContent = "새로운 내용";

                BoardRequest request = new BoardRequest();
                request.setTitle(title);
                request.setContent(newContent);

                Exception exception = assertThrows(BoardException.class, () -> {
                    boardService.updateBoard(id, request);
                });

                assertThat(BOARD_NOT_FOUND).isEqualTo(exception.getMessage());
            }
        }

    }
}