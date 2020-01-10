package com.depromeet.boiledegg.common.infrastructure.security;

import com.depromeet.boiledegg.user.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2Service customOAuth2Service;

    private final AuthFailureHandler authFailureHandler;

    private final AuthEntryPoint authEntryPoint;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        final var admin = Role.ADMIN.toString();
        final var user = Role.USER.toString();

        http.csrf().disable()
                .cors().disable()
                .headers().frameOptions().disable();

        http.authorizeRequests()

                // Public
                .antMatchers(
                        "/",
                        "/current-time-millis",
                        "/health", // For health check
                        "/h2-console/**", // For h2 database
                        "/docs/**", // For document

                        "/public"
                )
                .permitAll()

                // Only Admin
                .antMatchers("/only-admin")
                .hasRole(admin)

                // Only User
                .antMatchers(
                        "/books",
                        "/only-user"
                )
                .hasRole(user)

                // Admin Or User
                .antMatchers(
                        "/me",
                        "/normal"
                )
                .hasAnyRole(
                        admin,
                        user
                )

                // Books
                .antMatchers(
                        HttpMethod.POST,
                        "/books"
                )
                .hasRole(user)
                .antMatchers(
                        HttpMethod.PUT,
                        "/books"
                )
                .hasRole(user)
                .antMatchers(
                        HttpMethod.DELETE,
                        "/books"
                )
                .hasRole(user)
                .antMatchers(
                        HttpMethod.GET,
                        "/books"
                )
                .permitAll()

                // Bookstores
                .antMatchers(
                        HttpMethod.POST,
                        "/bookstores"
                )
                .hasRole(user)
                .antMatchers(
                        HttpMethod.PUT,
                        "/bookstores"
                )
                .hasRole(user)
                .antMatchers(
                        HttpMethod.DELETE,
                        "/bookstores"
                )
                .hasRole(user)
                .antMatchers(
                        HttpMethod.GET,
                        "/bookstores"
                )
                .permitAll()

                .anyRequest().authenticated()
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2Service)
                .and()
                .failureHandler(authFailureHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
        ;
    }
}
