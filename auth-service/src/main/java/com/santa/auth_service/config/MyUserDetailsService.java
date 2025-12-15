package com.santa.auth_service.config;

import com.santa.auth_service.exception.UserNotFoundException;
import com.santa.auth_service.model.User;
import com.santa.auth_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = repo.findByEmail(email).orElseThrow(()-> new UserNotFoundException(email));

        return new UserPrincipal(user);
    }
}
