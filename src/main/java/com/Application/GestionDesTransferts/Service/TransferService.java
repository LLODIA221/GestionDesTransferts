package com.Application.GestionDesTransferts.Service;// TransferService
import com.Application.GestionDesTransferts.Models.Transfer;
import com.Application.GestionDesTransferts.Repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService {
    @Autowired
    private TransferRepository transferRepository;

    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    public Transfer getTransferById(Long id) {
        return transferRepository.findById(id).orElse(null);
    }

    public Transfer saveTransfer(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    public void deleteTransfer(Long id) {
        transferRepository.deleteById(id);
    }
}