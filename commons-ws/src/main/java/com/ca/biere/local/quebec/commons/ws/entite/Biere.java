package com.ca.biere.local.quebec.commons.ws.entite;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ca.biere.local.quebec.commons.ws.enums.EnumAmertume;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "biere")
@SequenceGenerator(name = "biere_id_seq", sequenceName = "biere_id_seq", allocationSize = 1, initialValue = 1)
public class Biere extends BaseEntite {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "biere_id_seq")
	@Column(name = "id_biere")
	private Long idBiere;
	
	@NotBlank(message = "Le champ 'nom' est obligatoire")
	@Size(max = 150, message = "Le champ 'nom' doit avoir au maximum 150 caractères")
	@Column(name = "nom")
	private String nom;
	
	@NotBlank(message = "Le champ 'origine' est obligatoire")
	@Size(max = 200, message = "Le champ 'origine' doit avoir au maximum 200 caractères")
	@Column(name = "origine")
	private String origine;
	
	@NotNull(message = "Le champ 'taux d'alcool' est obligatoire")
	@Column(name = "taux_alcool")
	private BigDecimal tauxAlcool;
	
	@NotNull(message = "Le champ 'ibu' est obligatoire")
	@Column(name = "ibu")
	private Integer ibu;
	
	@NotNull(message = "Le cham 'amertume' est obligatoire")
	@Column(name = "amertume")
	@Enumerated(EnumType.STRING)
	private EnumAmertume amertume;
	
	@Size(max = 4000, message = "Le champ 'description' doit avoir au maximum 4000 caractères")
	@Column(name = "description")
	private String description;
	
	@Column(name = "photo")
	private byte[] photo;
	
	@JsonProperty(access = Access.READ_ONLY)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_type_biere", updatable = false, insertable = false)
	private TypeBiere typeBiere;
	
	@NotNull(message = "Le cham 'type de bière' est obligatoire")
	@Column(name = "id_type_biere")
	private Long idTypeBiere;

	public Long getIdBiere() {
		return idBiere;
	}

	public void setIdBiere(Long idBiere) {
		this.idBiere = idBiere;
	}

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

	public BigDecimal getTauxAlcool() {
		return tauxAlcool;
	}

	public void setTauxAlcool(BigDecimal tauxAlcool) {
		this.tauxAlcool = tauxAlcool;
	}

	public Integer getIbu() {
		return ibu;
	}

	public void setIbu(Integer ibu) {
		this.ibu = ibu;
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

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public TypeBiere getTypeBiere() {
		return typeBiere;
	}

	public void setTypeBiere(TypeBiere typeBiere) {
		this.typeBiere = typeBiere;
	}

	public Long getIdTypeBiere() {
		return idTypeBiere;
	}

	public void setIdTypeBiere(Long idTypeBiere) {
		this.idTypeBiere = idTypeBiere;
	}
}
