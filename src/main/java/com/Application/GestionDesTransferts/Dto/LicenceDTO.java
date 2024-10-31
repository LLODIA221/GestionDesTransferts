package com.Application.GestionDesTransferts.Dto;

import lombok.Data;

@Data
public class LicenceDTO {
    private Long idLicence;
    private String nomJoueur;
    private String zone;
    private String dateDeNaissance;
    private String lieuDeNaissance;
    private String numeroIdentite;
    private String numeroLicence;
    private String dateDelivrance;
    private String photo;
}
