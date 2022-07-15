package com.kisnahc.blog.dto.response;

import com.kisnahc.blog.domain.Post;
import lombok.Builder;
import lombok.Data;

@Data
public class PostResponse {

    private Long postId;
    private String title;
    private String content;
    private String author;
    private int view;

    @Builder
    public PostResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.view = post.getView();
    }

}
