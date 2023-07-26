package com.nts.board.repository;

import com.nts.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Long countByDeletedIsFalse();
}
