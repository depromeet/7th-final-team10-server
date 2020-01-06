package com.depromeet.boiledegg.post.application;

import com.depromeet.boiledegg.post.domain.PostBody;
import com.depromeet.boiledegg.post.domain.PostRepository;
import com.depromeet.boiledegg.post.domain.entity.Post;
import com.depromeet.boiledegg.post.representation.PostSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class PostService {

    private final PostRepository repository;

    public Post save(final PostSaveRequest request) {
        final var postBody = PostBody.builder()
                .content(request.getContent())
                .title(request.getTitle())
                .build();

        return repository.save(Post.builder()
                .postBody(postBody)
                .build());
    }

    public Optional<Post> findById(final Long id) {
        return repository.findById(id);
    }

    public List<Post> findAll() {
        return repository.findAll();
    }
}