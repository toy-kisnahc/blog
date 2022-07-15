package com.kisnahc.blog.dto.response;

import com.kisnahc.blog.domain.Post;
import lombok.Data;

@Data
public class UpdatePostResponse {

    private Long postId;
    private String title;
    private String content;

    public UpdatePostResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
