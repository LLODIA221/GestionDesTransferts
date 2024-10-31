package com.Application.GestionDesTransferts.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@Table(name = "licence", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idJoueur"}, name = "UK_licence_joueur")
})
public class Licence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLicence;

    @OneToOne
    @JoinColumn(name = "idJoueur" ,unique=true)
    private Player joueur; // Référence au joueur pour récupérer les informations

    @ManyToOne
    @JoinColumn(name = "idZone")
    private Zone zone;

    private String numeroLicence; // Numéro généré à partir de l'identité du joueur
    private String dateDelivrance;
    private  String photo;



    public void genererNumeroLicence() {
        // Remplace les trois premiers chiffres du numéro d'identité par "000"
        this.numeroLicence = "0000" + this.joueur.getNumeroIdentite().substring(4);
    }

    // Méthodes pour obtenir les informations du joueur
    public String getNomJoueur() {
        return joueur.getNom();
    }

    public String getDateDeNaissance() {
        return joueur.getDateDeNaissance();
    }

    public String getLieuDeNaissance() {
        return joueur.getLieuDeNaissance();
    }

    public String getNumeroIdentite() {
        return joueur.getNumeroIdentite();
    }
}
