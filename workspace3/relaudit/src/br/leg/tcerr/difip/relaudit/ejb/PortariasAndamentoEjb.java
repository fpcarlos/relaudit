package br.leg.tcerr.difip.relaudit.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.leg.tcerr.difip.relaudit.entity.PortariasAndamento;

/**
 * Session Bean implementation class PortariaEjb
 */
@Stateless
public class PortariasAndamentoEjb extends AbstractEjb implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	public void salvar(PortariasAndamento entity) throws Exception {
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


	public void remove(PortariasAndamento entity) {
		entityManager.remove(entity);
	}

	public List<PortariasAndamento> findAll() throws Exception {
		try {
			String sql = "select * from scsisaudit.postrarias_andamento";
			List<PortariasAndamento> listaPortariasAndamento = executaSqlNativo(sql, PortariasAndamento.class, entityManager);
			return listaPortariasAndamento;

		} catch (RuntimeException re) {
			re.printStackTrace();
			throw new Exception(" Erro" + re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(" Erro" + e.getMessage());
		}

	}

}
