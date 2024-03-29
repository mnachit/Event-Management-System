package org.example.event_management_system.model.mapper;

import org.example.event_management_system.model.dto.reponse.EvenementResponse;
import org.example.event_management_system.model.entity.Agenda;
import org.example.event_management_system.model.entity.Evenement;
import org.example.event_management_system.model.entity.User;
import org.example.event_management_system.model.request.AddUserInEvent;

import java.util.List;

public class EventMapper {
    public static Evenement mapToEvenement(User user,EvenementResponse evenementResponse) {
        return Evenement.builder().createdBy(user).amount(evenementResponse.getAmount()).nombreMaxUser(evenementResponse.getNombreMaxUser()).localisation(evenementResponse.getLocalisation()).nomEvenement(evenementResponse.getNomEvenement()).dateDebut(evenementResponse.getDateDebut()).dateFin(evenementResponse.getDateFin()).build();
    }

    public static Agenda mapToAgenda(User user,EvenementResponse evenementResponse) {
        return Agenda.builder().createdBy(user).descriptionAgenda(evenementResponse.getDescriptionAgenda()).dateCreation(evenementResponse.getDateCreation()).build();
    }

    public static User AddUserInEventToUser(AddUserInEvent addUserInEvent) {
        return User.builder().firstName(addUserInEvent.getFirstName()).lastName(addUserInEvent.getLastName()).email(addUserInEvent.getEmail()).build();
    }
}
