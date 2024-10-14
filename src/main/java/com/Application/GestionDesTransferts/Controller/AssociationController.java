package com.Application.GestionDesTransferts.Controller;// AssociationController
import com.Application.GestionDesTransferts.Models.Association;
import com.Application.GestionDesTransferts.Models.Zone;
import com.Application.GestionDesTransferts.Service.AssociationService;
import com.Application.GestionDesTransferts.Service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/associations")
public class AssociationController {
    @Autowired
    private AssociationService associationService;
    @Autowired
    private ZoneService zoneService;
    @GetMapping
    public String getAllAssociations(Model model) {
        model.addAttribute("associations", associationService.getAllAssociations());
        return "associations";
    }

    @GetMapping("/{id}")
    public String getAssociationById(@PathVariable Long id, Model model) {
        model.addAttribute("association", associationService.getAssociationById(id));
        return "association";
    }

    @GetMapping("/new")
    public String createAssociationForm(Model model) {
        model.addAttribute("association", new Association());
        List<Zone> zones = zoneService.getAllZones();
        model.addAttribute("zones", zones);
        return "createAssociation";
    }

    @PostMapping
    public String saveAssociation(@ModelAttribute Association association) {
        associationService.saveAssociation(association);
        return "redirect:/associations";
    }

    @GetMapping("/{id}/edit")
    public String editAssociationForm(@PathVariable Long id, Model model) {
        model.addAttribute("association", associationService.getAssociationById(id));
        List<Zone> zones = zoneService.getAllZones();
        model.addAttribute("zones", zones);
        return "editAssociation";
    }

    @PostMapping("/{id}")
    public String updateAssociation(@PathVariable Long id, @ModelAttribute Association association) {
        Association existingAssociation = associationService.getAssociationById(id);
        // Update the association fields with the new data
        existingAssociation.setNomAssociation(association.getNomAssociation());
        existingAssociation.setZone(association.getZone());
        associationService.saveAssociation(existingAssociation);
        return "redirect:/associations";
    }

//    @GetMapping("/{id}/delete")
//    public String deleteAssociation(@PathVariable Long id) {
//        associationService.deleteAssociation(id);
//        return "redirect:/associations";
//    }
//
        @PostMapping("/{id}/delete")
        public String deleteAssociation(@PathVariable Long id) {
            associationService.deleteAssociation(id);
            return "redirect:/associations"; // Redirect after deletion
}


}