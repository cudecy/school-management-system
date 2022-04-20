package com.school.project.updateservice.services.impl;

import com.school.project.libs.common.exceptions.CustomCheckedException;
import com.school.project.updateservice.models.AppUser;
import com.school.project.updateservice.models.requests.UpdateStudentRequest;
import com.school.project.updateservice.repositories.AppUserRepository;
import com.school.project.updateservice.services.UpdateService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UpdateServiceImpl implements UpdateService {
    private AppUserRepository appUserRepository;

    private PasswordEncoder passwordEncoder;

    public UpdateServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser updateStudent(Long studentId, UpdateStudentRequest updateStudentRequest) throws CustomCheckedException {
        AppUser appUser = this.getStudent(studentId);

        if(updateStudentRequest.getOldPassword() != null && updateStudentRequest.getNewPassword() != null){
            if(!updateStudentRequest.getOldPassword().trim().equals("") && !updateStudentRequest.getNewPassword().trim().equals("")) {
                if (!updateStudentRequest.getNewPassword().equals(updateStudentRequest.getConfirmNewPassword()))
                    throw new CustomCheckedException("New Passwords entered do not match");
                this.performPasswordChange(appUser, updateStudentRequest);
            }
        }
        return updateRecords(appUser, updateStudentRequest);
    }

    private void performPasswordChange(AppUser user, UpdateStudentRequest editUserRequest) throws CustomCheckedException {
        if(editUserRequest.getOldPassword().equals(editUserRequest.getNewPassword())) throw new CustomCheckedException("Passwords did not change. So password change won't be implemented");
        if(!passwordEncoder.matches(editUserRequest.getOldPassword(), user.getPasswordHash())) throw new CustomCheckedException("Password entered does not match old one. Password is: "+ editUserRequest.getOldPassword());
        user.setPasswordHash(passwordEncoder.encode(editUserRequest.getNewPassword()));
        appUserRepository.save(user);
    }

    private AppUser updateRecords(AppUser appUser, UpdateStudentRequest updateStudentRequest) throws CustomCheckedException {
        if(updateStudentRequest.getPhoneNumber() != null && !updateStudentRequest.getPhoneNumber().trim().equals("")) {
            if(!validatePhoneNumber(updateStudentRequest.getPhoneNumber().trim()))
                throw new CustomCheckedException("Sorry, only valid phone numbers are supported at the moment. Format should be -: 0xxxxxxxxxx. E.g, 08012345678");
            appUser.setPhoneNumber(updateStudentRequest.getPhoneNumber().trim());
        }

        if(updateStudentRequest.getEmail() != null && !updateStudentRequest.getEmail().trim().equals("")) {
            if(!validateEmail(updateStudentRequest.getEmail().trim()))
                throw new CustomCheckedException("Sorry, only valid phone numbers are supported at the moment. Format should be -: 0xxxxxxxxxx. E.g, 08012345678");
            if(!appUser.getEmail().trim().equals(updateStudentRequest.getEmail().trim())) {
                Optional<AppUser> existingStudent = appUserRepository.findByEmail(appUser.getEmail());
                if(existingStudent.isPresent())
                    throw new CustomCheckedException("A user already exists with the email address: "+ updateStudentRequest.getEmail());
            }
            appUser.setEmail(updateStudentRequest.getEmail().trim());
        }

        if(updateStudentRequest.getFirstName() != null && !updateStudentRequest.getFirstName().trim().equals(""))
            appUser.setFirstName(updateStudentRequest.getFirstName());
        if(updateStudentRequest.getLastName() != null && !updateStudentRequest.getLastName().trim().equals(""))
            appUser.setLastName(updateStudentRequest.getLastName());
        if(updateStudentRequest.getState() != null && !updateStudentRequest.getState().trim().equals(""))
            appUser.setState(updateStudentRequest.getState());
        if(updateStudentRequest.getCountry() != null && !updateStudentRequest.getCountry().trim().equals(""))
            appUser.setCountry(updateStudentRequest.getCountry());
        return appUserRepository.save(appUser);
    }

    private AppUser getStudent(Long id) throws CustomCheckedException {
        Optional<AppUser> appUser = appUserRepository.findById(id);
        if(!appUser.isPresent())
            throw new CustomCheckedException(String.format("Student with id: %d does not exist", id));
        return appUser.get();
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
