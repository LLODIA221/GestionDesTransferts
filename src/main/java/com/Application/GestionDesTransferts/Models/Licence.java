package com.Application.GestionDesTransferts.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "licence", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idJoueur"}, name = "UK_licence_joueur")
})
@Data
public class Licence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLicence;

    @OneToOne
    @JoinColumn(name = "idJoueur", unique = true)
    private Player joueur;

    @ManyToOne
    @JoinColumn(name = "idZone")
    private Zone zone;

    private String numeroLicence;
    private String dateDelivrance;
    private String dateExpiration; // Nouveau champ pour la date d'expiration
    private String photo;

    // Méthode pour générer le numéro de licence
    public void genererNumeroLicence() {
        this.numeroLicence = "0000" + this.joueur.getNumeroIdentite().substring(4);
    }

    // Méthode pour calculer la date d'expiration
    public void calculerDateExpiration() {
        if (this.dateDelivrance != null) {
            LocalDate delivranceDate = LocalDate.parse(this.dateDelivrance);
            this.dateExpiration = delivranceDate.plusYears(1).toString(); // Par exemple, 1 an de validité
        }
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
