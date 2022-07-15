package com.kisnahc.blog.controller;


import com.kisnahc.blog.dto.request.CreatePostRequest;
import com.kisnahc.blog.dto.request.PostSearchCondition;
import com.kisnahc.blog.dto.request.UpdatePostRequest;
import com.kisnahc.blog.dto.response.CreatePostResponse;
import com.kisnahc.blog.dto.response.ApiResponse;
import com.kisnahc.blog.dto.response.PostResponse;
import com.kisnahc.blog.dto.response.UpdatePostResponse;
import com.kisnahc.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    /**
     * 게시글 생성.
     * @param request
     * @return
     */
    @PostMapping("/api/posts")
    public ApiResponse<CreatePostResponse> createPost(@RequestBody @Valid CreatePostRequest request) {
        return postService.create(request);
    }

    /**
     * 게시글 단건 조회.
     * @param postId
     * @return
     */
    @GetMapping("/api/posts/{postId}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long postId) {
        postService.updateViewCount(postId); // 조회수 증가.
        return postService.get(postId);
    }

    /**
     * 게시글 전체 조회.
     * @return
     */
    @GetMapping("/api/posts")
    public ApiResponse<List<PostResponse>> getPosts(@ModelAttribute PostSearchCondition condition) {
        return postService.getList(condition);
    }

    /**
     * 게시글 수정.
     * @param postId
     * @param request
     * @return
     */
    @PutMapping("/api/posts/{postId}")
    public ApiResponse<UpdatePostResponse> updatePost(@PathVariable Long postId, @RequestBody @Valid UpdatePostRequest request) {
        return postService.update(postId, request);
    }

    /**
     * 게시글 삭제.
     * @param postId
     */
    @DeleteMapping("/api/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.delete(postId);
    }

}
