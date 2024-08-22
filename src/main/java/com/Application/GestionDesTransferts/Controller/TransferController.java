package com.Application.GestionDesTransferts.Controller;// TransferController
import com.Application.GestionDesTransferts.Models.Association;
import com.Application.GestionDesTransferts.Models.Player;
import com.Application.GestionDesTransferts.Models.Transfer;

import com.Application.GestionDesTransferts.Service.AssociationService;
import com.Application.GestionDesTransferts.Service.PlayerService;
import com.Application.GestionDesTransferts.Service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transfers")
public class TransferController {
    @Autowired
    private TransferService transferService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private  AssociationService associationService;


    @GetMapping
    public String getAllTransfers(Model model) {
        model.addAttribute("transfers", transferService.getAllTransfers());
        return "transfers";
    }

    @GetMapping("/{id}")
    public String getTransferById(@PathVariable Long id, Model model) {
        model.addAttribute("transfer", transferService.getTransferById(id));
        return "transfer";
    }

    @GetMapping("/new")
    public String createTransferForm(Model model) {
        model.addAttribute("transfer", new Transfer());
        List<Player> players = playerService.getAllPlayers();
        model.addAttribute("players", playerService.getAllPlayers());
        List<Association> associations = associationService.getAllAssociations();
        model.addAttribute("associations", associationService.getAllAssociations());
        return "createTransfer";
    }

    @PostMapping
    public String saveTransfer(@ModelAttribute Transfer transfer) {
        transferService.saveTransfer(transfer);
        return "redirect:/transfers";
    }

    @GetMapping("/{id}/edit")
    public String editTransferForm(@PathVariable Long id, Model model) {
        model.addAttribute("transfer", transferService.getTransferById(id));
        List<Player> players = playerService.getAllPlayers();
        model.addAttribute("players", playerService.getAllPlayers());
        List<Association> associations = associationService.getAllAssociations();
        model.addAttribute("associations", associationService.getAllAssociations());
        return "editTransfer";
    }

    @PostMapping("/{id}")
    public String updateTransfer(@PathVariable Long id, @ModelAttribute Transfer transfer) {
        Transfer existingTransfer = transferService.getTransferById(id);
        // Update the transfer fields with the new data
        existingTransfer.setJoueur(transfer.getJoueur());
        existingTransfer.setAssociationArrive(transfer.getAssociationArrive());
        existingTransfer.setAssociationDepart(transfer.getAssociationDepart());
        existingTransfer.setDateTransfert(transfer.getDateTransfert());
        //update
        transferService.saveTransfer(existingTransfer);
        return "redirect:/transfers";
    }

    @GetMapping("/{id}/delete")
    public String deleteTransfer(@PathVariable Long id) {
        transferService.deleteTransfer(id);
        return "redirect:/transfers";
    }
}