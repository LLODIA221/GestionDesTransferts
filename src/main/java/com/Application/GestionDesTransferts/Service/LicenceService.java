package com.Application.GestionDesTransferts.Service;

import com.Application.GestionDesTransferts.Dto.LicenceDTO;
import com.Application.GestionDesTransferts.Models.Licence;
import com.Application.GestionDesTransferts.Models.Player;
import com.Application.GestionDesTransferts.Models.Zone;
import com.Application.GestionDesTransferts.Repositories.LicenceRepository;
import com.Application.GestionDesTransferts.Repositories.PlayerRepository;
import com.Application.GestionDesTransferts.Repositories.ZoneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class LicenceService {
    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private PlayerRepository playerRepository;

    // ========== OPÉRATIONS CRUD DE BASE ==========

    public LicenceDTO createLicence(Long playerId, String dateDelivrance) {
        // Validation des entrées
        if (playerId == null || dateDelivrance == null) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être null");
        }

        // Vérification de l'existence d'une licence
        if (licenceRepository.existsByJoueurIdJoueur(playerId)) {
            throw new RuntimeException("Une licence existe déjà pour ce joueur");
        }

        // Récupération et validation du joueur
        Player joueur = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Joueur non trouvé"));

        // Création de la licence
        Licence licence = new Licence();
        licence.setJoueur(joueur);
        licence.setZone(joueur.getAssociation().getZone());
        licence.setDateDelivrance(dateDelivrance);
        licence.genererNumeroLicence();
        licence.setPhoto(joueur.getPhoto());

        return mapToDTO(licenceRepository.save(licence));
    }

    public LicenceDTO getLicenceById(Long licenceId) {
        return licenceRepository.findById(licenceId)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Licence non trouvée"));
    }

    public List<LicenceDTO> getAllLicences() {
        return licenceRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public LicenceDTO updateLicence(Long licenceId, LicenceDTO licenceDTO) {
        Licence licence = licenceRepository.findById(licenceId)
                .orElseThrow(() -> new RuntimeException("Licence non trouvée"));

        // Mise à jour des champs modifiables
        licence.setDateDelivrance(licenceDTO.getDateDelivrance());

        return mapToDTO(licenceRepository.save(licence));
    }

    public void deleteLicence(Long licenceId) {
        if (!licenceRepository.existsById(licenceId)) {
            throw new RuntimeException("Licence non trouvée");
        }
        licenceRepository.deleteById(licenceId);
    }

    // ========== OPÉRATIONS SPÉCIFIQUES ==========

    public LicenceDTO renouvelerLicence(Long licenceId, String nouvelleDateDelivrance) {
        if (nouvelleDateDelivrance == null) {
            throw new IllegalArgumentException("La nouvelle date ne peut pas être null");
        }

        Licence licence = licenceRepository.findById(licenceId)
                .orElseThrow(() -> new RuntimeException("Licence non trouvée"));

        // Validation de la nouvelle date
        try {
            LocalDate.parse(nouvelleDateDelivrance);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format de date invalide");
        }

        licence.setDateDelivrance(nouvelleDateDelivrance);
        licence.genererNumeroLicence();

        return mapToDTO(licenceRepository.save(licence));
    }

    // ========== MÉTHODES DE VALIDATION ==========

    public boolean isLicenceValide(Long licenceId) {
        Licence licence = licenceRepository.findById(licenceId)
                .orElseThrow(() -> new RuntimeException("Licence non trouvée"));
        return isLicenceValide(licence);
    }

    private boolean isLicenceValide(Licence licence) {
        try {
            LocalDate dateDelivrance = LocalDate.parse(licence.getDateDelivrance());
            LocalDate dateExpiration = dateDelivrance.plusYears(1);
            return LocalDate.now().isBefore(dateExpiration);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // ========== MÉTHODES DE STATISTIQUES ==========

    public long countLicencesValides() {
        return licenceRepository.findAll().stream()
                .filter(this::isLicenceValide)
                .count();
    }

    public List<ZoneLicenceStats> getLicenceStatsByZone() {
        List<ZoneLicenceStats> stats = new ArrayList<>();
        List<Zone> zones = zoneRepository.findAll();

        for (Zone zone : zones) {
            List<Licence> licencesZone = licenceRepository.findByZone(zone);
            long total = licencesZone.size();
            long valides = licencesZone.stream()
                    .filter(this::isLicenceValide)
                    .count();

            stats.add(new ZoneLicenceStats(
                    zone.getNomZone(),
                    total,
                    valides,
                    total - valides
            ));
        }

        return stats;
    }

    public Map<String, Long> getLicencesParMois() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH);

        return licenceRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        licence -> {
                            try {
                                LocalDate date = LocalDate.parse(licence.getDateDelivrance());
                                return date.format(formatter);
                            } catch (DateTimeParseException e) {
                                return "Date invalide";
                            }
                        },
                        Collectors.counting()
                ));
    }

    // ========== MÉTHODES DE MAPPING ==========

    private LicenceDTO mapToDTO(Licence licence) {
        LicenceDTO dto = new LicenceDTO();
        dto.setIdLicence(licence.getIdLicence());
        dto.setNomJoueur(licence.getNomJoueur());
        dto.setZone(licence.getZone().getNomZone());
        dto.setDateDeNaissance(licence.getDateDeNaissance());
        dto.setLieuDeNaissance(licence.getLieuDeNaissance());
        dto.setNumeroIdentite(licence.getNumeroIdentite());
        dto.setNumeroLicence(licence.getNumeroLicence());
        dto.setDateDelivrance(licence.getDateDelivrance());
        dto.setPhoto(licence.getPhoto());
        return dto;
    }
}