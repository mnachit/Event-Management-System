package org.example.event_management_system.service.impl;

import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.Agenda;
import org.example.event_management_system.model.entity.Evenement;
import org.example.event_management_system.repository.EvenementRepository;
import org.example.event_management_system.service.AgendaService;
import org.example.event_management_system.service.EvenementService;
import org.example.event_management_system.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EvenementServiceImpl implements EvenementService {
    private EvenementRepository evenementRepository;
    private AgendaService agendaService;
    private List<ErrorMessage> errorMessages = new ArrayList<>();

    @Autowired
    public EvenementServiceImpl(EvenementRepository evenementRepository, AgendaService agendaService) {
        this.evenementRepository = evenementRepository;
        this.agendaService = agendaService;
    }
    @Override
    public Boolean createEvenement(Evenement evenement, Agenda agenda) throws ValidationException {
        evenement.setCreatedAt(new Date());
        evenement.setUpdatedAt(null);
        if (evenement == null) {
            errorMessages.add(ErrorMessage.builder().message("The event is null").build());
        }
        if (agenda == null) {
            errorMessages.add(ErrorMessage.builder().message("Agenda is null").build());
        }
        // Check if either saving the event or creating the agenda fails
        if (evenementRepository.save(evenement) == null || agendaService.createAgenda(agenda) == false) {
            errorMessages.add(ErrorMessage.builder().message("Failed to save the event").build());
        }
        if (errorMessages.size() > 0) {
            throw new ValidationException(errorMessages);
        }
        return false;
    }

}
