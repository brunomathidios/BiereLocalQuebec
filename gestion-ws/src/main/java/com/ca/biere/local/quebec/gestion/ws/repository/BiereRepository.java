package com.ca.biere.local.quebec.gestion.ws.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ca.biere.local.quebec.commons.ws.entite.Biere;
import com.ca.biere.local.quebec.commons.ws.repository.base.BaseRepository;

@Repository
public interface BiereRepository extends BaseRepository<Biere> {

	@Query("SELECT CASE WHEN COUNT(b.idBiere) > 0 THEN 1 ELSE 0 END "
			+ "FROM Biere b WHERE b.idTypeBiere = :idTypeBiere")
	Integer existsBiereByIdTypeBiere(@Param("idTypeBiere") Long idTypeBiere);
}
