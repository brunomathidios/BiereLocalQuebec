package com.ca.biere.local.quebec.gestion.ws.filter;

import java.io.Serializable;

public class PrixBiereFilter implements Serializable {

    private String prix;
    private Long idBiere;

    public String getPrix() {
        return this.prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public Long getIdBiere() {
        return idBiere;
    }

    public void setIdBiere(Long idBiere) {
        this.idBiere = idBiere;
    }
}
