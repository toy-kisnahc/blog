package com.kisnahc.blog.exception;

public class PostNotPoundException extends BlogException{

    private static final String MESSAGE = "게시글을 찾을 수 없습니다.";

    public PostNotPoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
