package com.nts.board.api;

import com.nts.board.api.defaults.SearchType;
import com.nts.board.request.BoardRequest;
import com.nts.board.response.BoardResponse;
import com.nts.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 상세
    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable long boardId) {
        BoardResponse body = boardService.findBoard(boardId);
        return ResponseEntity.ok().body(body);
    }

    // 게시글 등록
    @PostMapping("/board")
    public ResponseEntity<BoardResponse> createBoard(@RequestBody BoardRequest request) {
        BoardResponse body = boardService.saveBoard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    // 게시물 수정
    @PutMapping("/board/{boardId}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable long boardId, @RequestBody BoardRequest request) {
        BoardResponse body = boardService.updateBoard(boardId, request);
        return ResponseEntity.ok().body(body);
    }

    // 게시물 삭제
    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<BoardResponse> deleteBoard(@PathVariable long boardId, @RequestBody BoardRequest request) {
        BoardResponse body = boardService.deleteBoard(boardId, request);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/")
    public ResponseEntity<List<BoardResponse>> findBoardAll() {
        List<BoardResponse> body = boardService.findBoardList();
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BoardResponse>> searchBoard(@RequestParam("t") String type, @RequestParam("q") String keyword) {
        String title = null;
        String content = null;
        String writer = null;
        String hashtag = null;

        if(SearchType.TITLE.toString().equals(type)) {
            title = keyword;
        } else if(SearchType.CONTENT.toString().equals(type)) {
            content = keyword;
        } else if(SearchType.WRITER.toString().equals(type)) {
            writer = keyword;
        } else if(SearchType.HASHTAG.toString().equals(keyword)) {
            hashtag = keyword;
        }

        List<BoardResponse> body = boardService.searchBoardList(title, content, writer, hashtag);
        return ResponseEntity.ok().body(body);
    }
}
