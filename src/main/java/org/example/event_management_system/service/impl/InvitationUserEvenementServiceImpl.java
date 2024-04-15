package org.example.event_management_system.service.impl;

import lombok.AllArgsConstructor;
import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.Evenement;
import org.example.event_management_system.model.entity.InvitationUserEvenement;
import org.example.event_management_system.model.entity.User;
import org.example.event_management_system.repository.EvenementRepository;
import org.example.event_management_system.repository.InvitationUserEvenementRepository;
import org.example.event_management_system.repository.UserRepository;
import org.example.event_management_system.service.EvenementService;
import org.example.event_management_system.service.InvitationUserEvenementService;
import org.example.event_management_system.service.UserService;
import org.example.event_management_system.util.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InvitationUserEvenementServiceImpl implements InvitationUserEvenementService {
    private InvitationUserEvenementRepository invitationUserEvenementRepository;
    private UserRepository userRepository;
    private EvenementRepository evenementRepository;
    private UserService userService;
    private EvenementService evenementService;
    @Override
    public Boolean addUserInEvent(User user, Evenement evenement) throws ValidationException {
        List<ErrorMessage> errorMessages = new ArrayList<>();
        if (userRepository.findByEmail(user.getEmail()).isEmpty())
            user = userRepository.save(user);
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            user = userRepository.findByEmail(user.getEmail()).get();
            if(!invitationUserEvenementRepository.findInvitationUserEvenementByUserId(user.getId()).isEmpty())
                errorMessages.add(ErrorMessage.builder().message("the user already exists").build());
        }
        if (user == null || evenement == null)
            errorMessages.add(ErrorMessage.builder().message("User Or Event is null").build());
        if (evenementRepository.findById(evenement.getId()).isEmpty())
            errorMessages.add(ErrorMessage.builder().message("Event not found").build());
        if (errorMessages.size() > 0)
            throw new ValidationException(errorMessages);
        InvitationUserEvenement userEvenement = new InvitationUserEvenement();
        userEvenement.setEvenement(evenement);
        userEvenement.setUser(user);
        invitationUserEvenementRepository.save(userEvenement);
        return true;
    }

    @Override
    public List<User> showAllUserInEvent(String code) throws ValidationException {
        Evenement evenement = new Evenement();
        List<ErrorMessage> errorMessages = new ArrayList<>();

        if (evenementRepository.findByCodeEvenement(code).isEmpty())
            errorMessages.add(ErrorMessage.builder().message("Event not found").build());
        if (errorMessages.size() > 0) {
            throw new ValidationException(errorMessages);
        }
        evenement = evenementRepository.findByCodeEvenement(code).get();
        List<InvitationUserEvenement> invitationUserEvenements = invitationUserEvenementRepository.findInvitationUserEvenementByEvenementId(evenement.getId());
        List<User> users = new ArrayList<>();
        for (InvitationUserEvenement invitationUserEvenement : invitationUserEvenements) {
            users.add(invitationUserEvenement.getUser());
        }
        return users;
    }

    @Override
    public Boolean deleteUserInEvent(User user, Evenement evenement) throws ValidationException {
        List<ErrorMessage> errorMessages = new ArrayList<>();
        if (userRepository.findByEmail(user.getEmail()).isEmpty())
            errorMessages.add(ErrorMessage.builder().message("User not found").build());
        if (evenementRepository.findByCodeEvenement(evenement.getCodeEvenement()).isEmpty())
            errorMessages.add(ErrorMessage.builder().message("Event not found").build());
        if (errorMessages.size() > 0)
            throw new ValidationException(errorMessages);
        InvitationUserEvenement invitationUserEvenement = invitationUserEvenementRepository.findInvitationUserEvenementByUserIdAndEvenementId(user.getId(), evenement.getId()).get();
        if (invitationUserEvenement == null)
            errorMessages.add(ErrorMessage.builder().message("User not found in the event").build());
        if (errorMessages.size() > 0)
            throw new ValidationException(errorMessages);
        invitationUserEvenementRepository.delete(invitationUserEvenement);
        return true;
    }
}
