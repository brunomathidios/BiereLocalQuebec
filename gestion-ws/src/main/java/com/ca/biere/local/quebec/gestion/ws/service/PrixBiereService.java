package com.ca.biere.local.quebec.gestion.ws.service;

import com.ca.biere.local.quebec.commons.ws.entite.PrixBiere;
import com.ca.biere.local.quebec.commons.ws.exception.ResourceNotFoundException;
import com.ca.biere.local.quebec.commons.ws.exception.SaveEntiteException;
import com.ca.biere.local.quebec.commons.ws.service.BaseEntiteService;
import com.ca.biere.local.quebec.gestion.ws.dto.PrixBiereDTO;
import com.ca.biere.local.quebec.gestion.ws.filter.PrixBiereFilter;
import com.ca.biere.local.quebec.gestion.ws.repository.PrixBiereRepository;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

@Service
public class PrixBiereService extends BaseEntiteService<PrixBiere> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PrixBiereRepository repository;

    @Autowired
    private BiereService biereService;

    @Autowired
    private Environment environment;

    public PrixBiereService(Validator validator) {
        this.setValidator(validator);
    }

    @Override
    public PrixBiere insert(PrixBiere prixBiere) {
        if (Optional.ofNullable(prixBiere.getIdPrix()).isPresent()) {
            String erreur = this.environment.getRequiredProperty("message.error.methode.insert");
            throw new SaveEntiteException(erreur);
        }

        this.valideEntite(prixBiere);
        this.validateBiereSelectionee(prixBiere.getIdBiere());

        try {
            return this.repository.saveAndFlush(prixBiere);
        } catch (Exception e) {
            log.error("Erreur pendand l'insertion d'une instance PrixBiere", e);
            throw e;
        }
    }

    private void validateBiereSelectionee(Long idBiere) {
        if (Optional.ofNullable(this.biereService.findById(idBiere)).isEmpty()) {
            throw new ResourceNotFoundException(
                    "Entité " + idBiere + " du type Bière n'a pas été trouvée");
        }
    }

    @Override
    public PrixBiere update(PrixBiere prixBiere) {
        try {

            if (!Optional.ofNullable(prixBiere.getIdPrix()).isPresent()) {
                String erreur = this.environment.getRequiredProperty("message.error.methode.update");
                throw new SaveEntiteException(erreur);
            }

            if (Optional.ofNullable(this.findById(prixBiere.getIdPrix())).isPresent()) {

                this.valideEntite(prixBiere);
                this.validateBiereSelectionee(prixBiere.getIdBiere());
                return this.repository.saveAndFlush(prixBiere);

            } else {
                throw new ResourceNotFoundException(
                        "Entité " + prixBiere.getIdPrix() + " du type Prix n'a pas été trouvée");
            }

        } catch (Exception e) {
            log.error("Erreur pendand mis a jour d'une instance PrixBiere", e);
            throw e;
        }
    }

    public PrixBiere findById(Long id) {
        try {
            return this.repository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error("Erreur pendand findById RecompenseBiere", e);
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
                                "Entité " + id + " du type Prix n'a pas été trouvée");
                    });
        } catch (Exception e) {
            log.error("Erreur pendand deleteById PrixBiere", e);
            throw e;
        }
    }

    public Page<PrixBiereDTO> listerPrix(PrixBiereFilter filter, Pageable page) {
        try {
            CriteriaBuilder cb = this.repository.getCriteriaBuilder();
            CriteriaQuery<PrixBiereDTO> query = cb.createQuery(PrixBiereDTO.class);
            CriteriaQuery<Long> queryCount = cb.createQuery(Long.class);

            Root<PrixBiere> root = query.from(PrixBiere.class);
            Root<PrixBiere> rootCount = queryCount.from(PrixBiere.class);

            query.select(cb.construct(PrixBiereDTO.class, root.get("idPrix"), root.get("prix"), root.get("biere").get("nom")));
            query.where(this.getWhere(root, filter, cb));

            queryCount.select(cb.count(rootCount));
            queryCount.where(this.getWhere(rootCount, filter, cb));

            List<PrixBiereDTO> result = this.repository.findCriteriaQueryWithDTO(query, page, root, cb);
            Long resultCount = this.repository.executeCriteriaQuery( queryCount );

            return new PageImpl<>( result, page, resultCount );

        } catch (Exception e) {
            log.error("Erreur pour recuperer les instances de PrixBiere", e);
            throw e;
        }
    }

    private Predicate getWhere(Root<PrixBiere> root, PrixBiereFilter filter, CriteriaBuilder cb) {
        Predicate where = cb.conjunction();

        if(StringUtils.isNotBlank(filter.getPrix())) {
            where = cb.and( where, cb.like(cb.upper(root.get("prix")), "%" + filter.getPrix().toUpperCase() + "%") );
        }

        if(filter.getIdBiere() != null) {
            where = cb.and( where, cb.equal(root.get("idBiere"), filter.getIdBiere()) );
        }
        return where;
    }
}
