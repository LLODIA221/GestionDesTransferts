package com.Application.GestionDesTransferts.Controller;

import com.Application.GestionDesTransferts.Dto.UserDto;
import com.Application.GestionDesTransferts.Repositories.AssociationRepository;
import com.Application.GestionDesTransferts.Repositories.PlayerRepository;
import com.Application.GestionDesTransferts.Repositories.TransferRepository;
import com.Application.GestionDesTransferts.Repositories.ZoneRepository;
import com.Application.GestionDesTransferts.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
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


@GetMapping("/dashboard")
public String showDashboard(Model model , Principal principal) {
    UserDto userDto = new UserDto();
    userDto.setEmail(principal.getName());

    // compter le nombre d'equipe
    long nbEquipe = associationRepository.count();
    //compter le nombre de joueurs
    long nbJoueurs = playerRepository.count();

    //compter le nombre de zone
    long nbZone = zoneRepository.count();

    //le nombre de transfert par mois
    long transfer = transferRepository.count();

    //recuperation du role de l'utilisateurteur
    model.addAttribute("role", userDetailsService.loadUserByUsername(principal.getName()).getAuthorities());
    model.addAttribute("user", userDto);
    model.addAttribute("username", userDto.getFullname());
    model.addAttribute("nbEquipe", nbEquipe);
    model.addAttribute("nbJoueurs", nbJoueurs);
    model.addAttribute("nbZone",nbZone);
    model.addAttribute("transfer",transfer);

    return "dashboard";
}
}
