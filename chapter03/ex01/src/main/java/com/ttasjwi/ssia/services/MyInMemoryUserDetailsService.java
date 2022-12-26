package com.ttasjwi.ssia.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MyInMemoryUserDetailsService implements UserDetailsService {

    private final List<UserDetails> users = new ArrayList<>();

    public MyInMemoryUserDetailsService(List<UserDetails> users) {
        this.users.addAll(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
