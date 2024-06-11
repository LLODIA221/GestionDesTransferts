package com.Application.GestionDesTransferts.Models;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Association {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAssociation;
    private String nomAssociation;
    @ManyToOne
    @JoinColumn(name = "idZone")
    private Zone zone;
}
