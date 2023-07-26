package com.nts.board.service;

import com.nts.board.repository.BoardRepository;
import com.nts.board.repository.CommentRepository;
import com.nts.board.response.CountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountServiceImpl implements CountService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    @Override
    public CountResponse countBoardAndComment() {
        Long boardCount = boardRepository.countByDeletedIsFalse();
        Long commentCount = commentRepository.countByDeletedIsFalse();
        return CountResponse.from(boardCount, commentCount);
    }
}
