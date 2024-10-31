package com.Application.GestionDesTransferts.Repositories;

import com.Application.GestionDesTransferts.Models.Licence;
import com.Application.GestionDesTransferts.Models.Player;
import com.Application.GestionDesTransferts.Models.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LicenceRepository extends JpaRepository<Licence, Long> {
    // Utilisation de @Query pour spécifier explicitement le comptage
    @Query("SELECT COUNT(l) FROM Licence l WHERE l.zone = :zone")
    long countByZone(@Param("zone") Zone zone);

    // Trouver les licences par zone
    @Query("SELECT l FROM Licence l WHERE l.zone = :zone")
    List<Licence> findByZone(@Param("zone") Zone zone);

    boolean existsByJoueurIdJoueur(Long idJoueur);
    Optional<Licence> findByJoueur(Player joueur);
}

