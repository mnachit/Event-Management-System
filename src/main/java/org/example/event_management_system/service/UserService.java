package org.example.event_management_system.service;

import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User saveUserRoleAdmin(User user) throws ValidationException;
    public User updateUser(User user) throws ValidationException;
    public User findByEmail(String email) throws ValidationException;

    public boolean findByEmailAndPassword(String email, String password) throws ValidationException;

    public User findByID(Long id) throws ValidationException;

    public Long findIdByEmail(String email) throws ValidationException;

}
