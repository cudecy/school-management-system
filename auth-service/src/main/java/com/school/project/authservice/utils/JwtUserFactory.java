package com.school.project.authservice.utils;

import com.school.project.authservice.models.AppUser;
import com.school.project.authservice.models.CustomUserDetails;

import java.util.ArrayList;

public class JwtUserFactory {
    public static CustomUserDetails create(AppUser appUser) {
        return new CustomUserDetails(appUser.getEmail(), appUser.getPasswordHash(), new ArrayList<>());
    }
}
