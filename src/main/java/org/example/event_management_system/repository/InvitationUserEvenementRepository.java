package org.example.event_management_system.repository;

import org.example.event_management_system.model.entity.InvitationUserEvenement;
import org.example.event_management_system.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationUserEvenementRepository extends JpaRepository<InvitationUserEvenement, Long> {
    public Optional<InvitationUserEvenement> findInvitationUserEvenementByUserId(Long id);
    public List<InvitationUserEvenement> findInvitationUserEvenementByEvenementId(Long id);
    public Optional<InvitationUserEvenement> findInvitationUserEvenementByUserIdAndEvenementId(Long userId, Long evenementId);
}