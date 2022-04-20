package com.school.project.updateservice.models.requests;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UpdateStudentRequest implements Serializable {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String country;
    private String state;
}
