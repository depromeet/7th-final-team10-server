package com.depromeet.boiledegg.post.representation;

import com.depromeet.boiledegg.post.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
final class PostResponseAssembler {

    private final ModelMapper mapper;

    PostResponse mapFrom(final Post post) {
        return mapper.map(post, PostResponse.class);
    }
}
