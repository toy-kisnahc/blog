package com.kisnahc.blog.domain;

import com.kisnahc.blog.dto.request.UpdatePostRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int view;


    @Builder
    public Post(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    /*
        게시글 수정 메서드
     */
    public void updatePost(UpdatePostRequest request) {
        title = request.getTitle() != null ? request.getTitle() : getTitle();
        content = request.getContent() != null ? request.getContent() : getContent();
    }
}
