package org.example.event_management_system.model.mapper;

import org.example.event_management_system.model.dto.reponse.UserDtoResponse;
import org.example.event_management_system.model.dto.request.UserDtoRequest;
import org.example.event_management_system.model.entity.User;

public class UserMapper {
    public static User UserDtoResponsetoEntity(UserDtoResponse userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRoleUser(userDto.getRole());
        return user;
    }
    public static User UserDtoResquestToEntity(UserDtoRequest userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
    public static UserDtoResponse toDtoResponse(User user) {
        return UserDtoResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRoleUser())
                .build();
    }
}