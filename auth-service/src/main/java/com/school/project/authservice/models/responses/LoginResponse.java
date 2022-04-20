package com.school.project.authservice.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Getter
public class LoginResponse implements Serializable {
    private String responseCode;
    private String token;
    private Date tokenExpirationDate;
}
