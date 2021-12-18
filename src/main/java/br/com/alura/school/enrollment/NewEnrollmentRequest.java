package br.com.alura.school.enrollment;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

class NewEnrollmentRequest {

    @Size(min = 3, max = 20, message = "username must contain betwen 3 and 20 characters")
    @NotBlank(message = "username cannot be empty")
    private final String username;

    @JsonCreator
    NewEnrollmentRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
