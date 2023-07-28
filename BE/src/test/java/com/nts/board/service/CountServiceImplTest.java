package com.nts.board.service;

import com.nts.board.repository.BoardRepository;
import com.nts.board.repository.CommentRepository;
import com.nts.board.response.CountResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CountServiceImplTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    BoardRepository boardRepository;

    @Nested
    @DisplayName("메인 페이지 게시글 댓글 전체 수")
    class MainCount {
        @Test
        @DisplayName("게시글 및 댓글 전체 수 세기")
        void count() {
            when(boardRepository.countByDeletedIsFalse()).thenReturn(1L);
            when(commentRepository.countByDeletedIsFalse()).thenReturn(1L);

            CountService countService = new CountServiceImpl(boardRepository, commentRepository);
            CountResponse response = countService.countBoardAndComment();

            assertThat(response.getBoardCount()).isEqualTo(1L);
            assertThat(response.getCommentCount()).isEqualTo(1L);
        }
    }
}