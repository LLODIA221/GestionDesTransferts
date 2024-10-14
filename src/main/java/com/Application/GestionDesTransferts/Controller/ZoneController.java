package com.Application.GestionDesTransferts.Controller;// ZoneController
import com.Application.GestionDesTransferts.Models.Association;
import com.Application.GestionDesTransferts.Models.Zone;

import com.Application.GestionDesTransferts.Service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/zones")
public class ZoneController {
    @Autowired
    private ZoneService zoneService;

    @GetMapping
    public String getAllZones(Model model) {
        model.addAttribute("zones", zoneService.getAllZones());
        return "zones";
    }

    @GetMapping("/{id}")
    public String getZoneById(@PathVariable Long id, Model model) {
        model.addAttribute("zone", zoneService.getZoneById(id));
        return "zone";
    }

    @GetMapping("/new")
    public String createZoneForm(Model model) {
        model.addAttribute("zone", new Zone());
        return "createZone";
    }

    @PostMapping
    public String saveZone(@ModelAttribute Zone zone) {
        zoneService.saveZone(zone);
        return "redirect:/zones";
    }

    @GetMapping("/{id}/edit")
    public String editZoneForm(@PathVariable Long id, Model model) {
        model.addAttribute("zone", zoneService.getZoneById(id));
        List<Zone> zones = zoneService.getAllZones();
        model.addAttribute("zones", zones);
        return "editZone";
    }

    @PostMapping("/{id}")
    public String updateZone(@PathVariable Long id, @ModelAttribute Zone zone) {
        Zone existingZone = zoneService.getZoneById(id);
        //mise à jour des données

        existingZone.setNomZone(zone.getNomZone());
        // Update the zone fields with the new data
        zoneService.saveZone(existingZone);
        return "redirect:/zones";
    }

    @PostMapping("/{id}/delete")
    public String deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return "redirect:/zones";
    }
}