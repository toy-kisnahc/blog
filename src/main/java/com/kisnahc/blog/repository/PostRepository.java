package com.kisnahc.blog.repository;

import com.kisnahc.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    @Modifying
    @Query("update Post p set p.view = p.view + 1 where p.id = :id")
    void updateView(@Param("id") Long postId);

}
