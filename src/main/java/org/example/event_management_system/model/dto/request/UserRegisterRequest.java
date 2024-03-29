package org.example.event_management_system.model.dto.request;

import jakarta.validation.Valid;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Enabled
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Integer phone;
}
