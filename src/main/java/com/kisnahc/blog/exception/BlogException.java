package com.kisnahc.blog.exception;

public abstract class BlogException extends RuntimeException {

    public BlogException(String message) {
        super(message);
    }

    public abstract int getStatusCode();
}
