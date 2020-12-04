package com.ca.biere.local.quebec.gestion.ws.filter;

import java.io.Serializable;
import java.math.BigDecimal;

import com.ca.biere.local.quebec.commons.ws.enums.EnumAmertume;

public class BiereFilter implements Serializable {

	private String nom;
	private String origine;
	private BigDecimal tauxAlcoolStart;
	private BigDecimal tauxAlcoolEnd;
	private Integer ibuStart;
	private Integer ibuEnd;
	private EnumAmertume amertume;
	private String description;
	private Long idTypeBiere;
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getOrigine() {
		return origine;
	}
	public void setOrigine(String origine) {
		this.origine = origine;
	}
	public BigDecimal getTauxAlcoolStart() {
		return tauxAlcoolStart;
	}
	public void setTauxAlcoolStart(BigDecimal tauxAlcoolStart) {
		this.tauxAlcoolStart = tauxAlcoolStart;
	}
	public BigDecimal getTauxAlcoolEnd() {
		return tauxAlcoolEnd;
	}
	public void setTauxAlcoolEnd(BigDecimal tauxAlcoolEnd) {
		this.tauxAlcoolEnd = tauxAlcoolEnd;
	}
	public Integer getIbuStart() {
		return ibuStart;
	}
	public void setIbuStart(Integer ibuStart) {
		this.ibuStart = ibuStart;
	}
	public Integer getIbuEnd() {
		return ibuEnd;
	}
	public void setIbuEnd(Integer ibuEnd) {
		this.ibuEnd = ibuEnd;
	}
	public EnumAmertume getAmertume() {
		return amertume;
	}
	public void setAmertume(EnumAmertume amertume) {
		this.amertume = amertume;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getIdTypeBiere() {
		return idTypeBiere;
	}
	public void setIdTypeBiere(Long idTypeBiere) {
		this.idTypeBiere = idTypeBiere;
	}
	
}
