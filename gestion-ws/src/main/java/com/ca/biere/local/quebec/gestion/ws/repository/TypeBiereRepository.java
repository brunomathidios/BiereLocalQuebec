package com.ca.biere.local.quebec.gestion.ws.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ca.biere.local.quebec.commons.ws.entite.TypeBiere;
import com.ca.biere.local.quebec.commons.ws.repository.base.BaseRepository;

@Repository
public interface TypeBiereRepository extends BaseRepository<TypeBiere> {

	Optional<TypeBiere> findByNom(String nom);
}
