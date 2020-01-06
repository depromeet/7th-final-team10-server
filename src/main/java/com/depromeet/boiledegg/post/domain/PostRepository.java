package com.depromeet.boiledegg.post.domain;

import com.depromeet.boiledegg.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
