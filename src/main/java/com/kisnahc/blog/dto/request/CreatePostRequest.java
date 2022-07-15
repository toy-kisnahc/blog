package com.kisnahc.blog.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class CreatePostRequest {

    @Size(max = 50, message = "50자 이하로 작성해 주세요.")
    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;

    @Lob
    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;

    private String author;

    @Builder
    public CreatePostRequest(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
