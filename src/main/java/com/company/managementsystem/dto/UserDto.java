package com.company.managementsystem.dto;

import com.company.managementsystem.validation.FieldMatch;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class UserDto {

    private Long id;
    @NotNull(message = "is required")
    @Size(min = 4, max = 15, message = "Username should be between 4 and 15 characters")
    private String username;

    @NotNull(message = "is required")
    @Size(min = 4, max = 20, message = "should be at least 8 characters")
    private String password;

    private String matchingPassword;

    @NotNull(message = "is required")
    @Size(min = 2, max = 20, message = "First name should be between 2 and 20 characters")
    private String firstName;

    @NotNull(message = "is required")
    @Size(min = 2, max = 20, message = "Last name should be between 2 and 20 characters")
    private String lastName;

    @NotNull(message = "is required")
    @Size(min = 8, message = "is required")
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
            message = "should be @email.com")
    private String email;

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
