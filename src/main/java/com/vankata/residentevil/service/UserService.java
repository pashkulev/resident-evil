package com.vankata.residentevil.service;

import com.vankata.residentevil.domain.model.binding.UserEditBindingModel;
import com.vankata.residentevil.domain.model.binding.UserRegisterBindingModel;
import com.vankata.residentevil.domain.model.service.UserServiceModel;

import java.util.Set;

public interface UserService {

    UserEditBindingModel findById(String id);

    Set<UserServiceModel> findAll();

    UserServiceModel findByUsername(String username);

    UserServiceModel findByEmail(String email);

    boolean registerUser(UserRegisterBindingModel userRegisterBindingModel);

    void editUser(UserEditBindingModel userEditBindingModel);
}
