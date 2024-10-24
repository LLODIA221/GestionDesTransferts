package com.Application.GestionDesTransferts.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Data
public class PlayerForm {
    private Long idJoueur;
    private String numeroIdentite;
    private String nom;
    private String dateDeNaissance;
    private String lieuDeNaissance;
    private String telephone;
    private String residence;
    private Long associationId;  // ID de l'association sélectionnée
    private MultipartFile photo;  // Fichier photo

    public PlayerForm() {
        super();
    }

}
