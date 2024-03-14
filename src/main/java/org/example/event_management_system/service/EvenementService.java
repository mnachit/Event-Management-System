package org.example.event_management_system.service;

import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.Agenda;
import org.example.event_management_system.model.entity.Evenement;
import org.springframework.stereotype.Service;

@Service
public interface EvenementService {
    public Boolean createEvenement(Evenement evenement, Agenda agenda) throws ValidationException;
}
