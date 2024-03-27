package org.example.event_management_system.service;

import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.Evenement;
import org.example.event_management_system.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvitationUserEvenementService {
    public Boolean addUserInEvent(User user, Evenement evenement) throws ValidationException;
    public List<User> showAllUserInEvent(String code) throws ValidationException;
}
