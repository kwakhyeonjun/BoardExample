package com.nts.board.repository;

import com.nts.board.domain.Board;
import com.nts.board.exception.BoardException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

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

        String title = "제목";
        String content = "내용";
        String writer = "작성자";
        String hashtag = "해시,태그";

        Board board = Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .hashtag(hashtag)
                .build();

        Board savedBoard = boardRepository.save(board);
        Board findBoard = boardRepository.findById(savedBoard.getId()).orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));

        assertThat(findBoard).isEqualTo(savedBoard);
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

    @Test
    @DisplayName("게시물 목록")
    void findBoardList() {
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

        List<Board> boardList = boardRepository.findAll();

        assertThat(boardList.size()).isEqualTo(2);
    }
}
