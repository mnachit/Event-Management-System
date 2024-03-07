package org.example.event_management_system.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.event_management_system.auth.JwtUtil;
import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.dto.reponse.UserDtoResponse;
import org.example.event_management_system.model.dto.request.UserLoginRequest;
import org.example.event_management_system.model.dto.request.UserRegisterRequest;
import org.example.event_management_system.model.entity.User;
import org.example.event_management_system.model.mapper.UserMapper;
import org.example.event_management_system.model.response.ErrorRes;
import org.example.event_management_system.service.UserService;
import org.example.event_management_system.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/rest/auth")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @PostMapping("/new/user")
    public ResponseEntity<?> createUser(@RequestBody UserRegisterRequest userLoginRequest)
    {
        Response<String> userDtoResponseResponse = new Response<>();
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));
            User user = UserMapper.UserRegisterRequestToEntity(userLoginRequest);
            user.setEmail(userLoginRequest.getEmail());
            String token = jwtUtil.createToken(user);
            userService.saveUserRoleAdmin(user);
            userDtoResponseResponse.setMessage("User has been added");
            userDtoResponseResponse.setResult(token);
            return ResponseEntity.ok(userDtoResponseResponse);
        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid email or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (ValidationException e)
        {
            userDtoResponseResponse.setMessage("User has not been added");
            userDtoResponseResponse.setErrors(new ArrayList<>(e.getErrorMessages()));
            return ResponseEntity.ok(userDtoResponseResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest)
    {
        Response<String> response = new Response<>();
        try {
            userService.findByEmailAndPassword(userLoginRequest.getEmail(), userLoginRequest.getPassword());
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));
//            String email = authentication.getName();
            User user = userService.findByEmail(userLoginRequest.getEmail());
            String token = jwtUtil.createToken(user);
            response.setResult(token);
            response.setMessage("User has been logged in");
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid email or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (jakarta.validation.ValidationException e){
            response.setMessage("User has not been logged in");
            return ResponseEntity.ok(response);
        }
    }
}
