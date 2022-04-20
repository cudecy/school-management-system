package com.school.project.authservice.controllers;

import com.school.project.authservice.services.HostMaster;
import com.school.project.libs.common.exceptions.CustomCheckedException;
import com.school.project.libs.common.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    private HostMaster hostMaster;

    @Autowired
    public void setHostMaster(HostMaster hostMaster) {
        this.hostMaster = hostMaster;
    }

    @GetMapping("/get-details")
    public ResponseEntity<?> getCurrentUserDetails() throws CustomCheckedException {
        return new ResponseEntity<>(new SuccessResponse("User details found!", hostMaster.getCurrentUser()), HttpStatus.OK);
    }
}
