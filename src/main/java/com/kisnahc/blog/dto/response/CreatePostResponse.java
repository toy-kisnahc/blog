package com.kisnahc.blog.dto.response;

import com.kisnahc.blog.domain.Post;
import lombok.Data;


@Data
public class CreatePostResponse {

    private Long postId;
    private String title;
    private String content;
    private String author;

    public CreatePostResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
    }
}
