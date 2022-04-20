package com.school.project.createservice.controllers;

import com.school.project.createservice.models.AppUser;
import com.school.project.createservice.models.requests.CreateRequest;
import com.school.project.createservice.services.CreateService;
import com.school.project.libs.common.exceptions.CustomCheckedException;
import com.school.project.libs.common.responses.ErrorResponse;
import com.school.project.libs.common.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/create")
@RestController
public class CreateController {
    private CreateService createService;

    @Autowired
    public void setCreateService(CreateService createService) {
        this.createService = createService;
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody CreateRequest createRequest) throws CustomCheckedException {
        System.out.println("Create student request is: "+ createRequest.toString());
        AppUser appUser = createService.create(createRequest);
        if(appUser == null)
            return new ResponseEntity<>(new ErrorResponse("Oops! A critical error occurred while trying to create student. Please try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new SuccessResponse("Student created successfully!", appUser), HttpStatus.OK);
    }
}
