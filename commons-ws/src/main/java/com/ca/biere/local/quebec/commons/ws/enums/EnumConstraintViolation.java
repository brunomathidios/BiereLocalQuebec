package com.ca.biere.local.quebec.commons.ws.enums;

import java.util.stream.Stream;

public enum EnumConstraintViolation {
	
	NOM_TYPE_BIERE_UNIQUE("nom_type_biere_unique", "Nom du type de bière déjà existe."),
	NOM_BIERE_UNIQUE("nom_biere_unique", "Nom de la bière déjà existe."),
	PRIX_BIERE_UNIQUE("prix_biere_unique", "Prix déjà existe pour la bière sélectionnée.");
	
	private String nomConstraint;
	private String message;
	
	private EnumConstraintViolation(String nomConstraint, String message) {
		this.nomConstraint = nomConstraint;
		this.message = message;
	}

	public String getNomConstraint() {
		return nomConstraint;
	}

	public void setNomConstraint(String nomConstraint) {
		this.nomConstraint = nomConstraint;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static EnumConstraintViolation getEnumByNomConstraint(String nomConstraint) {
		return Stream.of(EnumConstraintViolation.values())
			.filter(enumConstraint -> 
				enumConstraint.getNomConstraint().equals(nomConstraint)).findFirst().orElse(null);
		
	}
	
}
