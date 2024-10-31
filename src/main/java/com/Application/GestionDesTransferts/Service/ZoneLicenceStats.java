package com.Application.GestionDesTransferts.Service;

import jdk.jfr.SettingDefinition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZoneLicenceStats {
    private String nomZone;
    private long totalLicences;
    private long licencesValides;
    private long licencesExpirees;

    public ZoneLicenceStats(String nomZone, long totalLicences, long licencesValides, long licencesExpirees) {
        this.nomZone = nomZone;
        this.totalLicences = totalLicences;
        this.licencesValides = licencesValides;
        this.licencesExpirees = licencesExpirees;
    }

    // Getters
    public String getNomZone() { return nomZone; }
    public long getTotalLicences() { return totalLicences; }
    public long getLicencesValides() { return licencesValides; }
    public long getLicencesExpirees() { return licencesExpirees; }
}