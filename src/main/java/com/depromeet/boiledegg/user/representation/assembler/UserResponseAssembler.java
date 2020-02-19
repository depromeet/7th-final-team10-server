package com.depromeet.boiledegg.user.representation.assembler;

import com.depromeet.boiledegg.user.domain.entity.User;
import com.depromeet.boiledegg.user.representation.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class UserResponseAssembler {

    private final ModelMapper mapper;

    public UserResponse mapFrom(final User user) {
        return mapper.map(user, UserResponse.class);
    }
}
