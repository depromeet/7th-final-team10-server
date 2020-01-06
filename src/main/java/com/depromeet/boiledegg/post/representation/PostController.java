package com.depromeet.boiledegg.post.representation;

import com.depromeet.boiledegg.post.application.PostService;
import com.depromeet.boiledegg.post.domain.PostBody;
import com.depromeet.boiledegg.post.domain.entity.Post;
import com.depromeet.boiledegg.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@RequiredArgsConstructor
@RestController
@RequestMapping(PostController.BASE_PATH)
final class PostController {

    static final String BASE_PATH = "/posts";

    private final PostService postService;

    private final PostResponseAssembler postResponseAssembler;

    @PostMapping
    ResponseEntity<PostResponse> save(@RequestBody final PostSaveRequest request) {
        final var post = postService.save(request);

        final var uri = URI.create(BASE_PATH + "/" + post.getId());
        return ResponseEntity.created(uri)
                .body(postResponseAssembler.mapFrom(post));
    }

    @GetMapping
    List<PostResponse> findAll() {
        return postService.findAll()
                .stream()
                .map(postResponseAssembler::mapFrom)
                .collect(toUnmodifiableList());
    }

    @GetMapping("/{id}")
    PostResponse findById(@PathVariable final Long id) {
        return postService.findById(id)
                .map(postResponseAssembler::mapFrom)
                .orElseThrow(PostNotFoundException::new);
    }
}
