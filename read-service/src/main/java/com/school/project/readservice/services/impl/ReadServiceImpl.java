package com.school.project.readservice.services.impl;

import com.school.project.libs.common.exceptions.CustomCheckedException;
import com.school.project.readservice.models.AppUser;
import com.school.project.readservice.repositories.AppUserRepository;
import com.school.project.readservice.services.ReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReadServiceImpl implements ReadService {
    private AppUserRepository appUserRepository;

    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
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

    @Override
    public AppUser getSingleStudent(Long studentId) throws CustomCheckedException {
        return this.getStudent(studentId);
    }

    @Override
    public AppUser getSingleStudent(String emailAddress) throws CustomCheckedException {
        return this.getStudent(emailAddress);
    }

    @Override
    public List<AppUser> getAllStudents() {
        return appUserRepository.findAll();
    }

    @Override
    public List<AppUser> getAllStudents(Integer pageNumber, Integer pageSize) {
        return appUserRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }
}
