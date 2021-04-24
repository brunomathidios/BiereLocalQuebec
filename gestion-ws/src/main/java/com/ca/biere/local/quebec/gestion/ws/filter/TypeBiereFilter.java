package com.ca.biere.local.quebec.gestion.ws.filter;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeBiereFilter implements Serializable {

	private String nom;
	private String description;
	
}
