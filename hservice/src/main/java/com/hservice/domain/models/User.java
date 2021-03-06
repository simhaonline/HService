package com.hservice.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.hservice.domain.models.UserStatus.*;
import static java.util.Objects.isNull;

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

    private String userName;

    private String firstName;

    private String lastName;

    @NotBlank(message = "Email code is mandatory")
    @Size(min = 10, max = 50, message = "the length of email is out of range")
    @Email(message = "email isn't valid")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password code is mandatory")
    @Size(min = 6, max = 150, message = "the length of password is out of range")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
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

    @CreationTimestamp
    private Timestamp dateOfRegistration;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Timestamp expirationTime;

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

    public void update(User user) {
        userName = user.userName;
        firstName = user.firstName;
        lastName = user.lastName;
        email = user.email;
        role = user.role;
        commands = user.commands;
        projects = user.projects;
        position = user.position;
        department = user.department;
        placeOfResidence = user.placeOfResidence;
        avatarUrl = user.avatarUrl;
        expirationTime = user.expirationTime;
    }

    public void checkStatus() {
        if (isNull(status) || !status.equals(CREATED)) {
            status = expirationTime.after(new Date()) ? INVITED : EXPIRED;
        }
    }

    @JsonIgnore
    public boolean isExpired() {
        checkStatus();
        return status.equals(EXPIRED);
    }

    @JsonIgnore
    public boolean isInvited() {
        checkStatus();
        return status.equals(INVITED);
    }
}
