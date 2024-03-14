package org.example.event_management_system.model.dto.reponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@Enabled
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvenementResponse {
    @NotBlank(message = "Event name is required")
    private String nomEvenement;

    @NotBlank(message = "Localisation is required")
    private String localisation;

    @Positive(message = "Max number of users must be a positive number")
    private Long nombreMaxUser;

    @NotNull(message = "Start date is required")
    private Time dateDebut;

    @NotNull(message = "End date is required")
    private Time dateFin;

    @NotBlank(message = "Agenda description is required")
    private String descriptionAgenda;

    @PastOrPresent(message = "Creation date must be in the past or present")
    @NotNull(message = "Creation date is required")
    private Date dateCreation;

    @Positive(message = "Created by must be a positive number")
    @NotNull(message = "Created by is required")
    private Long createdBy;
}
