package org.example.event_management_system.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.event_management_system.model.dto.reponse.EvenementResponse;
import org.example.event_management_system.model.entity.Evenement;
import org.example.event_management_system.model.entity.User;
import org.example.event_management_system.model.mapper.EventMapper;
import org.example.event_management_system.repository.UserRepository;
import org.example.event_management_system.service.EvenementService;
import org.example.event_management_system.service.UserService;
import org.example.event_management_system.util.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class EvenementController {
    private EvenementService evenementService;
    private UserService userService;

    @PostMapping("/evenement/new")
    public ResponseEntity<?> createEvenement(@RequestBody @Valid EvenementResponse evenementResponse) {
        Response<String> evenet = new Response<>();
        try {
            User user = userService.findByID(evenementResponse.getCreatedBy());
            evenementService.createEvenement(EventMapper.mapToEvenement(user,evenementResponse), EventMapper.mapToAgenda(user,evenementResponse));
            evenet.setMessage("The event was added");
            return ResponseEntity.ok(evenementResponse);
        } catch (jakarta.validation.ValidationException e){
            evenet.setMessage("The event was not added");
            return ResponseEntity.ok(evenet);
        }
    }
}
