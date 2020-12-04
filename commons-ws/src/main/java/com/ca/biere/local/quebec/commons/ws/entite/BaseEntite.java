package com.ca.biere.local.quebec.commons.ws.entite;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseEntite {
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_creation")
	private Date dateCreation;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_mis_a_jour")
	private Date dateMisAJour;

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateMisAJour() {
		return dateMisAJour;
	}

	public void setDateMisAJour(Date dateMisAJour) {
		this.dateMisAJour = dateMisAJour;
	}
	
	@PrePersist
	public void prePersist() {
		this.dateCreation = new Date();
	}
	
	@PreUpdate
	public void preUpdate() {
		this.dateMisAJour = new Date();
	}
	
}
