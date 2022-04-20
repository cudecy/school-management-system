package com.school.project.deleteservice.controllers;

import com.school.project.deleteservice.services.DeleteService;
import com.school.project.libs.common.exceptions.CustomCheckedException;
import com.school.project.libs.common.responses.ErrorResponse;
import com.school.project.libs.common.responses.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/delete")
@RestController
public class DeleteController {
    private DeleteService deleteService;

    @Autowired
    public void setDeleteService(DeleteService deleteService) {
        this.deleteService = deleteService;
    }

    @DeleteMapping("/by-id/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long studentId) throws CustomCheckedException {
        boolean deleteStatus = deleteService.deleteStudent(studentId);
        if(!deleteStatus)
            return new ResponseEntity<>(new ErrorResponse("Oops! A critical error occurred while trying to delete student. Please try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new SuccessResponse("Student deleted successfully!", null), HttpStatus.OK);
    }

    @DeleteMapping("/by-email/{studentEmail}")
    public ResponseEntity<?> deleteStudent(@PathVariable String studentEmail) throws CustomCheckedException {
        boolean deleteStatus = deleteService.deleteStudent(studentEmail);
        if(!deleteStatus)
            return new ResponseEntity<>(new ErrorResponse("Oops! A critical error occurred while trying to delete student. Please try again later"), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(new SuccessResponse("Student deleted successfully!", null), HttpStatus.OK);
    }
}
