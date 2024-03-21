package org.example.event_management_system.repository;

import org.example.event_management_system.model.entity.Evenement;
import org.example.event_management_system.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {
    public Optional<List<Evenement>> findByCreatedBy(User User);
    public Optional<Evenement> findByCodeEvenement(String codeEvenement);
}
