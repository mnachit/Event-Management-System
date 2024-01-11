package org.example.event_management_system.service;

import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User saveUserRoleAdmin(User user) throws ValidationException;
}
