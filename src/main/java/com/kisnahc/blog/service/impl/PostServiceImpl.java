package com.kisnahc.blog.service.impl;

import com.kisnahc.blog.domain.Post;
import com.kisnahc.blog.dto.request.CreatePostRequest;
import com.kisnahc.blog.dto.request.PostSearchCondition;
import com.kisnahc.blog.dto.request.UpdatePostRequest;
import com.kisnahc.blog.dto.response.CreatePostResponse;
import com.kisnahc.blog.dto.response.ApiResponse;
import com.kisnahc.blog.dto.response.PostResponse;
import com.kisnahc.blog.dto.response.UpdatePostResponse;
import com.kisnahc.blog.exception.PostNotPoundException;
import com.kisnahc.blog.repository.PostRepository;
import com.kisnahc.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    /**
     * 게시글 생성.
     * @param request
     * @return ApiResponse<CreatePostResponse>
     */
    @Transactional
    @Override
    public ApiResponse<CreatePostResponse> create(CreatePostRequest request) {
        Post createPost = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(request.getAuthor())
                .build();

        Post savePost = postRepository.save(createPost);

        return new ApiResponse<>(SC_OK, new CreatePostResponse(savePost));
    }

    /**
     * 게시글 단건 조회.
     * @param postId
     * @return
     */
    @Override
    public ApiResponse<PostResponse> get(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotPoundException::new);
        return new ApiResponse<>(SC_OK, new PostResponse(post));
    }

    /**
     * 게시글 수정.
     * @param postId
     * @param request
     * @return ApiResponse<UpdatePostResponse>
     */
    @Transactional
    @Override
    public ApiResponse<UpdatePostResponse> update(Long postId, UpdatePostRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotPoundException::new);
        post.updatePost(request);
        return new ApiResponse<>(SC_OK, new UpdatePostResponse(post));
    }

    /**
     * 게시글 삭제
     * @param postId
     */
    @Override
    public void delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotPoundException::new);
        postRepository.delete(post);
    }

    /**
     * 조회수 증가.
     * @param postId
     * @return
     */
    @Override
    public void updateViewCount(Long postId) {
        postRepository.updateView(postId);
    }

    @Override
    public ApiResponse<List<PostResponse>> getList(PostSearchCondition condition) {
        List<Post> postList = postRepository.findAll(condition);

        List<PostResponse> result = postList
                .stream()
                .map(PostResponse::new)
                .toList();

        return new ApiResponse<>(SC_OK, result);
    }
}
