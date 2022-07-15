package com.kisnahc.blog.service;

import com.kisnahc.blog.domain.Post;
import com.kisnahc.blog.dto.request.PostSearchCondition;
import com.kisnahc.blog.dto.request.UpdatePostRequest;
import com.kisnahc.blog.dto.response.ApiResponse;
import com.kisnahc.blog.dto.response.PostResponse;
import com.kisnahc.blog.exception.PostNotPoundException;
import com.kisnahc.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @BeforeEach
    void init() {
        postRepository.deleteAll();
    }

    @DisplayName("저장 성공 테스트")
    @Test
    void save_success_test() {
        // given
        Post createPost = getPost();

        // when
        Post savedPost = postRepository.save(createPost);

        // then
        assertThat(savedPost.getAuthor()).isEqualTo(createPost.getAuthor());
        assertThat(savedPost.getTitle()).isEqualTo(createPost.getTitle());
        assertThat(savedPost.getContent()).isEqualTo(createPost.getContent());
    }

    @DisplayName("게시글 단건 조회 테스트")
    @Test
    void get_post_test() {
        // given
        Post createPost = getPost();
        Post savedPost = postRepository.save(createPost);

        // when
        ApiResponse<PostResponse> postResponseApiResponse = postService.get(savedPost.getId());

        // then
        assertThat(postResponseApiResponse.getData().getPostId()).isEqualTo(savedPost.getId());
        assertThat(postResponseApiResponse.getData().getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(postResponseApiResponse.getData().getContent()).isEqualTo(savedPost.getContent());

    }

    @DisplayName("게시글 단건 조회 예외처리 테스트")
    @Test
    void get_post_ex_test() {
        // given
        Post createPost = getPost();
        Post savedPost = postRepository.save(createPost);

        // then
        PostNotPoundException postNotPoundException = assertThrows(PostNotPoundException.class, () -> postService.get(savedPost.getId() + 1));
        assertThat(postNotPoundException.getMessage()).isEqualTo("게시글을 찾을 수 없습니다.");
        assertThat(postNotPoundException.getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @DisplayName("게시글 수정 테스트")
    @Test
    void update_post_test() {
        // given
        Post createPost = getPost();
        Post savedPost = postRepository.save(createPost);

        // when
        Post findPost = postRepository.findById(savedPost.getId()).orElseThrow(PostNotPoundException::new);

        UpdatePostRequest updatePostRequest = UpdatePostRequest.builder()
                .title("변경된 타이틀 입니다.")
                .content("변경된 내용 입니다.")
                .build();

        postService.update(findPost.getId(), updatePostRequest);

        // then
        assertThat(savedPost.getId()).isEqualTo(findPost.getId());
        assertThat(findPost.getTitle()).isEqualTo("변경된 타이틀 입니다.");
        assertThat(findPost.getContent()).isEqualTo("변경된 내용 입니다.");
    }

    @DisplayName("게시글 삭제 테스트")
    @Test
    void delete_post_test() {
        // given
        Post createPost = getPost();
        Post savedPost = postRepository.save(createPost);
        Post findPost = postRepository.findById(savedPost.getId()).orElseThrow(PostNotPoundException::new);

        // when
        postService.delete(findPost.getId());

        // then
        assertThat(postRepository.count()).isEqualTo(0);
    }

    @DisplayName("게시글 전체 조회")
    @Test
    void get_all_post_test() {
        // given
        List<Post> collect = IntStream.range(1, 51)
                .mapToObj(i -> Post.builder()
                        .author("작성자 " + i)
                        .title("제목" + i)
                        .content("내용 " + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(collect);

        PostSearchCondition searchCondition = PostSearchCondition.builder()
                .size(10) // null -> Default = 10
                .offset(1)
                .build();

        // when
        ApiResponse<List<PostResponse>> postList = postService.getList(searchCondition);

        // then
        assertThat(postRepository.count()).isEqualTo(50);
        assertThat(postList.getData().size()).isEqualTo(10);
        assertThat(postList.getData().get(0).getAuthor()).isEqualTo("작성자 50");
        assertThat(postList.getData().get(9).getAuthor()).isEqualTo("작성자 41");
    }

    private Post getPost() {
        return Post.builder()
                .author("memberA")
                .title("타이틀 입니다.")
                .content("내용 입니다.")
                .build();
    }
}