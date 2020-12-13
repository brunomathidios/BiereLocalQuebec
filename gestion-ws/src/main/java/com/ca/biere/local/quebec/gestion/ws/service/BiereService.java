package com.ca.biere.local.quebec.gestion.ws.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.*;
import javax.validation.Validator;

import com.ca.biere.local.quebec.commons.ws.entite.TypeBiere;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ca.biere.local.quebec.commons.ws.entite.Biere;
import com.ca.biere.local.quebec.commons.ws.exception.ResourceNotFoundException;
import com.ca.biere.local.quebec.commons.ws.exception.SaveEntiteException;
import com.ca.biere.local.quebec.commons.ws.service.BaseEntiteService;
import com.ca.biere.local.quebec.gestion.ws.filter.BiereFilter;
import com.ca.biere.local.quebec.gestion.ws.repository.BiereRepository;

@Service
public class BiereService extends BaseEntiteService<Biere> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BiereRepository repository;

	@Autowired
	private Environment environment;
	
	public BiereService(Validator validator) {
		this.setValidator(validator);
	}

	@Override
	public Biere insert(Biere biere) {
		if (Optional.ofNullable(biere.getIdBiere()).isPresent()) {
			String erreur = this.environment.getRequiredProperty("message.error.methode.insert");
			throw new SaveEntiteException(erreur);
		}

		this.valideEntite(biere);

		try {
			return this.repository.save(biere);
		} catch (Exception e) {
			log.error("Erreur pendand l'insertion d'une instance Biere", e);
			throw e;
		}
	}

	@Override
	public Biere update(Biere biere) {
		try {

			if (!Optional.ofNullable(biere.getIdBiere()).isPresent()) {
				String erreur = this.environment.getRequiredProperty("message.error.methode.update");
				throw new SaveEntiteException(erreur);
			}

			if (Optional.ofNullable(this.findById(biere.getIdBiere())).isPresent()) {

				this.valideEntite(biere);
				return this.repository.save(biere);

			} else {
				throw new ResourceNotFoundException(
						"Entité " + biere.getIdBiere() + " du type Biere n'a pas été trouvée");
			}

		} catch (Exception e) {
			log.error("Erreur pendand mis a jour d'une instance TypeBiere", e);
			throw e;
		}
	}
	
	public Biere findById(Long id) {
		try {
			return this.repository.findById(id).orElse(null);
		} catch (Exception e) {
			log.error("Erreur pendand findById Biere", e);
			throw e;
		}
	}
	
	public boolean existsBiereByIdTypeBiere(Long idTypeBiere) {
		try {
			return this.repository.existsBiereByIdTypeBiere(idTypeBiere) == 1;
		} catch (Exception e) {
			log.error("Erreur pendand existsBiereByIdTypeBiere " + idTypeBiere, e);
			throw e;
		}
	}
	
	public void deleteById(Long id) {
		try {
			this.repository.findById(id).ifPresentOrElse(
				entite -> {
					this.repository.delete(entite);
				}, 
				() -> {
					throw new ResourceNotFoundException(
							"Entité " + id + " du type Biere n'a pas été trouvée");
			});
		} catch (Exception e) {
			log.error("Erreur pendand deleteById Biere", e);
			throw e;
		}
	}
	
	public Page<Biere> listerBiere(BiereFilter filter, Pageable page) {
		try {
			CriteriaBuilder cb = this.repository.getCriteriaBuilder();
			CriteriaQuery<Biere> query = cb.createQuery(Biere.class);
			CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);
			
			Root<Biere> root = query.from(Biere.class);
			root.fetch("typeBiere", JoinType.LEFT);
			Root<Biere> rootCount = queryCount.from(Biere.class);
			
			query.where(this.getWhere(root, filter, cb));
			
			queryCount.select(cb.count(rootCount));
			queryCount.where(this.getWhere(rootCount, filter, cb));
			
			List<Biere> result = this.repository.findCriteriaQuery(query, page, root, cb);
			Long resultCount = this.repository.executeCriteriaQuery( queryCount );
			
			return new PageImpl<>( result, page, resultCount );
			
		} catch (Exception e) {
			log.error("Erreur pour recuperer les instances de Biere", e);
			throw e;
		}
	}
	
	private Predicate getWhere(Root<Biere> root, BiereFilter filter, CriteriaBuilder cb) {
		Predicate where = cb.conjunction();
		
		if(StringUtils.isNotBlank(filter.getNom())) {
			where = cb.and( where, cb.like(cb.upper(root.get("nom")), "%" + filter.getNom().toUpperCase() + "%") );
		}
		
		if(StringUtils.isNotBlank(filter.getOrigine())) {
			where = cb.and( where, cb.like(cb.upper(root.get("origine")), "%" + filter.getOrigine().toUpperCase() + "%") );
		}
		
		if(filter.getTauxAlcoolStart() != null && filter.getTauxAlcoolEnd() != null) {
			where = cb.and( where, cb.between(root.get("tauxAlcool"), filter.getTauxAlcoolStart(), filter.getTauxAlcoolEnd() ));
		}
		
		if(filter.getTauxAlcoolStart() == null && filter.getTauxAlcoolEnd() != null) {
			where = cb.and( where, cb.lessThanOrEqualTo(root.get("tauxAlcool"), filter.getTauxAlcoolEnd() ));
		}
		
		if(filter.getTauxAlcoolStart() != null && filter.getTauxAlcoolEnd() == null) {
			where = cb.and( where, cb.greaterThanOrEqualTo(root.get("tauxAlcool"), filter.getTauxAlcoolStart() ));
		}
		
		if(filter.getIbuStart() != null && filter.getIbuEnd() != null) {
			where = cb.and( where, cb.between(root.get("ibu"), filter.getIbuStart(), filter.getIbuEnd()));
		} 
		
		if(filter.getIbuStart() != null && filter.getIbuEnd() == null) {
			where = cb.and( where, cb.greaterThanOrEqualTo(root.get("ibu"), filter.getIbuStart()));
		}
		
		if(filter.getIbuStart() == null && filter.getIbuEnd() != null) {
			where = cb.and( where, cb.lessThanOrEqualTo(root.get("ibu"), filter.getIbuEnd()));
		}
		
		if(filter.getAmertume() != null) {
			where = cb.and( where, cb.equal(root.get("amertume"), filter.getAmertume() ));
		}
		
		if(StringUtils.isNotBlank(filter.getDescription())) {
			where = cb.and( where, cb.like(cb.upper(root.get("description")), "%" + filter.getDescription().toUpperCase() + "%") );
		}
		
		if(filter.getIdTypeBiere() != null) {
			where = cb.and( where, cb.equal(root.get("idTypeBiere"), filter.getIdTypeBiere() ));
		}
		
		return where;
	}

	public List<Biere> listerBiereByNom(String nom) {
		try {
			CriteriaBuilder cb = this.repository.getCriteriaBuilder();
			CriteriaQuery<Biere> query = cb.createQuery(Biere.class);
			Root<Biere> root = query.from(Biere.class);
			query.where(this.getWhereNom(root, nom, cb));

			List<Biere> result = this.repository.getEntityManager().createQuery( query ).getResultList();

			return result;

		} catch (Exception e) {
			log.error("Erreur pour recuperer les instances de Biere by Nom", e);
			throw e;
		}
	}

	private Predicate getWhereNom(Root<Biere> root, String nom, CriteriaBuilder cb) {
		Predicate where = cb.conjunction();

		if(StringUtils.isNotBlank(nom)) {
			where = cb.and( where, cb.like(cb.upper(root.get("nom")), "%" + nom.toUpperCase() + "%") );
		}

		return where;
	}

}
