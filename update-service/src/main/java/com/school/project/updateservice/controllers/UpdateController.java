package com.school.project.updateservice.controllers;

import com.school.project.libs.common.exceptions.CustomCheckedException;
import com.school.project.libs.common.responses.ErrorResponse;
import com.school.project.libs.common.responses.SuccessResponse;
import com.school.project.updateservice.models.AppUser;
import com.school.project.updateservice.models.requests.UpdateStudentRequest;
import com.school.project.updateservice.services.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/update")
@RestController
public class UpdateController {
    private UpdateService updateService;

    @Autowired
    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

/** @param studentId is passed a s request parameter
 * @param updateStudentRequest is the request body containing updates to be implemented
 * 'e.g' /update?studentId=x
 * @return  edited profile
 */
    @PutMapping
    public ResponseEntity<?> editProfile(@RequestParam("studentId") Long studentId, @RequestBody UpdateStudentRequest updateStudentRequest) throws CustomCheckedException {
        AppUser appUser = updateService.updateStudent(studentId, updateStudentRequest);
        if(appUser == null)
            return new ResponseEntity<>(new ErrorResponse("Oops! A critical error occurred while trying to edit profile. Please try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new SuccessResponse("Profile edited successfully!", appUser), HttpStatus.OK);
    }
}
