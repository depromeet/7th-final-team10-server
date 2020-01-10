package com.depromeet.boiledegg.common.infrastructure.security;

import com.depromeet.boiledegg.user.application.UserService;
import com.depromeet.boiledegg.user.domain.Role;
import com.depromeet.boiledegg.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@Service
public final class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;

    private final AuthorityFactory authorityFactory;

    private final HttpSession httpSession;

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final var oAuth2User = delegate.loadUser(userRequest);

        final var registration = userRequest.getClientRegistration();
        final var registrationId = registration.getRegistrationId();
        final var userNameAttributeName = registration.getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        final var provider = Provider.of(registrationId);
        final var attributes = provider.parseOAuthAttributes(
                userNameAttributeName,
                oAuth2User.getAttributes()
        );

        final var user = createOrUpdate(attributes);

        final var sessionUser = SessionUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .picture(user.getPicture())
                .build();
        httpSession.setAttribute(
                SessionUser.ATTRIBUTE_KEY,
                sessionUser
        );

        return new DefaultOAuth2User(
                authorityFactory.create(user.getRole()),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private User createOrUpdate(final OAuthAttributes oAuthAttributes) {
        final var user = userService.findByEmail(oAuthAttributes.getEmail())
                .map(foundUser -> foundUser.updateName(oAuthAttributes.getName()))
                .map(foundUser -> foundUser.updatePicture(oAuthAttributes.getPicture()))
                .orElseGet(() -> User.builder()
                        .authProvider(oAuthAttributes.getAuthProvider())
                        .email(oAuthAttributes.getEmail())
                        .name(oAuthAttributes.getName())
                        .picture(oAuthAttributes.getPicture())
                        .role(Role.USER)
                        .build());

        return userService.save(user);
    }
}
