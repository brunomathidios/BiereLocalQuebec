package com.ca.biere.local.quebec.gestion.ws.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrixBiereDTO implements Serializable {

    private Long idPrix;
    private String prix;
    private String nomBiere;

    public PrixBiereDTO(Long idPrix, String prix, String nomBiere) {
        this.idPrix = idPrix;
        this.prix = prix;
        this.nomBiere = nomBiere;
    }
}
