package com.kisnahc.blog.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostSearchCondition {
    @Builder.Default
    private Integer offset = 1;

    @Builder.Default
    private int size = 10;
}
