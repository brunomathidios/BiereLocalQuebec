package com.ca.biere.local.quebec.commons.ws.repository.base;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.ca.biere.local.quebec.commons.ws.entite.BaseEntite;

public class BaseRepositoryImpl<T extends BaseEntite> extends SimpleJpaRepository<T, Long> implements BaseRepository<T> {

	private final EntityManager entityManager;
	
	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
        this.entityManager = entityManager;
	}
	
	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return this.entityManager.getCriteriaBuilder();
	}

	@Override
	public <E> List<E> findCriteriaQuery(CriteriaQuery<E> query, Pageable pageable, 
			Root<E> root, CriteriaBuilder cb) {
		
		query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, cb));
		
		TypedQuery<E> typedQuery = this.entityManager.createQuery( query );
		
		if( pageable != null && pageable.getPageSize() > 0 ) {
			typedQuery.setFirstResult( pageable.getPageNumber() * pageable.getPageSize() );
			typedQuery.setMaxResults( pageable.getPageSize() );
		}
		
		return typedQuery.getResultList();	
	}

	@Override
	public <R> R executeCriteriaQuery(CriteriaQuery<R> query) {
		return this.entityManager.createQuery(query).getSingleResult();
	}

}
