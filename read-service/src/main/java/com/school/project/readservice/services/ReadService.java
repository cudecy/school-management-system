package com.school.project.readservice.services;

import com.school.project.libs.common.exceptions.CustomCheckedException;
import com.school.project.readservice.models.AppUser;

import java.util.List;

public interface ReadService {
    AppUser getSingleStudent(Long studentId) throws CustomCheckedException;

    AppUser getSingleStudent(String emailAddress) throws CustomCheckedException;

    List<AppUser> getAllStudents();

    List<AppUser> getAllStudents(Integer pageNumber, Integer pageSize);
}
