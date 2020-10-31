package com.hservice.domain.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(min = 6, max = 45)
    private String userName;

    @NotBlank(message = "First name code is mandatory")
    @Size(min = 2, max = 45, message = "the length of first name is out of range")
    private String firstName;

    @NotBlank(message = "Last name code is mandatory")
    @Size(min = 1, max = 45, message = "the length of first name is out of range")
    private String lastName;

    @NotBlank(message = "Email code is mandatory")
    @Size(min = 10, max = 50, message = "the length of first name is out of range")
    @Email(message = "email isn't valid")
    private String email;

    @NotBlank(message = "Password code is mandatory")
    @Size(min = 6, max = 150, message = "the length of first name is out of range")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "role", nullable = false)
    private Role role;

    @ManyToMany(mappedBy = "users",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Command> commands = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_project",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private Set<Project> projects = new HashSet<>();

    private String position;

    private String department;

    private String placeOfResidence;

    private Timestamp lastActivity;

    private String avatarUrl;

    private Timestamp dateOfRegistration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userName.equals(user.userName) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                email.equals(user.email) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, firstName, lastName, email, password);
    }
}