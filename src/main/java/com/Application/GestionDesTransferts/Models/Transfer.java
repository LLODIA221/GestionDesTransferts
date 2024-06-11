package com.Application.GestionDesTransferts.Models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransfert;
    @ManyToOne
    @JoinColumn(name = "idJoueur")
    private Player joueur;
    @ManyToOne
    @JoinColumn(name = "associationArrive")
    private Association associationArrive;
    @ManyToOne
    @JoinColumn(name = "associationDepart")
    private Association associationDepart;
    private String dateTransfert;
}
