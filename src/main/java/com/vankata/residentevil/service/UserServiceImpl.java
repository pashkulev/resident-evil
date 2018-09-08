package com.vankata.residentevil.service;

import com.vankata.residentevil.domain.entity.Role;
import com.vankata.residentevil.domain.entity.User;
import com.vankata.residentevil.domain.model.binding.UserEditBindingModel;
import com.vankata.residentevil.domain.model.binding.UserRegisterBindingModel;
import com.vankata.residentevil.domain.model.service.UserServiceModel;
import com.vankata.residentevil.repository.RoleRepository;
import com.vankata.residentevil.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Qualifier("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEditBindingModel findById(String id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user != null) {
            UserEditBindingModel userEditBindingModel = this.modelMapper.map(user, UserEditBindingModel.class);
            Set<String> roles = user.getRoles()
                    .stream()
                    .map(Role::getAuthority)
                    .collect(Collectors.toSet());
            userEditBindingModel.setRoles(roles);

            return userEditBindingModel;
        }

        return null;
    }

    @Override
    public Set<UserServiceModel> findAll() {
        List<User> users = this.userRepository.findAll();
         return users.stream().map(user -> {
            UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
            String userAuthorities = user.getRoles().toString();
            userServiceModel
                    .setRoles(userAuthorities.substring(1, userAuthorities.length() - 1));
            return userServiceModel;
        }).collect(Collectors.toSet());
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user != null) {
            return this.modelMapper.map(user, UserServiceModel.class);
        }

        return null;
    }

    @Override
    public UserServiceModel findByEmail(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user != null) {
            return this.modelMapper.map(user, UserServiceModel.class);
        }

        return null;
    }

    @Override
    public boolean registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        User user = this.modelMapper.map(userRegisterBindingModel, User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role = this.roleRepository.findByAuthority("USER");
        user.getRoles().add(role);
        role.getUsers().add(user);

        this.roleRepository.save(role);
        return this.userRepository.save(user).getId() != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return user;
    }

    @Override
    public void editUser(UserEditBindingModel userEditBindingModel) {
        User user = this.userRepository.findById(userEditBindingModel.getId()).orElse(null);
        if (user != null) {
            user.setUsername(userEditBindingModel.getUsername());
            user.setEmail(userEditBindingModel.getEmail());

            for (Role role : user.getRoles()) {
                role.getUsers().remove(user);
                this.roleRepository.save(role);
            }

            user.getRoles().clear();
            for (String roleString : userEditBindingModel.getRoles()) {
                Role role = this.roleRepository.findByAuthority(roleString);
                user.getRoles().add(role);
                role.getUsers().add(user);
                this.roleRepository.save(role);
            }

            this.userRepository.save(user);
        }
    }
}
