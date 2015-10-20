package br.leg.tcerr.difip.sisaudit.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.leg.tcerr.difip.sisaudit.entity.EquipeFiscalizacao;

/**
 * Session Bean implementation class AuditoriaEjb
 */
@Stateless
public class EquipeFiscalizacaoEjb extends AbstractEjb implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	public void salvar(EquipeFiscalizacao entity) throws Exception {
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

	public void remove(EquipeFiscalizacao entity) {
		entityManager.remove(entity);
	}

	public List<EquipeFiscalizacao> findAll() throws Exception {
		try {
			String sql = "select * from scsisaudit.equipe_fiscalizacao";
			List<EquipeFiscalizacao> listaEquipeFiscalizacao = executaSqlNativo(sql, EquipeFiscalizacao.class, entityManager);
			return listaEquipeFiscalizacao;

		} catch (RuntimeException re) {
			re.printStackTrace();
			throw new Exception(" Erro" + re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(" Erro" + e.getMessage());
		}

	}

}
