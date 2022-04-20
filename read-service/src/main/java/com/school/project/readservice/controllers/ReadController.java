package com.school.project.readservice.controllers;

import com.school.project.libs.common.exceptions.CustomCheckedException;
import com.school.project.libs.common.responses.ErrorResponse;
import com.school.project.libs.common.responses.SuccessResponse;
import com.school.project.readservice.models.AppUser;
import com.school.project.readservice.services.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/read")
@RestController
public class ReadController {
    private ReadService readService;

    @Autowired
    public void setDeleteService(ReadService readService) {
        this.readService = readService;
    }

    @GetMapping("/get-single/{studentId}")
    public ResponseEntity<?> getStudent(@PathVariable Long studentId) throws CustomCheckedException {
        return new ResponseEntity<>(new SuccessResponse("Student found!", readService.getSingleStudent(studentId)), HttpStatus.OK);
    }

    @GetMapping("/get-single-by-email/{emailAddress}")
    public ResponseEntity<?> getStudent(@PathVariable String emailAddress) throws CustomCheckedException {
        return new ResponseEntity<>(new SuccessResponse("Student found!", readService.getSingleStudent(emailAddress)), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllStudents() {
        return new ResponseEntity<>(new SuccessResponse("Students found!", readService.getAllStudents()), HttpStatus.OK);
    }

    @GetMapping("/get-all/{pageNumber}/{pageSize}")
    public ResponseEntity<?> getAllStudentsPaginated(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        return new ResponseEntity<>(new SuccessResponse("Students found!", readService.getAllStudents(pageNumber, pageSize)), HttpStatus.OK);
    }
}
