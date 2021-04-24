package com.ca.biere.local.quebec.gestion.ws.filter;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrixBiereFilter implements Serializable {

    private String prix;
    private Long idBiere;
}
