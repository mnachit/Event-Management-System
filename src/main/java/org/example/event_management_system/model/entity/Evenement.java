package org.example.event_management_system.model.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Enabled
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomEvenement;
    private Date dateDebut;
    private Date dateFin;
    private Date createdAt;
    private Date updatedAt;
    @ManyToOne
    private Agenda agenda;
    @OneToMany
    private List<InvitationUserEvenement> invitationUserEvenements;
}
