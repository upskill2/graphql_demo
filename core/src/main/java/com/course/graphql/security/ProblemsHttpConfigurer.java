package com.course.graphql.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class ProblemsHttpConfigurer extends AbstractHttpConfigurer<ProblemsHttpConfigurer, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        var authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(
                new ProblemsSecurityFilter(authenticationManager),
                UsernamePasswordAuthenticationFilter.class
        );
    }

    public static ProblemsHttpConfigurer newInstance() {
        return new ProblemsHttpConfigurer ();
    }

}
