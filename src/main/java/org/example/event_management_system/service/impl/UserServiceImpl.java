package org.example.event_management_system.service.impl;

import lombok.AllArgsConstructor;
import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.User;
import org.example.event_management_system.model.enums.RoleUser;
import org.example.event_management_system.repository.UserRepository;
import org.example.event_management_system.service.UserService;
import org.example.event_management_system.util.ErrorMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public boolean isPasswordValid(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String generateUsername(String firstName, String lastName) {
        String usernameBase = firstName.substring(0, 1).toLowerCase() + lastName.toLowerCase();
        if (userRepository.findByUsername(usernameBase).isEmpty()) {
            return usernameBase;
        }
        else {
            int i = 1;
            while (userRepository.findByUsername(usernameBase + i).isPresent()) {
                i++;
            }
            return usernameBase + i;
        }
    }

    @Override
    public User saveUserRoleAdmin(User user) throws ValidationException {
        user.setUsername(generateUsername(user.getFirstName(), user.getLastName()));
        List<ErrorMessage> errorMessages = new ArrayList<>();
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            errorMessages.add(ErrorMessage.builder().message("Email already exists").build());
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            errorMessages.add(ErrorMessage.builder().message("Username already exists").build());
        if (errorMessages.size() > 0)
            throw new ValidationException(errorMessages);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoleUser(RoleUser.USER);
        Date date = new Date();
        user.setCreatedAt(date);
        userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(User user) throws ValidationException {
        userRepository.save(user);
        return user;
    }

    @Override
    public User findByEmail(String email) throws ValidationException{
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new ValidationException(List.of(ErrorMessage.builder().message("Email not found").build()));
        }

        return userRepository.findByEmail(email).get();
    }


    @Override
    public boolean findByEmailAndPassword(String email, String password) throws ValidationException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        List<ErrorMessage> errorMessages1 = new ArrayList<>();
        if (userOptional.isEmpty())
            errorMessages1.add(ErrorMessage.builder().message("Email is incorrect").build());

        User user = userOptional.get();
        if (isPasswordValid(password, user.getPassword()) == false)
            errorMessages1.add(ErrorMessage.builder().message("Password is incorrect").build());
        if (errorMessages1.size() > 0)
            throw new ValidationException(errorMessages1);
        return true;
    }

    @Override
    public User findByID(Long id) throws ValidationException {
        if (userRepository.findById(id).isEmpty()) {
            throw new ValidationException(List.of(ErrorMessage.builder().message("User not found").build()));
        }
        return userRepository.findById(id).get();
    }

    @Override
    public Long findIdByEmail(String email) throws ValidationException {
        if (userRepository.findByEmail(email).isEmpty()) {
            throw new ValidationException(List.of(ErrorMessage.builder().message("Email not found").build()));
        }
        return userRepository.findByEmail(email).get().getId();
    }

    @Override
    public Boolean deleteUser(Long id) throws ValidationException {
        List<ErrorMessage> errorMessages1 = new ArrayList<>();
        if (userRepository.findById(id).isEmpty()) {
            errorMessages1.add(ErrorMessage.builder().message("User not found").build());
        }
        if (errorMessages1.size() > 0) {
            throw new ValidationException(errorMessages1);
        }
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
