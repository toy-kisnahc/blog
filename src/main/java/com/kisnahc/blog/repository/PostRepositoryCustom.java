package com.kisnahc.blog.repository;

import com.kisnahc.blog.domain.Post;
import com.kisnahc.blog.dto.request.PostSearchCondition;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findAll(PostSearchCondition cond);
}
