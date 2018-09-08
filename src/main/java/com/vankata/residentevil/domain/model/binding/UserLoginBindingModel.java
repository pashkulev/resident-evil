package com.vankata.residentevil.domain.model.binding;

import javax.validation.constraints.Size;

public class UserLoginBindingModel {

    @Size(min = 3, max = 20, message = "Username should be between 3 and 20 characters!")
    private String username;

    @Size(min = 4, max = 10, message = "Password should be between 4 and 10 characters!")
    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
