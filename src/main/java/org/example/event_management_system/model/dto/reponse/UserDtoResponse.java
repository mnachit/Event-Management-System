package org.example.event_management_system.model.dto.reponse;

import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.event_management_system.model.enums.RoleUser;

@Data
@Enabled
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDtoResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Integer phone;
    private RoleUser role;
}