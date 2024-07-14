package com.ghtk.social_network.config;

import com.ghtk.social_network.domain.model.User;
import com.ghtk.social_network.domain.port.api.UserServicePort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("userDetailsService")
public class CustomUserDetails implements UserDetailsService {

    private final UserServicePort userService;

    public CustomUserDetails(UserServicePort userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.findUserByEmail(username);
        System.out.println(user);

        if (user == null) {
            throw new UsernameNotFoundException("Username/password khong hop le");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

}
