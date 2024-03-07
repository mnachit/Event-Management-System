package org.example.event_management_system.service.impl;

import lombok.AllArgsConstructor;
import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.User;
import org.example.event_management_system.model.enums.RoleUser;
import org.example.event_management_system.repository.UserRepository;
import org.example.event_management_system.service.UserService;
import org.example.event_management_system.util.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private List<ErrorMessage> errorMessages = new ArrayList<>();

    @Override
    public User saveUserRoleAdmin(User user) throws ValidationException {
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            errorMessages.add(ErrorMessage.builder().message("Email already exists").build());
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            errorMessages.add(ErrorMessage.builder().message("Username already exists").build());
        if (errorMessages.size() > 0)
            throw new ValidationException(errorMessages);
        user.setRoleUser(RoleUser.USER);
        Date date = new Date();
        user.setCreatedAt(date);
        userRepository.save(user);
        return user;
    }

    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email).get();
    }

    @Override
    public boolean findByEmailAndPassword(String email, String password) throws ValidationException {
        if (userRepository.findByEmailAndPassword(email, password).isEmpty()) {
            throw new ValidationException(List.of(ErrorMessage.builder().message("Email or password is incorrect").build()));

        }
        return true;
    }
}
