package br.com.alura.school.enrollment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

class NewEnrollmentRequest {

    @Size(max = 20)
    @NotBlank
    @JsonProperty
    private final String username;

    @JsonCreator
    NewEnrollmentRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
