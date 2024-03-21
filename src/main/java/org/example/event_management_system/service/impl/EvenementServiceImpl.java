package org.example.event_management_system.service.impl;

import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.entity.Agenda;
import org.example.event_management_system.model.entity.Evenement;
import org.example.event_management_system.model.entity.User;
import org.example.event_management_system.repository.EvenementRepository;
import org.example.event_management_system.service.AgendaService;
import org.example.event_management_system.service.EvenementService;
import org.example.event_management_system.service.UserService;
import org.example.event_management_system.util.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class EvenementServiceImpl implements EvenementService {
    private EvenementRepository evenementRepository;
    private UserService userService;
    private AgendaService agendaService;
    private List<ErrorMessage> errorMessages = new ArrayList<>();

    @Autowired
    public EvenementServiceImpl(EvenementRepository evenementRepository, AgendaService agendaService, UserService userService) {
        this.evenementRepository = evenementRepository;
        this.agendaService = agendaService;
        this.userService = userService;
    }

    public String generateUniqueCode() {
        String codePrefix = generateRandomChars(3);
        String codeSuffix = generateRandomChars(3);
        return codePrefix + "-" + codeSuffix;
    }

    public String generateRandomChars(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            stringBuilder.append(chars.charAt(index));
        }
        return stringBuilder.toString();
    }

    @Override
    public Boolean createEvenement(Evenement evenement, Agenda agenda) throws ValidationException {
        evenement.setCreatedAt(new Date());
        evenement.setUpdatedAt(null);
        evenement.setCodeEvenement(generateUniqueCode())    ;

        if (evenement == null) {
            errorMessages.add(ErrorMessage.builder().message("The event is null").build());
        }
        if (agenda == null) {
            errorMessages.add(ErrorMessage.builder().message("Agenda is null").build());
        }

        // Check if the Agenda object is already saved or save it if it doesn't exist yet
        if (agenda.getId() == null) {
            agendaService.createAgenda(agenda);
        }

        // Save the Evenement object after ensuring that the Agenda object is saved
        evenement.setAgenda(agenda);
        Evenement savedEvenement = evenementRepository.save(evenement);

        if (savedEvenement == null) {
            errorMessages.add(ErrorMessage.builder().message("Failed to save the event").build());
        }

        if (errorMessages.size() > 0) {
            throw new ValidationException(errorMessages);
        }

        return false;
    }


    @Override
    public List<Evenement>  showEvenementByUser(Long id) throws ValidationException {
        User user = userService.findByID(id);

        if (evenementRepository.findByCreatedBy(user).isEmpty()) {
            errorMessages.add(ErrorMessage.builder().message("Event not found").build());
        }
        if (errorMessages.size() > 0) {
            throw new ValidationException(errorMessages);
        }
        return new ArrayList<>(evenementRepository.findByCreatedBy(user).get());
    }

    @Override
    public Evenement showEvenement(Long id) throws ValidationException {
        if (evenementRepository.findById(id).isEmpty()) {
            errorMessages.add(ErrorMessage.builder().message("Event not found").build());
        }
        if (errorMessages.size() > 0) {
            throw new ValidationException(errorMessages);
        }
        if (evenementRepository.findById(id).isPresent()) {
            return evenementRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Boolean deleteEvenement(Long id) throws ValidationException {
        List<ErrorMessage> errorMessages1 = new ArrayList<>();
        if (evenementRepository.findById(id).isEmpty()) {
            errorMessages1.add(ErrorMessage.builder().message("Event not found").build());
        }
        if (errorMessages1.size() > 0) {
            throw new ValidationException(errorMessages1);
        }
        if (evenementRepository.findById(id).isPresent()) {
            evenementRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Evenement showEvenementByCode(String code) throws ValidationException {
        List<ErrorMessage> errorMessages1 = new ArrayList<>();
        if (evenementRepository.findByCodeEvenement(code).isEmpty()) {
            errorMessages1.add(ErrorMessage.builder().message("Event not found").build());
        }
        if (errorMessages1.size() > 0) {
            throw new ValidationException(errorMessages1);
        }
        if (evenementRepository.findByCodeEvenement(code).isPresent()) {
            return evenementRepository.findByCodeEvenement(code).get();
        }
        return null;
    }

}
