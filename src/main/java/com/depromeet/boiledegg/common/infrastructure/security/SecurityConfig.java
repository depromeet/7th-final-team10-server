package com.depromeet.boiledegg.common.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true, order = Ordered.HIGHEST_PRECEDENCE)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2Service customOAuth2Service;

    private final AuthFailureHandler authFailureHandler;

    private final AuthEntryPoint authEntryPoint;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable()
                .headers().frameOptions().disable();

        http.authorizeRequests()
                .anyRequest()
                .permitAll()

                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2Service)
                .and()
                .failureHandler(authFailureHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint);

        /* 개별 권한으로 변경
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
                .antMatchers(
                        HttpMethod.GET,
                        "/books/**"
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

                // Transactions

                .anyRequest().permitAll()
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2Service)
                .and()
                .failureHandler(authFailureHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
        ;
        */
    }
}
