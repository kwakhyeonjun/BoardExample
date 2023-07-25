package com.nts.board.service;

import com.nts.board.domain.Board;
import com.nts.board.repository.BoardRepository;
import com.nts.board.request.BoardSaveRequest;
import com.nts.board.response.BoardResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @DisplayName("게시물 저장 확인")
    @Test
    public void save() {
        BoardSaveRequest request = new BoardSaveRequest();
        request.setTitle("제목");
        request.setContent("내용");
        request.setWriter("작성자");
        request.setPassword("password");

        BoardResponse response = boardService.saveBoard(request);

        assertThat(response.getTitle()).isEqualTo(request.getTitle());
    }

    @DisplayName("게시물 호출 확인")
    @Test
    public void findBoard() {
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .writer("작성자").build();
        Board saveBoard = boardRepository.save(board);

        BoardResponse findBoard = boardService.findBoard(saveBoard.getId());

        assertThat(findBoard.getTitle()).isEqualTo(saveBoard.getTitle());
    }
}