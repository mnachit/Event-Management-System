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
        if (errorMessages.size() > 0)
            throw new ValidationException(errorMessages);
        user.setRoleUser(RoleUser.ADMIN);
        userRepository.save(user);
        return user;
    }
}
