package com.kisnahc.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kisnahc.blog.domain.Post;
import com.kisnahc.blog.dto.request.CreatePostRequest;
import com.kisnahc.blog.dto.request.UpdatePostRequest;
import com.kisnahc.blog.exception.PostNotPoundException;
import com.kisnahc.blog.repository.PostRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @DisplayName("저장 성공 테스트")
    @Test
    void success_save_test() throws Exception {
        // given
        Post post = getPost();
        Post savePost = postRepository.save(post);
        String json = toJson(savePost);

        // expected
        mockMvc.perform(post("/api/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("title 필드 @NotBlank 검증 테스트")
    @Test()
    void validation_test() throws Exception {
        // given
        CreatePostRequest request = CreatePostRequest.builder()
                .title("")
                .content("내용")
                .author("작성자")
                .build();

        Post post = Post.builder()
                .author(request.getAuthor())
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        Post savePost = postRepository.save(post);

        String json = toJson(savePost);

        // expected
        MvcResult result = mockMvc.perform(post("/api/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        assertThat(Objects.requireNonNull(result.getResolvedException()).getClass()).isEqualTo(MethodArgumentNotValidException.class);
    }

    @DisplayName("게시글 단건 조회 테스트")
    @Test
    void get_test() throws Exception {
        // given
        Post post = getPost();

        Post savePost = postRepository.save(post);

        Post findPost = postRepository.findById(savePost.getId()).orElseThrow(PostNotPoundException::new);

        String json = toJson(findPost);

        // expected
        mockMvc.perform(get("/api/posts/{postId}", findPost.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("게시글 단건 조회 예외처리 테스트")
    @Test
    void get_ex_test() throws Exception {
        // given
        Post post = getPost();

        Post savePost = postRepository.save(post);

        Post findPost = postRepository.findById(savePost.getId()).orElseThrow(PostNotPoundException::new);

        String json = toJson(findPost);

        // expected
        MvcResult result = mockMvc.perform(get("/api/posts/{postId}", findPost.getId() + 1)
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();

        assertThat(Objects.requireNonNull(result.getResolvedException()).getClass()).isEqualTo(PostNotPoundException.class);
    }

    @Transactional
    @DisplayName("게시글 수정 테스트")
    @Test
    void update_post_test() throws Exception {
        // given
        Post post = getPost();

        Post savePost = postRepository.save(post);

        Post findPost = postRepository.findById(savePost.getId()).orElseThrow(PostNotPoundException::new);

        UpdatePostRequest updatePostRequest = UpdatePostRequest.builder()
                .title("변경된 타이틀 입니다.")
                .content("변경된 내용 입니다.")
                .build();

        findPost.updatePost(updatePostRequest); // 게시글 수정.

        String json = toJson(findPost);

        // expected
        mockMvc.perform(put("/api/posts/{postId}", findPost.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("게시글 삭제 테스트")
    @Test
    void delete_post_test() throws Exception {
        // given
        Post post = getPost();

        Post savePost = postRepository.save(post);

        Post findPost = postRepository.findById(savePost.getId()).orElseThrow(PostNotPoundException::new);

        String json = toJson(findPost);

        // expected
        mockMvc.perform(delete("/api/posts/{postId}", findPost.getId())
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    private String toJson(Post post) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(post);
    }

    private Post getPost() {
        CreatePostRequest request = CreatePostRequest.builder()
                .title("제목")
                .content("내용")
                .author("작성자")
                .build();

        return Post.builder()
                .author(request.getAuthor())
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

}