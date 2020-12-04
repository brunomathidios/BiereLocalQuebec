package com.ca.biere.local.quebec.gestion.ws.filter;

import java.io.Serializable;

public class TypeBiereFilter implements Serializable {

	private String nom;
	private String description;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
