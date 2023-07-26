package com.nts.board.repository;

import com.nts.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByBoard_Id(long boardId);
}
