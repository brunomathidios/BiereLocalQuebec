package com.ca.biere.local.quebec.gestion.ws.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ca.biere.local.quebec.commons.ws.entite.TypeBiere;
import com.ca.biere.local.quebec.commons.ws.exception.ResourceNotFoundException;
import com.ca.biere.local.quebec.commons.ws.exception.SaveEntiteException;
import com.ca.biere.local.quebec.commons.ws.exception.ValidationException;
import com.ca.biere.local.quebec.commons.ws.service.BaseEntiteService;
import com.ca.biere.local.quebec.gestion.ws.filter.TypeBiereFilter;
import com.ca.biere.local.quebec.gestion.ws.repository.TypeBiereRepository;

@Service
public class TypeBiereService extends BaseEntiteService<TypeBiere>{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment environment;

	@Autowired
	private TypeBiereRepository repository;
	
	@Autowired
	private BiereService biereService;
	
	public TypeBiereService(Validator validator) {
		this.setValidator(validator);
	}
	
	public Page<TypeBiere> listerTypeBiere(TypeBiereFilter filter, Pageable page) {
		try {
			CriteriaBuilder cb = this.repository.getCriteriaBuilder();
			CriteriaQuery<TypeBiere> query = cb.createQuery(TypeBiere.class);
			CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
			
			Root<TypeBiere> root = query.from(TypeBiere.class);
			Root<TypeBiere> rootCount = queryCount.from(TypeBiere.class);
			
			query.where(this.getWhere(root, filter, cb));
			
			queryCount.select(cb.count(rootCount));
			queryCount.where(this.getWhere(rootCount, filter, cb));
			
			List<TypeBiere> result = this.repository.findCriteriaQuery(query, page, root, cb);
			Long resultCount = this.repository.executeCriteriaQuery( queryCount );
			
			return new PageImpl<>( result, page, resultCount );
			
		} catch (Exception e) {
			log.error("Erreur pour recuperer les instances de TypeBiere", e);
			throw e;
		}
	}
	
	private Predicate getWhere(Root<TypeBiere> root, TypeBiereFilter filter, CriteriaBuilder cb) {
		Predicate where = cb.conjunction();
		
		if(StringUtils.isNotBlank(filter.getNom())) {
			where = cb.and( where, cb.like(cb.upper(root.get("nom")), "%" + filter.getNom().toUpperCase() + "%") );
		}
		
		if(StringUtils.isNotBlank(filter.getDescription())) {
			where = cb.and( where, cb.like(cb.upper(root.get("description")), "%" + filter.getDescription().toUpperCase() + "%") );
		}
		
		return where;
	}
	
	@Override
	public TypeBiere insert(TypeBiere typeBiere) {
		
		if(Optional.ofNullable(typeBiere.getIdTypeBiere()).isPresent()) {
			String erreur = this.environment.getRequiredProperty("message.error.methode.insert");
			throw new SaveEntiteException(erreur);
		}
		
		this.valideEntite(typeBiere);
		
		try {
			return this.repository.saveAndFlush(typeBiere);
		} catch (Exception e) {
			log.error("Erreur pendand l'insertion d'une instance TypeBiere", e);
			throw e;
		} 
	}

	@Override
	public TypeBiere update(TypeBiere typeBiere) {
		try {
			
			if(! Optional.ofNullable(typeBiere.getIdTypeBiere()).isPresent()) {
				String erreur = this.environment.getRequiredProperty("message.error.methode.update");
				throw new SaveEntiteException(erreur);
			}
			
			if(Optional.ofNullable(this.findById(typeBiere.getIdTypeBiere())).isPresent()) {

				this.valideEntite(typeBiere);
				return this.repository.saveAndFlush(typeBiere);
				
			} else {
				throw new ResourceNotFoundException(
						"Entité " + typeBiere.getIdTypeBiere() + " du type TypeBiere n'a pas été trouvée");
			}
			
		} catch (Exception e) {
			log.error("Erreur pendand mis a jour d'une instance TypeBiere", e);
			throw e;
		}
	}
	
	public TypeBiere findById(Long id) {
		try {
			return this.repository.findById(id).orElse(null);
		} catch (Exception e) {
			log.error("Erreur pendand findById TypeBiere", e);
			throw e;
		}
	}
	
	public void deleteById(Long id) {
		try {
			this.repository.findById(id).ifPresentOrElse(
				entite -> {
					if(! this.biereService.existsBiereByIdTypeBiere(id)) {
						this.repository.delete(entite);						
					} else {
						throw new ValidationException("Interdit de supprimer cet enregistrement, "
								+ "il possède d'autres bières associées à lui.");
					}
				}, 
				() -> {
					throw new ResourceNotFoundException(
							"Entité " + id + " du type TypeBiere n'a pas été trouvée");
			});
		} catch (Exception e) {
			log.error("Erreur pendand deleteById TypeBiere", e);
			throw e;
		}
	}

	public List<TypeBiere> listerTypeBiereByNom(String nom) {
		try {
			CriteriaBuilder cb = this.repository.getCriteriaBuilder();
			CriteriaQuery<TypeBiere> query = cb.createQuery(TypeBiere.class);
			Root<TypeBiere> root = query.from(TypeBiere.class);
			query.where(this.getWhereNom(root, nom, cb));

			List<TypeBiere> result = this.repository.getEntityManager()
					.createQuery( query ).getResultList();

			return result;

		} catch (Exception e) {
			log.error("Erreur pour recuperer les instances de TypeBiere by Nom", e);
			throw e;
		}
	}

	private Predicate getWhereNom(Root<TypeBiere> root, String nom, CriteriaBuilder cb) {
		Predicate where = cb.conjunction();

		if(StringUtils.isNotBlank(nom)) {
			where = cb.and( where, cb.like(cb.upper(root.get("nom")), "%" + nom.toUpperCase() + "%") );
		}

		return where;
	}
}
