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
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descriptionAgenda;
    private Date createdAt;
    private Date updatedAt;
    @ManyToOne
    private User user;
    @OneToMany
    private List<Evenement> evenements;
}
