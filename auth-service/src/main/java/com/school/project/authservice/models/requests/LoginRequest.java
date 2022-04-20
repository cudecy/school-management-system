package com.school.project.authservice.models.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@AllArgsConstructor
@Getter
public class LoginRequest implements Serializable {
    @NotEmpty(message = "Email address is required")
    private String emailAddress;
    @NotEmpty(message = "Password is required")
    private String password;
}
