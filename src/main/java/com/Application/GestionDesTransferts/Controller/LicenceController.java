package com.Application.GestionDesTransferts.Controller;

import com.Application.GestionDesTransferts.Dto.LicenceDTO;
import com.Application.GestionDesTransferts.Service.LicenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/licences")
public class LicenceController {

    @Autowired
    private LicenceService licenceService;

    // Liste des licences
    @GetMapping
    public String listLicences(Model model) {
        model.addAttribute("licences", licenceService.getAllLicences());
        return "licence/list";
    }

   

    // Formulaire de création
    @GetMapping("/create")
    public String createLicenceForm(@RequestParam Long playerId, Model model) {
        model.addAttribute("playerId", playerId);
        return "licence/create";
    }

    // Créer une licence
    @PostMapping("/create")
    public String createLicence(@RequestParam Long playerId,
                                @RequestParam String dateDelivrance,
                                RedirectAttributes redirectAttributes) {
        try {
            LicenceDTO licence = licenceService.createLicence(playerId, dateDelivrance);
            redirectAttributes.addFlashAttribute("success", "Licence créée avec succès");
            return "redirect:/licences/" + licence.getIdLicence();
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/players/" + playerId;
        }
    }
    @GetMapping("/generate/{playerId}")
    public String generateLicence(@PathVariable Long playerId, Model model) {
        try {
            // Générer la date de délivrance actuelle
            String dateDelivrance = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

            // Créer la licence
            LicenceDTO licence = licenceService.createLicence(playerId, dateDelivrance);

            // Rediriger vers la page de détails de la licence
            return "redirect:/licences/" + licence.getIdLicence();
        } catch (RuntimeException e) {
            // En cas d'erreur, rediriger vers la page du joueur avec un message d'erreur
            model.addAttribute("error", e.getMessage());
            return "redirect:/players/" + playerId;
        }
    }

    // Méthode pour afficher les détails de la licence
    @GetMapping("/{id}")
    public String showLicence(@PathVariable Long id, Model model) {
        try {
            LicenceDTO licence = licenceService.getLicenceById(id);
            model.addAttribute("licence", licence);
//            return "licence/details";
            return "licence/details";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/licences";
        }
    }

    // Formulaire de renouvellement
    @GetMapping("/{id}/renouveler")
    public String renouvelerLicenceForm(@PathVariable Long id, Model model) {
        model.addAttribute("licence", licenceService.getLicenceById(id));
        return "licence/renouveler";
    }

    // Renouveler une licence
    @PostMapping("/{id}/renouveler")
    public String renouvelerLicence(@PathVariable Long id,
                                    @RequestParam String nouvelleDateDelivrance,
                                    RedirectAttributes redirectAttributes) {
        try {
            licenceService.renouvelerLicence(id, nouvelleDateDelivrance);
            redirectAttributes.addFlashAttribute("success", "Licence renouvelée avec succès");
            return "redirect:/licences/" + id;
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/licences/" + id;
        }
    }

    // Supprimer une licence
    @PostMapping("/{id}/delete")
    public String deleteLicence(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            licenceService.deleteLicence(id);
            redirectAttributes.addFlashAttribute("success", "Licence supprimée avec succès");
            return "redirect:/licences";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/licences/" + id;
        }
    }
}