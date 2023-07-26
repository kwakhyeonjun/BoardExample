package com.nts.board.service;

import com.nts.board.domain.Board;
import com.nts.board.exception.BoardException;
import com.nts.board.repository.BoardRepository;
import com.nts.board.repository.SearchQueryRepository;
import com.nts.board.request.BoardRequest;
import com.nts.board.response.BoardResponse;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
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
    @Mock
    SearchQueryRepository searchQueryRepository;

    @Before
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String password;
    private String hashtag;

    @BeforeEach
    void setup() {
        title = "제목";
        content = "내용";
        writer = "작성자";
        password = "password";
        hashtag = "해시,태그";
    }

    @Nested
    @DisplayName("게시물 생성")
    class CreateBoard {
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
                        .hashtag(hashtag)
                        .build();
                when(boardRepository.save(any(Board.class))).thenReturn(board);

                BoardRequest request = new BoardRequest();
                request.setTitle(title);
                request.setContent(content);
                request.setWriter(writer);
                request.setPassword(password);
                List<String> hashtagList = new ArrayList<>();
                hashtagList.add("해시");
                hashtagList.add("태그");
                request.setHashtagList(hashtagList);

                BoardService boardService = new BoardServiceImpl(boardRepository, searchQueryRepository);
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

                BoardService boardService = new BoardServiceImpl(boardRepository, searchQueryRepository);

                Exception exception = assertThrows(BoardException.class, () -> boardService.saveBoard(request));

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

                BoardService boardService = new BoardServiceImpl(boardRepository, searchQueryRepository);
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

                BoardService boardService = new BoardServiceImpl(boardRepository, searchQueryRepository);

                Exception exception = assertThrows(BoardException.class, () -> boardService.findBoard(id));

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

                BoardService boardService = new BoardServiceImpl(boardRepository, searchQueryRepository);
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

                BoardService boardService = new BoardServiceImpl(boardRepository, searchQueryRepository);
                String newContent = "새로운 내용";

                BoardRequest request = new BoardRequest();
                request.setTitle(title);
                request.setContent(newContent);

                Exception exception = assertThrows(BoardException.class, () -> boardService.updateBoard(id, request));

                assertThat(BOARD_NOT_FOUND).isEqualTo(exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("게시물 삭제")
    class DeleteBoard {
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
            @DisplayName("기존의 게시물을 정상적으로 제거함")
            void deleteBoardSuccess() {
                Board board = Board.builder()
                        .title(title)
                        .content(content)
                        .writer(writer)
                        .password(password)
                        .build();
                when(boardRepository.findById(id)).thenReturn(Optional.of(board));

                BoardService boardService = new BoardServiceImpl(boardRepository, searchQueryRepository);
                BoardResponse boardResponse = boardService.deleteBoard(id);

                assertThat(boardResponse.getContent()).isEqualTo(content);
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("삭제하려는 게시물이 존재하지 않는 경우")
            void deleteBoardFail() {
                when(boardRepository.findById(id)).thenThrow(new BoardException(BOARD_NOT_FOUND));
                BoardService boardService = new BoardServiceImpl(boardRepository, searchQueryRepository);

                Exception exception = assertThrows(BoardException.class, () -> boardService.deleteBoard(id));

                assertThat(BOARD_NOT_FOUND).isEqualTo(exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("게시글 목록")
    class findBoardList {
        List<Board> boardList;
        @BeforeEach
        void setup() {
            boardList = new ArrayList<>();
            boardList.add(Board.builder().build());
            boardList.add(Board.builder().build());
        }
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("전체 목록을 호출")
            void findBoardListSuccess() {
                when(boardRepository.findAll()).thenReturn(boardList);

                BoardService boardService = new BoardServiceImpl(boardRepository, searchQueryRepository);
                List<BoardResponse> response = boardService.findBoardList();

                assertThat(response.size()).isEqualTo(2);
            }
        }
    }
}