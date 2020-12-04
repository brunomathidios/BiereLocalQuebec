package com.ca.biere.local.quebec.commons.ws.repository.base;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.ca.biere.local.quebec.commons.ws.entite.BaseEntite;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntite> extends JpaRepository<T, Long> {

	CriteriaBuilder getCriteriaBuilder();
	<E> List<E> findCriteriaQuery(CriteriaQuery<E> query, Pageable pageable, Root<E> root, CriteriaBuilder cb);
	<R> R executeCriteriaQuery(CriteriaQuery<R> query);
}
