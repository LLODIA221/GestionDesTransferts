package com.Application.GestionDesTransferts.Repositories;

import com.Application.GestionDesTransferts.Models.Association;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AssociationRepository extends JpaRepository<Association, Long> {

}
