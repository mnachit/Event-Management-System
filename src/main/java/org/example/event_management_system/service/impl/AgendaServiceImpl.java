package org.example.event_management_system.service.impl;

import lombok.AllArgsConstructor;
import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.Agenda;
import org.example.event_management_system.model.entity.User;
import org.example.event_management_system.repository.AgendaRepository;
import org.example.event_management_system.service.AgendaService;
import org.example.event_management_system.service.UserService;
import org.example.event_management_system.util.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AgendaServiceImpl implements AgendaService {
    private AgendaRepository agendaRepository;
    private UserService userService;

    private List<ErrorMessage> errorMessages = new ArrayList<>();
    @Override
    public Boolean createAgenda(Agenda agenda) throws ValidationException {
        agenda.setCreatedAt(new Date());
//        User user = userService.findByID(agenda.getCreatedBy())
        if (agenda == null) {
            errorMessages.add(ErrorMessage.builder().message("Agenda is null").build());
        }
        if (errorMessages.size() > 0) {
            throw new ValidationException(errorMessages);
        }
        if (agendaRepository.save(agenda) != null)
            return true;
        return false;
    }
}
