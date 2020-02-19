package com.depromeet.boiledegg.user.representation.controller;

import com.depromeet.boiledegg.book.representation.BookResponse;
import com.depromeet.boiledegg.common.infrastructure.security.LoginUser;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import com.depromeet.boiledegg.common.representation.PageRequest;
import com.depromeet.boiledegg.transaction.representation.TransactionResponse;
import com.depromeet.boiledegg.user.application.UserBookService;
import com.depromeet.boiledegg.user.application.UserService;
import com.depromeet.boiledegg.user.application.UserTransactionService;
import com.depromeet.boiledegg.user.domain.entity.User;
import com.depromeet.boiledegg.user.exception.UserNotFoundException;
import com.depromeet.boiledegg.user.representation.UserResponse;
import com.depromeet.boiledegg.user.representation.UserUpdateRequest;
import com.depromeet.boiledegg.user.representation.assembler.UserResponseAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(UserController.BASE_PATH)
public class UserController {

    public static final String BASE_PATH = "/users";

    private final UserService userService;

    private final UserBookService userBookService;

    private final UserTransactionService userTransactionService;

    private final UserResponseAssembler assembler;

    @Secured("ROLE_USER")
    @GetMapping("/me")
    public SessionUser findMe(@LoginUser final SessionUser user) {
        return user;
    }

    @Secured("ROLE_USER")
    @PutMapping("/me")
    public UserResponse updateMe(
            @LoginUser final SessionUser sessionUser,
            @RequestBody final UserUpdateRequest request
    ) {
        final var user = userService.update(
                sessionUser,
                User.builder()
                        .name(request.getName())
                        .picture(request.getPicture())
                        .nickname(request.getNickname())
                        .build()
        );

        return assembler.mapFrom(user);
    }

    @GetMapping("/{userId}")
    UserResponse findById(@PathVariable final Long userId) {
        return userService.findById(userId)
                .map(assembler::mapFrom)
                .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping("/{userId}/books")
    Page<BookResponse> findAllBook(
            @PathVariable final Long userId,
            final PageRequest pageRequest
    ) {
        return userBookService.findByOwner(
                userId,
                pageRequest
        );
    }

    @GetMapping("/{userId}/transactions")
    Page<TransactionResponse> findAllTransaction(
            @PathVariable final Long userId,
            final PageRequest pageRequest
    ) {
        return userTransactionService.findByBookOwnerOrOwner(
                userId,
                pageRequest
        );
    }
}
