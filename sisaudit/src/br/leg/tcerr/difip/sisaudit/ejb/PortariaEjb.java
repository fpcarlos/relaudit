package br.leg.tcerr.difip.sisaudit.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.leg.tcerr.difip.sisaudit.entity.Portaria;

/**
 * Session Bean implementation class PortariaEjb
 */
@Stateless
public class PortariaEjb extends AbstractEjb implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	public void salvar(Portaria entity) throws Exception {
		try {
			if (entity.getId() != null && entity.getId() > 0) {
				entityManager.merge(entity);
			} else {
				entityManager.persist(entity);
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}


	public void remove(Portaria entity) {
		entityManager.remove(entity);
	}

	public List<Portaria> findAll() throws Exception {
		try {
			String sql = "select * from scsisaudit.auditoria";
			List<Portaria> listaPortaria = executaSqlNativo(sql, Portaria.class, entityManager);
			return listaPortaria;

		} catch (RuntimeException re) {
			re.printStackTrace();
			throw new Exception(" Erro" + re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(" Erro" + e.getMessage());
		}

	}

}
