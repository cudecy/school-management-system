package com.school.project.createservice.services;

import com.school.project.createservice.models.AppUser;
import com.school.project.createservice.models.requests.CreateRequest;
import com.school.project.libs.common.exceptions.CustomCheckedException;

public interface CreateService {
    AppUser create(CreateRequest createRequest) throws CustomCheckedException;
}
