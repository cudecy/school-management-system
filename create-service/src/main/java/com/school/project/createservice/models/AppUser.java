package com.school.project.createservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.school.project.createservice.models.requests.CreateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    private String phoneNumber;
    private String country;
    private String state;
    @JsonIgnore
    private String passwordHash;
    private Date createdOn;

    public AppUser(CreateRequest createRequest) {
        this.firstName = createRequest.getFirstName();
        this.lastName = createRequest.getLastName();
        this.email = createRequest.getEmail().trim();
        this.phoneNumber = createRequest.getPhoneNumber().trim();
        this.country = createRequest.getCountry();
        this.state = createRequest.getState();
        this.createdOn = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return getId().equals(appUser.getId()) && getEmail().equals(appUser.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPhoneNumber());
    }
}
