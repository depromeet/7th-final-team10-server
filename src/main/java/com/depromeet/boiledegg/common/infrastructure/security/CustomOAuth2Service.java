package com.depromeet.boiledegg.common.infrastructure.security;

import com.depromeet.boiledegg.account.application.AccountService;
import com.depromeet.boiledegg.account.domain.Role;
import com.depromeet.boiledegg.account.domain.entity.Account;
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

    private final AccountService accountService;

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

        final var account = createOrUpdate(attributes);

        final var sessionUser = SessionUser.builder()
                .id(account.getId())
                .email(account.getEmail())
                .name(account.getName())
                .picture(account.getPicture())
                .build();
        httpSession.setAttribute(
                SessionUser.ATTRIBUTE_KEY,
                sessionUser
        );

        return new DefaultOAuth2User(
                authorityFactory.create(account.getRole()),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private Account createOrUpdate(final OAuthAttributes oAuthAttributes) {
        final var account = accountService.findByEmail(oAuthAttributes.getEmail())
                .map(foundAccount -> foundAccount.updateName(oAuthAttributes.getName()))
                .map(foundAccount -> foundAccount.updatePicture(oAuthAttributes.getPicture()))
                .orElseGet(() -> Account.builder()
                        .authProvider(oAuthAttributes.getAuthProvider())
                        .email(oAuthAttributes.getEmail())
                        .name(oAuthAttributes.getName())
                        .picture(oAuthAttributes.getPicture())
                        .role(Role.USER)
                        .build());

        return accountService.save(account);
    }
}
