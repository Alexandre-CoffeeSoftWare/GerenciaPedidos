package org.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*Dados fictícios para reduzir o tempo de implementação*/
        if ("admin".equals(username)) {
            return User.withUsername(username)
                    .password("{noop}password")
                    .roles("USER")
                    .build();
        }

        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}

