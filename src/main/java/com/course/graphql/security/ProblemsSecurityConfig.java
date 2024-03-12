package com.course.graphql.security;

import com.course.graphql.datasource.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity (securedEnabled = true)
public class ProblemsSecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        var authProvider = new ProblemsAuthenticationProvider (userRepository);

        http.apply (ProblemsHttpConfigurer.newInstance ());
        http.authenticationProvider (authProvider);
        http.csrf ().disable ().authorizeHttpRequests (
                auth -> auth.anyRequest ().authenticated ()
        );

        return http.build ();
    }

}
