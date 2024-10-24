package com.Application.GestionDesTransferts.Controller;// PlayerController
import com.Application.GestionDesTransferts.Dto.PlayerForm;
import com.Application.GestionDesTransferts.Models.Association;
import com.Application.GestionDesTransferts.Models.Player;
import com.Application.GestionDesTransferts.Service.AssociationService;
import com.Application.GestionDesTransferts.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private static String UPLOADED_FOLDER = System.getProperty("user.dir") + "/uploads/";
    // Dossier où les photos seront stockées
    @Autowired
    private PlayerService playerService;
    @Autowired
    private AssociationService associationService;

    @GetMapping
    public String getAllPlayers(Model model) {
        model.addAttribute("players", playerService.getAllPlayers());
        return "players";
    }

    @GetMapping("/{id}")
    public String getPlayerById(@PathVariable Long id, Model model) {
        model.addAttribute("player", playerService.getPlayerById(id));
        return "player";
    }

    @GetMapping("/new")
    public String createPlayerForm(Model model) {
        model.addAttribute("player", new Player());
        List<Association> associations = associationService.getAllAssociations();
        model.addAttribute("associations", associations);
        return "createPlayer";
    }

//    @PostMapping
//    public String savePlayer(@ModelAttribute Player player) {
//        playerService.savePlayer(player);
//        return "redirect:/players";
//    }
@PostMapping
public String savePlayer(@ModelAttribute Player player, @RequestParam("file") MultipartFile file) {
    try {
        // Sauvegarde du fichier
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            // Assigner le nom du fichier à l'entité Player
            player.setPhoto(file.getOriginalFilename());
        }

        playerService.savePlayer(player);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "redirect:/players";
}

    @GetMapping("/{id}/edit")
    public String editPlayerForm(@PathVariable Long id, Model model) {
        model.addAttribute("player", playerService.getPlayerById(id));
        List<Association> associations = associationService.getAllAssociations();
        model.addAttribute("associations", associations);
        return "editPlayer";
    }

//    @PostMapping("/{id}")
//    public String updatePlayer(@PathVariable Long id, @ModelAttribute Player player) {
//        Player existingPlayer = playerService.getPlayerById(id);
//        // Update the player fields with the new data
//        existingPlayer.setNumeroIdentite(player.getNumeroIdentite());
//        existingPlayer.setNom(player.getNom());
//        existingPlayer.setResidence(player.getResidence());
//        existingPlayer.setTelephone(player.getTelephone());
//        existingPlayer.setLieuDeNaissance(player.getLieuDeNaissance());
//        existingPlayer.setDateDeNaissance(player.getDateDeNaissance());
//        existingPlayer.setAssociation(player.getAssociation());
//        //update
//        playerService.savePlayer(existingPlayer);
//        return "redirect:/players";
//    }


//
//    @PostMapping("/{id}/delete")
//    public String deletePlayer(@PathVariable Long id) {
//        playerService.deletePlayer(id);
//        return "redirect:/players";
//    }

    @PostMapping("/{id}/delete")
    public String deletePlayer(@PathVariable Long id) {
        Player player = playerService.getPlayerById(id);

        // Suppression de la photo
        if (player.getPhoto() != null) {
            try {
                Path path = Paths.get(UPLOADED_FOLDER + player.getPhoto());
                Files.deleteIfExists(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        playerService.deletePlayer(id);
        return "redirect:/players";
    }


    @PostMapping("/{id}")
    public String updatePlayer(@PathVariable Long id,
                               @ModelAttribute PlayerForm playerForm) {
        // Récupérer l'entité Player depuis la base de données
        Player player = playerService.getPlayerById(id);

        // Mettre à jour les champs du joueur avec les données du formulaire
        player.setNumeroIdentite(playerForm.getNumeroIdentite());
        player.setNom(playerForm.getNom());
        player.setDateDeNaissance(playerForm.getDateDeNaissance());
        player.setLieuDeNaissance(playerForm.getLieuDeNaissance());
        player.setTelephone(playerForm.getTelephone());
        player.setResidence(playerForm.getResidence());

        // Gérer le fichier photo
        MultipartFile file = playerForm.getPhoto();
        if (!file.isEmpty()) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            player.setPhoto(filename);  // Assigner le nom du fichier à l'entité Player

            try {
                Path uploadPath = Paths.get(UPLOADED_FOLDER);
                Files.copy(file.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Mettre à jour le joueur dans la base de données
        playerService.savePlayer(player);
        return "redirect:/players";
    }



    @PostMapping("/uploadPhoto")
    public String uploadPlayerPhoto(@RequestParam("file") MultipartFile file, @RequestParam("idJoueur") Long idJoueur, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Veuillez sélectionner un fichier à uploader.");
            return "redirect:/players";
        }

        try {
            // Chemin où stocker les fichiers
            String uploadDirectory = System.getProperty("user.dir") + "/uploads/";

            // Nom du fichier avec le joueur
            String fileName = idJoueur + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDirectory + fileName);

            // Sauvegarder le fichier sur le disque
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Tu peux aussi associer ce nom de fichier à l'entité `Player` en l'enregistrant dans la base de données
            Player player = playerService.getPlayerById(idJoueur);
            player.setPhoto(fileName); // Il te faudra ajouter le champ `photo` dans la classe Player
            playerService.savePlayer(player);

            redirectAttributes.addFlashAttribute("message", "Photo uploadée avec succès : " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/players";
    }

}

