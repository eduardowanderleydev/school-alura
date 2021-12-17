package br.com.alura.school.user;

import com.fasterxml.jackson.annotation.JsonProperty;

class UserResponse {

    @JsonProperty
    private final String username;

    @JsonProperty
    private final String email;

    UserResponse(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
