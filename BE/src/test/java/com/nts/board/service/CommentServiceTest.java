package com.nts.board.service;

import com.nts.board.domain.Board;
import com.nts.board.domain.Comment;
import com.nts.board.exception.BoardException;
import com.nts.board.exception.CommentException;
import com.nts.board.repository.BoardRepository;
import com.nts.board.repository.CommentRepository;
import com.nts.board.request.CommentRequest;
import com.nts.board.response.CommentResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.nts.board.exception.BoardException.BOARD_NOT_FOUND;
import static com.nts.board.exception.CommentException.COMMENT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    BoardRepository boardRepository;

    private Long id;
    private String content;
    private String writer;
    private String password;
    private Comment comment;
    private Board board;
    private String title;
    private Long boardId;

    @BeforeEach
    void setup() {
        id = 1L;
        content = "댓글내용";
        writer = "작성자";
        password = "password";
        title = "제목";

        board = Board.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .build();

        boardId = 1L;
    }

    @Nested
    @DisplayName("댓글 작성")
    class CreateComment {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("댓글이 작성되어 저장")
            void createCommentSuccess() {
                Comment comment = Comment.builder()
                        .content(content)
                        .writer(writer)
                        .password(password)
                        .board(board)
                        .build();
                when(commentRepository.save(any(Comment.class))).thenReturn(comment);
                when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
                CommentRequest request = new CommentRequest();
                request.setContent(content);
                request.setWriter(writer);
                request.setPassword(password);
                request.setBoardId(boardId);

                CommentService commentService = new CommentServiceImpl(commentRepository, boardRepository);
                CommentResponse response = commentService.createComment(request);

                assertThat(response.getContent()).isEqualTo(content);
                assertThat(response.getWriter()).isEqualTo(writer);
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("저장할 댓글이 잘못 들어온 경우")
            void createCommentFail() {
                when(commentRepository.save(any(Comment.class))).thenThrow(new CommentException(COMMENT_NOT_FOUND));
                when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
                CommentRequest request = new CommentRequest();
                request.setContent(content);
                request.setWriter(writer);
                request.setPassword(password);
                request.setBoardId(boardId);

                CommentService commentService = new CommentServiceImpl(commentRepository, boardRepository);
                Exception exception = assertThrows(CommentException.class, () -> commentService.createComment(request));

                assertThat(COMMENT_NOT_FOUND).isEqualTo(exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("댓글 목록")
    class CommentList {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("게시글에 대한 댓글 목록을 가져옴")
            void findCommentListSuccess() {
                List<Comment> commentList = new ArrayList<>();
                commentList.add(Comment.builder()
                        .id(id)
                        .content(content)
                        .board(board)
                        .writer(writer)
                        .build());
                commentList.add(Comment.builder()
                        .id(id)
                        .content(content)
                        .board(board)
                        .writer(writer)
                        .build());
                long boardId = 1L;
                when(commentRepository.findByBoard_Id(boardId)).thenReturn(commentList);

                CommentService commentService = new CommentServiceImpl(commentRepository, boardRepository);
                List<CommentResponse> commentResponseList = commentService.getCommentList(boardId);

                assertThat(commentList.size()).isEqualTo(commentResponseList.size());
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("선택한 게시물이 없는 경우")
            void findCommentListFail() {
                when(commentRepository.findByBoard_Id(boardId)).thenThrow(new BoardException(BOARD_NOT_FOUND));

                CommentService commentService = new CommentServiceImpl(commentRepository, boardRepository);
                Exception exception = assertThrows(BoardException.class, () -> commentService.getCommentList(boardId));

                assertThat(BOARD_NOT_FOUND).isEqualTo(exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("댓글 삭제")
    class DeleteComment {
        @Nested
        @DisplayName("정상 케이스")
        class SuccessCase {
            @Test
            @DisplayName("선택한 댓글의 deleted = true")
            void deleteCommentSuccess() {
                Comment comment = Comment.builder()
                        .content(content)
                        .writer(writer)
                        .password(password)
                        .board(board)
                        .build();
                when(commentRepository.findById(id)).thenReturn(Optional.of(comment));

                CommentService commentService = new CommentServiceImpl(commentRepository, boardRepository);
                CommentResponse response = commentService.deleteComment(id);

                assertThat(response.isDeleted()).isTrue();
            }
        }

        @Nested
        @DisplayName("비정상 케이스")
        class FailCase {
            @Test
            @DisplayName("해당 댓글이 없는 경우")
            void deleteCommentFail() {
                when(commentRepository.findById(id)).thenThrow(new CommentException(COMMENT_NOT_FOUND));

                CommentService commentService = new CommentServiceImpl(commentRepository, boardRepository);
                Exception exception = assertThrows(CommentException.class, () -> commentService.deleteComment(id));

                assertThat(exception.getMessage()).isEqualTo(COMMENT_NOT_FOUND);
            }
        }
    }
}
