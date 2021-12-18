package br.com.alura.school.builder;

import br.com.alura.school.user.User;

public class UserBuilder {

    private final String USERNAME_DEAFAULT = "eduardo";
    private final String EMAIL_DEFAULT = "eduardo@email.com";

    private String username = USERNAME_DEAFAULT;
    private String email = EMAIL_DEFAULT;

    public static UserBuilder oneUser(){
        return new UserBuilder();
    }

    public UserBuilder withUsername(String username){
        this.username = username;
        return this;
    }

    public UserBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public User build(){
        return new User(this.username, this.email);
    }

}
