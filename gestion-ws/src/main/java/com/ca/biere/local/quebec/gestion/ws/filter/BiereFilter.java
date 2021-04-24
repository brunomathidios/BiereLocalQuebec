package com.ca.biere.local.quebec.gestion.ws.filter;

import com.ca.biere.local.quebec.commons.ws.enums.EnumAmertume;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
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
}
