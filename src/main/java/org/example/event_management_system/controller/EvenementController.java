package org.example.event_management_system.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.dto.reponse.EvenementResponse;
import org.example.event_management_system.model.entity.Evenement;
import org.example.event_management_system.model.entity.User;
import org.example.event_management_system.model.mapper.EventMapper;
import org.example.event_management_system.repository.UserRepository;
import org.example.event_management_system.service.EvenementService;
import org.example.event_management_system.service.UserService;
import org.example.event_management_system.util.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class EvenementController {
    private EvenementService evenementService;
    private UserService userService;

    @PostMapping("/evenement/new")
    public ResponseEntity<?> createEvenement(@RequestBody @Valid EvenementResponse evenementResponse) {
        Response<EvenementResponse> evenet = new Response<>();
        try {
            User user = userService.findByID(evenementResponse.getCreatedBy());

            evenementService.createEvenement(EventMapper.mapToEvenement(user,evenementResponse), EventMapper.mapToAgenda(user,evenementResponse));
            evenet.setMessage("The event was added");
            evenet.setResult(evenementResponse);
            return ResponseEntity.ok(evenet);
        } catch (jakarta.validation.ValidationException e){
            evenet.setMessage("The event was not added");
            return ResponseEntity.ok(evenet);
        }
    }

    @GetMapping("/evenement/show/user/{id}")
    public ResponseEntity<?> showEvenementByUser(@PathVariable @Valid Long id) {
        Response<List<Evenement>> evenet = new Response<>();
        try {
            evenet.setMessage("");
            evenet.setResult(evenementService.showEvenementByUser(id));
            return ResponseEntity.ok(evenementService.showEvenementByUser(id));
        } catch (jakarta.validation.ValidationException e){
            evenet.setMessage("The event was not found");
            return ResponseEntity.ok(evenet);
        }
    }

    @GetMapping("/evenement/{code}")
    public ResponseEntity<?> showEvenementByCode(@PathVariable @Valid String code) {
        Response<Evenement> evenet = new Response<>();
        try {
            evenet.setMessage("");
            evenet.setResult(evenementService.showEvenementByCode(code));
            return ResponseEntity.ok(evenet);
        } catch (ValidationException e){
            evenet.setMessage("The event was not found");
            return ResponseEntity.ok(evenet);
        }
    }

    @GetMapping("/evenement/show/{id}")
    public ResponseEntity<?> showEvenement(@PathVariable @Valid Long id) {
        Response<Evenement> evenet = new Response<>();
        try {
            evenet.setMessage("");
            evenet.setResult(evenementService.showEvenement(id));
            return ResponseEntity.ok(evenementService.showEvenement(id));
        } catch (jakarta.validation.ValidationException e){
            evenet.setMessage("The event was not found");
            return ResponseEntity.ok(evenet);
        }
    }

    @DeleteMapping("/evenement/delete/{id}")
    public ResponseEntity<?> deleteEvenement(@PathVariable @Valid Long id) {
        Response<Evenement> evenet = new Response<>();
        try {
            if (evenementService.deleteEvenement(id))
            {
                evenet.setMessage("The event was deleted");
                return ResponseEntity.ok(evenet);
            }
            return ResponseEntity.ok(evenementService.deleteEvenement(id));
        } catch (ValidationException e){
            evenet.setMessage("The event was not found");
            return ResponseEntity.ok(evenet);
        }
    }
}
