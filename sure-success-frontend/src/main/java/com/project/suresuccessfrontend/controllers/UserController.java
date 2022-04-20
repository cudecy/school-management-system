package com.project.suresuccessfrontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {
    @GetMapping("/create-student")
    public String createStudent() {
        return "create-student";
    }

    @GetMapping("/edit-profile/{studentId}")
    public String editProfile(Model model, @PathVariable Long studentId) {
        model.addAttribute("editMode", true);
        model.addAttribute("studentId", studentId);
        return "edit-student";
    }

    @GetMapping("/view-student/{studentId}")
    public String viewStudent(Model model, @PathVariable Long studentId) {
        model.addAttribute("editMode", false);
        model.addAttribute("studentId", studentId);
        return "edit-student";
    }

    @GetMapping("/all-students")
    public String allStudents() {
        return "all-students";
    }
}
