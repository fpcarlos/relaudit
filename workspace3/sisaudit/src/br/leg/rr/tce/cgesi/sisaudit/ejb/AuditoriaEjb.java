package br.leg.rr.tce.cgesi.sisaudit.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.leg.rr.tce.cgesi.sisaudit.comum.entity.UnidadeGestora;
import br.leg.rr.tce.cgesi.sisaudit.entity.Auditoria;
import br.leg.rr.tce.cgesi.sisaudit.entity.UnidadeGestoraAuditoria;

/**
 * Session Bean implementation class AuditoriaEjb
 */
@Stateless
public class AuditoriaEjb extends AbstractEjb implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@EJB
	private UnidadeGestoraAuditoriaEjb unidadeGestoraAuditoriaEjb;

	public void salvar(Auditoria entity) throws Exception {
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

	public void salvarBloco(Auditoria entity) {
		try {
			this.salvar(entity);
			//entity.criarListaUGAuditoria();
			for (UnidadeGestoraAuditoria aux : entity.getUnidadeGestoraAuditoriaList()) {
				unidadeGestoraAuditoriaEjb.salvar(aux);
			}
			for (UnidadeGestoraAuditoria aux : entity.getUnidadeGestoraAuditoriasExcluidas()) {
				unidadeGestoraAuditoriaEjb.remove(aux);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public Auditoria carregarAuditoria(Integer id) throws Exception{
		try {
			Auditoria aud = new Auditoria();
			aud = entityManager.find(Auditoria.class, id);
			aud.setUnidadeGestoraAuditorias(unidadeGestoraAuditoriaEjb.findUgId(id));
			aud.setUnidadeGestoraAuditoriaList(new ArrayList<UnidadeGestoraAuditoria>());
			aud.setListaUnidadeGestoraTmp(new ArrayList<UnidadeGestora>());
			aud.setUnidadeGestoraAuditoriasExcluidas(new ArrayList<UnidadeGestoraAuditoria>());
			return aud;			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	public void remove(Auditoria entity) {
		entityManager.remove(entity);
	}

	public List<Auditoria> findAll() throws Exception {
		try {
			String sql = "select * from scsisaudit.auditoria";
			List<Auditoria> listaAuditoria = executaSqlNativo(sql, Auditoria.class, entityManager);
			return listaAuditoria;

		} catch (RuntimeException re) {
			re.printStackTrace();
			throw new Exception(" Erro" + re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(" Erro" + e.getMessage());
		}

	}
	


}
