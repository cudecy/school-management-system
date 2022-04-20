package com.school.project.authservice.services;


import com.school.project.authservice.models.AppUser;
import com.school.project.libs.common.exceptions.CustomCheckedException;

public interface HostMaster {
    AppUser getCurrentUser() throws CustomCheckedException;
}
