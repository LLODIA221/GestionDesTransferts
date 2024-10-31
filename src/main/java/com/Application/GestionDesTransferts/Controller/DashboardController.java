package com.Application.GestionDesTransferts.Controller;

import com.Application.GestionDesTransferts.Dto.UserDto;
import com.Application.GestionDesTransferts.Repositories.*;
import com.Application.GestionDesTransferts.Service.LicenceService;
import com.Application.GestionDesTransferts.Service.UserService;
import com.Application.GestionDesTransferts.Service.ZoneLicenceStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private AssociationRepository associationRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private LicenceRepository licenceRepository;
    @Autowired
    private LicenceService licenceService;





    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        UserDto userDto = new UserDto();
        userDto.setEmail(principal.getName());

        // Statistiques existantes
        long nbEquipe = associationRepository.count();
        long nbJoueurs = playerRepository.count();
        long nbZone = zoneRepository.count();
        long transfer = transferRepository.count();

        // Nouvelles statistiques pour les licences
        long totalLicences = licenceRepository.count();
        long licencesValides = licenceService.countLicencesValides();
        long licencesExpirees = totalLicences - licencesValides;

        // Calcul du pourcentage de licences valides
        double pourcentageValides = totalLicences > 0
                ? (double) licencesValides / totalLicences * 100
                : 0;

        // Statistiques par zone
        List<ZoneLicenceStats> statsParZone = licenceService.getLicenceStatsByZone();

        // Données pour le graphique mensuel
        Map<String, Long> licencesParMois = licenceService.getLicencesParMois();

        model.addAttribute("role", userDetailsService.loadUserByUsername(principal.getName()).getAuthorities());
        model.addAttribute("user", userDto);
        model.addAttribute("username", userDto.getFullname());
        model.addAttribute("nbEquipe", nbEquipe);
        model.addAttribute("nbJoueurs", nbJoueurs);
        model.addAttribute("nbZone", nbZone);
        model.addAttribute("transfer", transfer);

        // Ajout des attributs pour les licences
        model.addAttribute("totalLicences", totalLicences);
        model.addAttribute("licencesValides", licencesValides);
        model.addAttribute("licencesExpirees", licencesExpirees);
        model.addAttribute("pourcentageValides", String.format("%.1f", pourcentageValides));
        model.addAttribute("statsParZone", statsParZone);
        model.addAttribute("licencesParMois", licencesParMois);

        return "dashboard";
    }
}
