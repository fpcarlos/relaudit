package br.leg.tcerr.difip.sisaudit.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import br.leg.tcerr.difip.sisaudit.entity.CriteriosSelecao;

@Stateless
public class CriteriosSelecaoEjb {
	@PersistenceContext
	private EntityManager entityManager;
	
	public void salvar(CriteriosSelecao entity) {
		try {
			entityManager.persist(entity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}

	public void editar(CriteriosSelecao entity) {
		entityManager.merge(entity);
	}

	public void excluir(CriteriosSelecao entity) {
		entityManager.remove(entityManager.merge(entity));
	}
	
	public List<CriteriosSelecao> findAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CriteriosSelecao> criteriaQuery = criteriaBuilder
				.createQuery(CriteriosSelecao.class);
		TypedQuery<CriteriosSelecao> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

		
	
}
