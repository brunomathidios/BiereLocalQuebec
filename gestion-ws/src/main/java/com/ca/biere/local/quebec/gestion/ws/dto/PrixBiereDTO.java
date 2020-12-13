package com.ca.biere.local.quebec.gestion.ws.dto;

import java.io.Serializable;

public class PrixBiereDTO implements Serializable {

    private Long idPrix;
    private String prix;
    private String nomBiere;

    public PrixBiereDTO(Long idPrix, String prix, String nomBiere) {
        this.idPrix = idPrix;
        this.prix = prix;
        this.nomBiere = nomBiere;
    }

    public Long getIdPrix() {
        return idPrix;
    }

    public void setIdPrix(Long idPrix) {
        this.idPrix = idPrix;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getNomBiere() {
        return nomBiere;
    }

    public void setNomBiere(String nomBiere) {
        this.nomBiere = nomBiere;
    }
}
