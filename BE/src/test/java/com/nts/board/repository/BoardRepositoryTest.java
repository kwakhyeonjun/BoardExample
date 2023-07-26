package com.nts.board.repository;

import com.nts.board.domain.Board;
import com.nts.board.exception.BoardException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.nts.board.exception.BoardException.BOARD_NOT_FOUND;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    @DisplayName("게시물 저장 및 호출")
    void save() {

        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .writer("작성자").build();

        Board savedBoard = boardRepository.save(board);

        Optional<Board> findBoard = boardRepository.findById(savedBoard.getId());

        assertThat(findBoard.orElse(null)).isEqualTo(savedBoard);
    }

    @Test
    @DisplayName("게시물 수정")
    void update() {
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .writer("작성자").build();

        Board saveBoard = boardRepository.save(board);
        Board prevBoard = boardRepository.findById(saveBoard.getId()).orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));

        prevBoard.update("새제목", "새내용");

        Board updateBoard = boardRepository.findById(board.getId()).orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
        assertThat(prevBoard.getTitle()).isEqualTo(updateBoard.getTitle());
    }

    @Test
    @DisplayName("게시물 삭제")
    void deleteBoard() {
        String title = "제목";
        String writer = "작성자";
        String content = "내용";
        String password = "password";

        Board board = Board.builder()
                .title(title)
                .writer(writer)
                .content(content)
                .password(password)
                .build();

        Board saveBoard = boardRepository.save(board);

        boardRepository.deleteById(saveBoard.getId());

        Exception exception = assertThrows(BoardException.class, () -> {
            boardRepository.findById(saveBoard.getId()).orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
        });

        assertThat(BOARD_NOT_FOUND).isEqualTo(exception.getMessage());
    }
}
