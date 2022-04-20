package com.school.project.deleteservice.services.impl;

import com.school.project.deleteservice.models.AppUser;
import com.school.project.deleteservice.repositories.AppUserRepository;
import com.school.project.deleteservice.services.DeleteService;
import com.school.project.libs.common.exceptions.CustomCheckedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteServiceImpl implements DeleteService {
    private AppUserRepository appUserRepository;

    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public boolean deleteStudent(Long studentId) throws CustomCheckedException {
        AppUser appUser = this.getStudent(studentId);
        appUserRepository.delete(appUser);
//        Check of student still exists in the db...
        try {
            this.getStudent(studentId);
            return false;
        }catch (CustomCheckedException ignored) {
            return true;
        }
    }

    @Override
    public boolean deleteStudent(String emailAddress) throws CustomCheckedException {
        AppUser appUser = this.getStudent(emailAddress);
        appUserRepository.delete(appUser);
//        Check of student still exists in the db...
        try {
            this.getStudent(emailAddress);
            return false;
        }catch (CustomCheckedException ignored) {
            return true;
        }
    }

    private AppUser getStudent(Long id) throws CustomCheckedException {
        Optional<AppUser> appUser = appUserRepository.findById(id);
        if(!appUser.isPresent())
            throw new CustomCheckedException(String.format("Student with id: %d does not exist", id));
        return appUser.get();
    }

    private AppUser getStudent(String email) throws CustomCheckedException {
        Optional<AppUser> appUser = appUserRepository.findByEmail(email);
        if(!appUser.isPresent())
            throw new CustomCheckedException(String.format("Student with email: %s does not exist", email));
        return appUser.get();
    }
}
