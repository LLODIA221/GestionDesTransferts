package com.Application.GestionDesTransferts.Models;

import jakarta.persistence.*;
import lombok.*;




@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String password;
    private String role;
    private String fullname;


    public User() {
        super();
    }

    public User(String email, String password, String role, String fullname) {

        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
    }



    // Énumération Role
    /*public enum Role {
        ADMINISTRATEUR,
        UTILISATEUR
    }*/

}