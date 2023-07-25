package com.nts.board.api;

import com.nts.board.request.BoardSaveRequest;
import com.nts.board.response.BoardResponse;
import com.nts.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BoardResponse> setBoard(@RequestBody BoardSaveRequest request) {
        BoardResponse body = boardService.saveBoard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

}
