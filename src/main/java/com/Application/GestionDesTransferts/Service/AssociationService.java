package com.Application.GestionDesTransferts.Service;

import com.Application.GestionDesTransferts.Models.Association;
import com.Application.GestionDesTransferts.Repositories.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssociationService {
    @Autowired
    private AssociationRepository associationRepository;

    public List<Association> getAllAssociations() {
        return associationRepository.findAll();
    }

    public Association getAssociationById(Long id) {
        return associationRepository.findById(id).orElse(null);
    }

    public Association saveAssociation(Association association) {
        return associationRepository.save(association);
    }

    public void deleteAssociation(Long id) {
        associationRepository.deleteById(id);
    }
}