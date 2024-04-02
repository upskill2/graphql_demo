package com.course.graphql.security;

import com.course.graphql.datasource.UserRole;
import com.course.graphql.datasource.entity.Users;
import com.course.graphql.datasource.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public class ProblemsAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository;

    public ProblemsAuthenticationProvider (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate (Authentication auth) {
        var user = userRepository.findUsersByUsernameIgnoreCase (auth.getCredentials ().toString ())
                .orElse (new Users ());

        return new UsernamePasswordAuthenticationToken (user, auth.getCredentials ().toString (),
                getAuthorities (user.getUserRole ()));
    }

    @Override
    public boolean supports (Class<?> authentication) {
        return authentication.equals (UsernamePasswordAuthenticationToken.class);
    }

    private Collection<? extends GrantedAuthority> getAuthorities (UserRole userRole) {
        var authorities = new ArrayList<GrantedAuthority> ();

        if (StringUtils.isNotBlank (userRole.toString ())) {
            authorities.add (new SimpleGrantedAuthority (userRole.toString ()));
        }

        return authorities;
    }

}
