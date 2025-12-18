package com.example.demo.security;

import com.example.demo.entity.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserProfileRepository userRepo;

    public CustomUserDetailsService(UserProfileRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserProfile user = userRepo.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (Boolean.FALSE.equals(user.getActive())) {
            throw new UsernameNotFoundException("User inactive");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getPassword(),
                Collections.singleton(() -> "ROLE_" + user.getRole())
        );
    }
}
