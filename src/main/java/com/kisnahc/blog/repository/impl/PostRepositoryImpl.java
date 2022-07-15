package com.kisnahc.blog.repository.impl;

import com.kisnahc.blog.domain.Post;
import com.kisnahc.blog.dto.request.PostSearchCondition;
import com.kisnahc.blog.repository.PostRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.kisnahc.blog.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> findAll(PostSearchCondition cond) {
        return jpaQueryFactory.selectFrom(post)
                .offset((long) (cond.getOffset() - 1) * cond.getSize())
                .limit(cond.getSize())
                .orderBy(post.id.desc())
                .fetch();
    }
}
