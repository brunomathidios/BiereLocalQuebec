package com.ca.biere.local.quebec.commons.ws.entite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "type_biere")
@SequenceGenerator(name = "type_biere_id_seq", sequenceName = "type_biere_id_seq", allocationSize = 1, initialValue = 1)
public class TypeBiere extends BaseEntite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_biere_id_seq")
	@Column(name = "id_type_biere")
	private Long idTypeBiere;
	
	@NotBlank(message = "Le champ 'nom' est obligatoire")
	@Size(max = 200, message = "Le champ 'nom' doit avoir au maximum 200 caractères")
	@Column(name = "nom")
	private String nom;
	
	@Size(max = 4000, message = "Le champ 'description' doit avoir au maximum 4000 caractères")
	@Column(name = "description")
	private String description;

	public Long getIdTypeBiere() {
		return idTypeBiere;
	}

	public void setIdTypeBiere(Long idTypeBiere) {
		this.idTypeBiere = idTypeBiere;
	}

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
