package org.example.event_management_system.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.event_management_system.auth.JwtUtil;
import org.example.event_management_system.exception.ValidationException;
import org.example.event_management_system.model.dto.reponse.UserDtoResponse;
import org.example.event_management_system.model.dto.request.UserLoginRequest;
import org.example.event_management_system.model.dto.request.UserRegisterRequest;
import org.example.event_management_system.model.entity.User;
import org.example.event_management_system.model.enums.RoleUser;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;

    @PostMapping("/rest/auth/new/user")
    public ResponseEntity<?> createUser(@RequestBody UserRegisterRequest userLoginRequest)
    {
        Response<String> userDtoResponseResponse = new Response<>();
        try {
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
        }
    }

    @PostMapping("/rest/auth/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest)
    {
        Response<String> response = new Response<>();
        try {
            userService.findByEmailAndPassword(userLoginRequest.getEmail(), userLoginRequest.getPassword());
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));
            User user = userService.findByEmail(userLoginRequest.getEmail());
            String token = jwtUtil.createToken(user);
            response.setResult(token);
            response.setMessage("Logged in successfully");
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid email or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (jakarta.validation.ValidationException e){
            response.setMessage("User has not been logged in");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email)
    {
        Response<UserDtoResponse> response = new Response<>();
        try {
            User user = userService.findByEmail(email);
            UserDtoResponse userDtoResponse = UserMapper.UserToUserDto(user);
            response.setResult(userDtoResponse);
            response.setMessage("User has been found");
            return ResponseEntity.ok(response);
        } catch (jakarta.validation.ValidationException e){
            response.setMessage("User has not been found");
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/user/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody UserRegisterRequest userRegisterRequest)
    {
        Response<UserDtoResponse> response = new Response<>();
        try {
            User user = userService.findByID(id);

            user.setFirstName(userRegisterRequest.getFirstName());
            user.setLastName(userRegisterRequest.getLastName());
            user.setEmail(userRegisterRequest.getEmail());
            user.setPhone(userRegisterRequest.getPhone());
            userService.updateUser(user);
            UserDtoResponse userDtoResponse = UserMapper.UserToUserDto(user);
            response.setResult(userDtoResponse);
            response.setMessage("User has been edited");
            return ResponseEntity.ok(response);
        } catch (jakarta.validation.ValidationException e){
            response.setMessage("User has not been edited");
            return ResponseEntity.ok(response);
        }
    }

}
