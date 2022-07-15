package com.kisnahc.blog;

import com.kisnahc.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@RequiredArgsConstructor
@EnableJpaAuditing
@SpringBootApplication
public class BlogApplication {

	private final PostRepository postRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
