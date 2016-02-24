package br.leg.rr.tce.cgesi.sisaudit.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.leg.rr.tce.cgesi.sisaudit.entity.UnidadeFiscalizadora;

@Stateless
public class UnidadeFiscalizadoraEjb extends AbstractEjb implements Serializable {
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager entityManager;

	public void create(UnidadeFiscalizadora entity){
		entityManager.persist(entity);
	}
    
    public void edit(UnidadeFiscalizadora entity){
    	entityManager.merge(entity);
    }

    public void remove(UnidadeFiscalizadora entity){
    	entityManager.remove(entity);
    }

    public List<UnidadeFiscalizadora> findAll()
			throws Exception {    	
		try {
			String sql = "select * from scsisaudit.unidade_fiscalizadora";
			List<UnidadeFiscalizadora> listaUnidadeFiscalizadora = executaSqlNativo(sql,UnidadeFiscalizadora.class, entityManager);
			return listaUnidadeFiscalizadora;

		} catch (RuntimeException re) {
			re.printStackTrace();
			throw new Exception(" Erro" + re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(" Erro" + e.getMessage());
		}
    }

	

}
