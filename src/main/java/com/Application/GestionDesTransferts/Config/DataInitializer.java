package com.Application.GestionDesTransferts.Config;


import com.Application.GestionDesTransferts.Dto.UserDto;
import com.Application.GestionDesTransferts.Models.Association;
import com.Application.GestionDesTransferts.Models.Player;
import com.Application.GestionDesTransferts.Models.Transfer;
import com.Application.GestionDesTransferts.Models.Zone;
import com.Application.GestionDesTransferts.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {

    @Autowired
    private UserService userService;

    @Autowired
    private AssociationService associationService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TransferService transferService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Initialiser les utilisateurs par défaut
            createDefaultUser("adminadmin@gmail.com", "admin221", "admin", "ADMIN");
            createDefaultUser("useruser@gmail.com", "user221", "user", "USER");


            // Initialiser les zones par défaut
            Zone zone1 = createDefaultZone("Zone Nord");
            Zone zone2 = createDefaultZone("Zone Sud");
            Zone zone3 = createDefaultZone("Zone Est");
            Zone zone4 = createDefaultZone("Zone Ouest");

            // Initialiser les associations par défaut
            Association association1 = createDefaultAssociation("Association 1",zone1);
            Association association2 = createDefaultAssociation("Association 2", zone2);
            Association association3 = createDefaultAssociation("Association 3", zone3);
            Association association4 = createDefaultAssociation("Association 4", zone1);
            Association association5 = createDefaultAssociation("Association 5", zone4);

            // Initialiser les joueurs par défaut
            Player player1 = createDefaultPlayer("123456", "Joueur 1", "1990-01-01", "Paris", "0600000001", "Paris", association1);
            Player player2 = createDefaultPlayer("123457", "Joueur 2", "1991-02-02", "Marseille", "0600000002", "Marseille", association2);
            Player player3 = createDefaultPlayer("123458", "Joueur 3", "1992-03-03", "Lyon", "0600000003", "Lyon", association3);
            // ... Ajouter 7 autres joueurs comme player4, player5, jusqu'à player10

            // Initialiser les transferts par défaut
            createDefaultTransfer(player1, association1, association2, "2023-01-01");
            createDefaultTransfer(player2, association2, association3, "2023-02-01");
            createDefaultTransfer(player3, association3, association4, "2023-03-01");
            // ... Ajouter 12 autres transferts
        };
    }
//creation d'utilisation par defaut'
    private void createDefaultUser(String email, String password, String fullname, String role) {
        // Vérifier si l'utilisateur existe déjà
        if (userService.findbyEmail(email) == null) {
            // Créer l'utilisateur
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setPassword(password);
            userDto.setFullname(fullname);
            userDto.setRole(role);

            // Sauvegarder l'utilisateur
            userService.save(userDto);
            System.out.println("Utilisateur par défaut créé avec succès: " + email);
        } else {
            System.out.println("L'utilisateur par défaut existe déjà: " + email);
        }
    }

    // Création des utilisateurs

    private Player createDefaultPlayer(String numeroIdentite, String nom, String dateDeNaissance, String lieuDeNaissance, String telephone, String residence, Association association) {
        List<Player> players = playerService.getAllPlayers();
        boolean exists = players.stream()
                .anyMatch(p -> p.getNumeroIdentite().equals(numeroIdentite));

        if (!exists) {
            Player player = new Player();
            player.setNumeroIdentite(numeroIdentite);
            player.setNom(nom);
            player.setDateDeNaissance(dateDeNaissance);
            player.setLieuDeNaissance(lieuDeNaissance);
            player.setTelephone(telephone);
            player.setResidence(residence);
            player.setAssociation(association);

            playerService.savePlayer(player);
            System.out.println("Joueur par défaut créé avec succès: " + nom);
            return player;
        } else {
            System.out.println("Le joueur existe déjà: " + nom);
            return players.stream()
                    .filter(p -> p.getNumeroIdentite().equals(numeroIdentite))
                    .findFirst()
                    .orElse(null);
        }
    }

    //creation de transfert par defaut
    private void createDefaultTransfer(Player joueur, Association associationDepart, Association associationArrive, String dateTransfert) {
        List<Transfer> transfers = transferService.getAllTransfers();
        boolean exists = transfers.stream()
                .anyMatch(t -> t.getJoueur().equals(joueur) &&
                        t.getAssociationDepart().equals(associationDepart) &&
                        t.getAssociationArrive().equals(associationArrive) &&
                        t.getDateTransfert().equals(dateTransfert));

        if (!exists) {
            Transfer transfer = new Transfer();
            transfer.setJoueur(joueur);
            transfer.setAssociationDepart(associationDepart);
            transfer.setAssociationArrive(associationArrive);
            transfer.setDateTransfert(dateTransfert);

            transferService.saveTransfer(transfer);
            System.out.println("Transfert par défaut créé avec succès pour: " + joueur.getNom());
        } else {
            System.out.println("Le transfert existe déjà pour: " + joueur.getNom());
        }
    }

    //creation de zone par defaut
    private Zone createDefaultZone(String nomZone) {
        // Vérifier si la zone existe déjà
        List<Zone> zones = zoneService.getAllZones();
        Zone existingZone = zones.stream()
                .filter(z -> z.getNomZone().equals(nomZone))
                .findFirst()
                .orElse(null);

        if (existingZone == null) {
            Zone zone = new Zone();
            zone.setNomZone(nomZone);
            zoneService.saveZone(zone);
            System.out.println("Zone par défaut créée avec succès: " + nomZone);
            return zone;
        } else {
            System.out.println("La zone existe déjà: " + nomZone);
            return existingZone;
        }
    }

    //creation d'association  par defaut
    //création d'association par défaut
    private Association createDefaultAssociation(String nomAssociation, Zone zone) {
        List<Association> associations = associationService.getAllAssociations();
        Association existingAssociation = associations.stream()
                .filter(a -> a.getNomAssociation().equals(nomAssociation))
                .findFirst()
                .orElse(null);

        if (existingAssociation == null) {
            Association association = new Association();
            association.setNomAssociation(nomAssociation);
            association.setZone(zone);
            associationService.saveAssociation(association);
            System.out.println("Association par défaut créée avec succès: " + nomAssociation);
            return association;  // Retourne l'association nouvellement créée
        } else {
            System.out.println("L'association par défaut existe déjà: " + nomAssociation);
            return existingAssociation;  // Retourne l'association existante
        }
    }

}

