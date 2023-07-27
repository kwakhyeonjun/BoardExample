package com.nts.board.repository;

import com.nts.board.domain.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nts.board.domain.QBoard.board;

@Repository
@RequiredArgsConstructor
public class SearchQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Board> findBoardContains(String title, String content, String writer, String hashtag) {
        return queryFactory
                .selectFrom(board)
                .where(containsTitle(title), containsContent(content), containsWriter(writer), containsHashtag(hashtag))
                .fetch();
    }

    private BooleanExpression containsTitle(String title) {
        if(StringUtils.isNullOrEmpty(title)) return null;
        return board.title.contains(title);
    }

    private BooleanExpression containsContent(String content) {
        if(StringUtils.isNullOrEmpty(content)) return null;
        return board.content.contains(content);
    }

    private BooleanExpression containsWriter(String writer) {
        if(StringUtils.isNullOrEmpty(writer)) return null;
        return board.writer.contains(writer);
    }

    private BooleanExpression containsHashtag(String hashtag) {
        if(StringUtils.isNullOrEmpty(hashtag)) return null;
        return board.hashtag.contains(hashtag);
    }
}
