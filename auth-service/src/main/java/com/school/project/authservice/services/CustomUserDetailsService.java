package com.school.project.authservice.services;

import com.school.project.authservice.models.AppUser;
import com.school.project.authservice.repositories.AppUserRepository;
import com.school.project.authservice.utils.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private AppUserRepository appUserRepository;

    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Objects.requireNonNull(username, "Email address cannot be null");
        Optional<AppUser> user = appUserRepository.findByEmail(username);
        if(!user.isPresent()) throw new UsernameNotFoundException("No user found with email address: "+ username);
        return JwtUserFactory.create(user.get());
    }
}
