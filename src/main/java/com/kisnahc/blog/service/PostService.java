package com.kisnahc.blog.service;

import com.kisnahc.blog.dto.request.CreatePostRequest;
import com.kisnahc.blog.dto.request.PostSearchCondition;
import com.kisnahc.blog.dto.request.UpdatePostRequest;
import com.kisnahc.blog.dto.response.CreatePostResponse;
import com.kisnahc.blog.dto.response.ApiResponse;
import com.kisnahc.blog.dto.response.PostResponse;
import com.kisnahc.blog.dto.response.UpdatePostResponse;

import java.util.List;

public interface PostService {
    ApiResponse<List<PostResponse>> getList(PostSearchCondition condition);

    ApiResponse<PostResponse> get(Long postId);

    ApiResponse<CreatePostResponse> create(CreatePostRequest request);

    ApiResponse<UpdatePostResponse> update(Long postId, UpdatePostRequest request);

    void delete(Long postId);

    void updateViewCount(Long postId);
}
