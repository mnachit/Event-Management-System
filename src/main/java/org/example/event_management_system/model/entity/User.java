package org.example.event_management_system.model.entity;


import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.event_management_system.model.enums.RoleUser;

import java.util.Date;
import java.util.List;

@Entity
@Enabled
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private RoleUser roleUser;
    private Date createdAt;
    private Date updatedAt;
    @OneToMany
    private List<Agenda> agendas;
    @OneToMany
    private List<Invitation> invitations;
    @OneToMany
    private List<InvitationUserEvenement> invitationUserEvenements;
}
