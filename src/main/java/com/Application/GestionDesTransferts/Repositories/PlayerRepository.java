package com.Application.GestionDesTransferts.Repositories;

import com.Application.GestionDesTransferts.Models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
