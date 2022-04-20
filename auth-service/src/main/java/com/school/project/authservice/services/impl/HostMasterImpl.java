package com.school.project.authservice.services.impl;

import com.school.project.authservice.models.AppUser;
import com.school.project.authservice.repositories.AppUserRepository;
import com.school.project.authservice.services.HostMaster;
import com.school.project.libs.common.exceptions.CustomCheckedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HostMasterImpl implements HostMaster {
    private AppUserRepository appUserRepository;

    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser getCurrentUser() throws CustomCheckedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<AppUser> appUser = appUserRepository.findByEmail(authentication.getName());
        if(!appUser.isPresent()) throw new CustomCheckedException(String.format("User with email: %s not found", authentication.getName()));
        return appUser.get();
    }
}
