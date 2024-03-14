package org.example.event_management_system.service;

import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.Agenda;
import org.springframework.stereotype.Service;

@Service
public interface AgendaService {
    public Boolean createAgenda(Agenda agenda) throws ValidationException;
}
