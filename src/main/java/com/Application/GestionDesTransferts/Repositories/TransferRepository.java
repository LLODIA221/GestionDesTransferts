package com.Application.GestionDesTransferts.Repositories;

import com.Application.GestionDesTransferts.Models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
