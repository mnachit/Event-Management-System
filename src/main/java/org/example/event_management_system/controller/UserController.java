package org.example.event_management_system.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.dto.reponse.UserDtoResponse;
import org.example.event_management_system.model.dto.request.UserDtoRequest;
import org.example.event_management_system.model.mapper.UserMapper;
import org.example.event_management_system.service.UserService;
import org.example.event_management_system.util.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/new/user")
    public ResponseEntity<Response<UserDtoResponse>> createUser(@Valid @RequestBody UserDtoRequest userDtoRequest)
    {
        Response<UserDtoResponse> userDtoResponseResponse = new Response<>();
        try {
            userDtoResponseResponse.setMessage("User has been added");
            userDtoResponseResponse.setResult(UserMapper.toDtoResponse(userService.saveUserRoleAdmin(UserMapper.UserDtoResquestToEntity(userDtoRequest))));
            return ResponseEntity.ok(userDtoResponseResponse);
        }catch (ValidationException e)
        {
            userDtoResponseResponse.setMessage("User has not been added");
            userDtoResponseResponse.setErrors(new ArrayList<>(e.getErrorMessages()));
            return ResponseEntity.ok(userDtoResponseResponse);
        }
    }
}
