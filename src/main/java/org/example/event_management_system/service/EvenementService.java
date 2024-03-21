package org.example.event_management_system.service;

import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.Agenda;
import org.example.event_management_system.model.entity.Evenement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EvenementService {
    public Boolean createEvenement(Evenement evenement, Agenda agenda) throws ValidationException;
    public List<Evenement> showEvenementByUser(Long id) throws ValidationException;
    public Evenement showEvenement(Long id) throws ValidationException;
    public Boolean deleteEvenement(Long id) throws ValidationException;
    public Evenement showEvenementByCode(String code) throws ValidationException;
}
