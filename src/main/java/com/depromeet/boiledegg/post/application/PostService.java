package com.depromeet.boiledegg.post.application;

import com.depromeet.boiledegg.post.domain.PostBody;
import com.depromeet.boiledegg.post.domain.PostRepository;
import com.depromeet.boiledegg.post.domain.entity.Post;
import com.depromeet.boiledegg.post.representation.PostSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

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

    @Transactional(readOnly = true)
    public Optional<Post> findById(final Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return repository.findAll();
    }
}
