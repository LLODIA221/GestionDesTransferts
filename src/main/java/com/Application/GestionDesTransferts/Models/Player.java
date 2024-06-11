package com.Application.GestionDesTransferts.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJoueur;
    private String numeroIdentite;
    private String nom;
    private String dateDeNaissance;
    private String lieuDeNaissance;
    private String telephone;
    private String residence;
    @ManyToOne
    @JoinColumn(name = "idAssociation")
    private Association association;
}
