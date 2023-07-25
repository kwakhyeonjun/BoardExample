package com.nts.board.api;

import com.nts.board.domain.Board;
import com.nts.board.repository.BoardRepository;
import com.nts.board.response.BoardResponse;
import com.nts.board.service.BoardService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardControllerTest {

    @Autowired
    BoardController boardController;

    @Autowired
    BoardRepository boardRepository;

    @Test
    void getBoard() {
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .writer("작성자").build();

        Board savedBoard = boardRepository.save(board);

        ResponseEntity<BoardResponse> response = boardController.getBoard(savedBoard.getId());

        assertThat(Objects.requireNonNull(response.getBody()).getTitle()).isEqualTo(savedBoard.getTitle());
    }
}