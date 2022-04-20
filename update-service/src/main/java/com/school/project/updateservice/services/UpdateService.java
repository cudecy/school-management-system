package com.school.project.updateservice.services;

import com.school.project.libs.common.exceptions.CustomCheckedException;
import com.school.project.updateservice.models.AppUser;
import com.school.project.updateservice.models.requests.UpdateStudentRequest;

public interface UpdateService {
    AppUser updateStudent(Long studentId, UpdateStudentRequest updateStudentRequest) throws CustomCheckedException;
}
