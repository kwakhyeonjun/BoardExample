package com.nts.board.api;

import com.nts.board.response.CountResponse;
import com.nts.board.service.CountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CountController {

    private final CountService countService;

    @GetMapping("/count")
    public ResponseEntity<CountResponse> count() {
        CountResponse body = countService.countBoardAndComment();
        return ResponseEntity.ok().body(body);
    }
}
