package org.example.event_management_system.model.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Entity
@Enabled
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomEvenement;
    private String codeEvenement;
    private String localisation;
    private Long nombreMaxUser;
    private Long nombreUser;
    private Time dateDebut;
    private Time dateFin;
    private Date createdAt;
    private String amount;
    private Date updatedAt;
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private Agenda agenda;
    @OneToMany
    private List<InvitationUserEvenement> invitationUserEvenements;
}
