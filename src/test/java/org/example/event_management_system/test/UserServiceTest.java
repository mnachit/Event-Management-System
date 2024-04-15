package org.example.event_management_system.test;

import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.User;
import org.example.event_management_system.model.enums.RoleUser;
import org.example.event_management_system.repository.UserRepository;
import org.example.event_management_system.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService; // Mocking UserService interface


    @Test
    public void testSaveUserRoleAdmin() {
        // Mock data
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPhone(123456789);
        user.setPassword("password");
        // Mocking repository behavior
//        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
//        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        // Call the method under test
        User savedUser = null;
        try {
            savedUser = userService.saveUserRoleAdmin(user);

        } catch (ValidationException e) {
            e.printStackTrace();
        }

    }
}

