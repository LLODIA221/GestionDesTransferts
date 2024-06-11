package com.Application.GestionDesTransferts.Controller;// PlayerController
import com.Application.GestionDesTransferts.Models.Association;
import com.Application.GestionDesTransferts.Models.Player;
import com.Application.GestionDesTransferts.Service.AssociationService;
import com.Application.GestionDesTransferts.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {
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

    @PostMapping
    public String savePlayer(@ModelAttribute Player player) {
        playerService.savePlayer(player);
        return "redirect:/players";
    }

    @GetMapping("/{id}/edit")
    public String editPlayerForm(@PathVariable Long id, Model model) {
        model.addAttribute("player", playerService.getPlayerById(id));
        List<Association> associations = associationService.getAllAssociations();
        model.addAttribute("associations", associations);
        return "editPlayer";
    }

    @PostMapping("/{id}")
    public String updatePlayer(@PathVariable Long id, @ModelAttribute Player player) {
        Player existingPlayer = playerService.getPlayerById(id);
        // Update the player fields with the new data
        playerService.savePlayer(existingPlayer);
        return "redirect:/players";
    }

    @GetMapping("/{id}/delete")
    public String deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return "redirect:/players";
    }
}

// Similarly, create controllers for Association, Transfer, and Zone