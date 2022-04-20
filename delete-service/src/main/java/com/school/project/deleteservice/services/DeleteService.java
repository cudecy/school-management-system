package com.school.project.deleteservice.services;

import com.school.project.libs.common.exceptions.CustomCheckedException;

public interface DeleteService {
    boolean deleteStudent(Long studentId) throws CustomCheckedException;

    boolean deleteStudent(String emailAddress) throws CustomCheckedException;
}
