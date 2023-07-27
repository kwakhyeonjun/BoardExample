package com.nts.board.repository;

import com.nts.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Long countByDeletedIsFalse();
}
