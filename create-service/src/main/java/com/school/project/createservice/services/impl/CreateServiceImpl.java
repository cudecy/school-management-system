package com.school.project.createservice.services.impl;

import com.school.project.createservice.models.AppUser;
import com.school.project.createservice.models.requests.CreateRequest;
import com.school.project.createservice.repositories.AppUserRepository;
import com.school.project.createservice.services.CreateService;
import com.school.project.libs.common.exceptions.CustomCheckedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CreateServiceImpl implements CreateService {
    private AppUserRepository appUserRepository;

    private PasswordEncoder passwordEncoder;

    public CreateServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser create(CreateRequest createRequest) throws CustomCheckedException {
        if(!this.validateEmail(createRequest.getEmail().trim()))
            throw new CustomCheckedException("Sorry, only valid email addresses are supported at the moment. Format should be -: example@domain.com");
        if(!this.validatePhoneNumber(createRequest.getPhoneNumber().trim()))
            throw new CustomCheckedException("Sorry, only valid phone numbers are supported at the moment. Format should be -: 0xxxxxxxxxx. E.g, 08012345678");
        passwordEncoder.encode(createRequest.getPassword());

        if(appUserRepository.findByEmail(createRequest.getEmail()).isPresent())
            throw new CustomCheckedException(String.format("A student with email: %s already exists", createRequest.getEmail()));

        AppUser appUser = new AppUser(createRequest);
        appUser.setPasswordHash(passwordEncoder.encode(createRequest.getPassword()));
        return appUserRepository.save(appUser);
    }

    private boolean validateEmail(String emailAddress){
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("0\\d{10}");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
