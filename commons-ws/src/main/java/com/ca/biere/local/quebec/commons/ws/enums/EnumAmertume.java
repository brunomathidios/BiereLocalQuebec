package com.ca.biere.local.quebec.commons.ws.enums;

import java.util.stream.Stream;

import com.ca.biere.local.quebec.commons.ws.dto.AmertumeDTO;

public enum EnumAmertume {

	FAIBLE("Faible"), MOYENNE("Moyenne"), FORTE("Forte");
	
	String description;
	
	EnumAmertume(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static EnumAmertume of(String description) {
        return Stream.of(EnumAmertume.values())
          .filter(p -> p.getDescription() == description)
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
	
	public AmertumeDTO getAmertumeDTO() {
		return new AmertumeDTO(this.name(), this.getDescription());
	}
}
