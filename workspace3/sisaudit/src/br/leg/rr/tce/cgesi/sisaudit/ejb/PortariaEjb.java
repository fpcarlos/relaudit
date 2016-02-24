package br.leg.rr.tce.cgesi.sisaudit.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.leg.rr.tce.cgesi.sisaudit.entity.Auditoria;
import br.leg.rr.tce.cgesi.sisaudit.entity.EquipeFiscalizacao;
import br.leg.rr.tce.cgesi.sisaudit.entity.Portaria;
import br.leg.rr.tce.cgesi.sisaudit.entity.PortariasAndamento;
import br.leg.rr.tce.cgesi.sisaudit.entity.UnidadeGestoraAuditoria;
import br.leg.rr.tce.cgesi.sisaudit.entity.UnidadeGestoraPortaria;

/**
 * Session Bean implementation class PortariaEjb
 */
@Stateless
public class PortariaEjb extends AbstractEjb implements Serializable {
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@EJB
	UnidadeGestoraPortariaEjb unidadeGestoraPortariaEjb;
	
	@EJB
	AuditoriaEjb auditoriaEjb;
	
	@EJB
	EquipeFiscalizacaoEjb equipeFiscalizacaoEjb;
	
	@EJB
	PortariasAndamentoEjb portariasAndamentoEjb;

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
	
	public void salvarPacote(Portaria entity) throws Exception{
		try {
			boolean sc=false;
			if(entity.getId()==null)
				sc=true;
			this.salvar(entity);
			for(UnidadeGestoraPortaria x: entity.getUnidadeGestoraPortarias()){
				unidadeGestoraPortariaEjb.salvar(x);
			}
			for (UnidadeGestoraPortaria aux : entity.getUnidadeGestoraPortariaExcluidas()) {
				unidadeGestoraPortariaEjb.excluir(aux);
			}
			if(sc==true){
				for(EquipeFiscalizacao aux1: entity.getEquipeFiscalizacaoList()){
					equipeFiscalizacaoEjb.salvar(aux1);
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void salvarMinuta(Portaria entity) throws Exception{
		try {
			boolean sc=false;
			if(entity.getId()==null)
				sc=true;
			
			this.salvar(entity);
			
			if(!entity.getUnidadeGestoraPortarias().isEmpty()){
				for(UnidadeGestoraPortaria x: entity.getUnidadeGestoraPortarias()){
					unidadeGestoraPortariaEjb.salvar(x);
				}
			}
			if(!entity.getUnidadeGestoraPortariaExcluidas().isEmpty()){
				for (UnidadeGestoraPortaria aux : entity.getUnidadeGestoraPortariaExcluidas()) {
					unidadeGestoraPortariaEjb.excluir(aux);
				}
			}
			
			if(sc==true){
				for(PortariasAndamento x: entity.getPortariasAndamentos()){
					portariasAndamentoEjb.salvar(x);
				}				
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}


	public void remove(Portaria entity) {
		Portaria aux = entityManager.find(Portaria.class, entity.getId());
		entityManager.remove(aux);
	}
	
	public Portaria pegarPortaria(Integer id){
		Portaria aux = entityManager.find(Portaria.class, id);
		return aux;
	}

	public Portaria iniciarPortiariaComAuditoria(Auditoria aux) throws Exception{
		try {
			Portaria portaria = new Portaria();
			portaria.setAuditoria(aux);
			portaria.setIdAuditoria(portaria.getAuditoria().getId());
			portaria.setUnidadeGestoraPortarias( new ArrayList<UnidadeGestoraPortaria>());
			for(UnidadeGestoraAuditoria x: portaria.getAuditoria().getUnidadeGestoraAuditorias()){
				UnidadeGestoraPortaria unidadeGP = new UnidadeGestoraPortaria();
				unidadeGP.setPortaria(portaria);
				unidadeGP.setUnidadeGestora(x.getUnidadeGestora());
				//unidadeGP.setIdUnidadeGestora(x.getUnidadeGestora().getId());
				portaria.getUnidadeGestoraPortarias().add(unidadeGP);
			}
			return portaria;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	
	//
	public List<Portaria> findAll() throws Exception {
		try {
			String sql = "select * from scsisaudit.portaria order by id";
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
//lpad(cast(cod_curso as varchar),4,'0')
	public List<Portaria> listaPortaria() throws Exception {
		try {
			String sql = "select id, id_auditoria, objetivo, deliberacao, id_servidor, "
					+ "id_tipo_fiscalizacao, lpad(cast(numero_portaria as varchar),3,'0') as numero_portaria, ano_portaria, id_unidade_fiscalizadora from scsisaudit.portaria order by id";
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

	
	public List<Portaria> findIdAuditoria(Integer id) throws Exception {
		try {
			String sql = "select * from scsisaudit.portaria where id_auditoria = " + id + " ";
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

	
	public String  ultimoNumeroPortaria(String anop) throws Exception {
		try {
			String sql = "select COALESCE(max(cast(numero_portaria as integer))+1,0) as numero_portaria from scsisaudit.portaria where ano_portaria='" + anop + "'";
			Integer resultSql =  (Integer) entityManager.createNativeQuery(sql).getSingleResult();
			
			//Portaria resultSql = executaSqlNativo(sql, Portaria.class, entityManager).get(0); 
			/*
			 String sql = "select COALESCE(max(cast(numero_portaria as integer))+1,0) as numero_portaria from scsisaudit.portaria where ano_portaria='" + anop + "'";
			String sql = "id,  id_auditoria,  processo_numero,  processo_ano,  data_assinatura,  numero_publica_doe,  data_publica_doe, "
					+ " objetivo,  deliberacao,  numero_portaria_revogada,  ano_portaria_revogada,  plan_inicio,  plan_fim,  plan_dias_uteis,  "
					+ "exec_inicio,  exec_fim,  exec_dias_uteis,  rela_inicio,  rela_fim,  rela_dias_uteis,  id_servidor,  id_tipo_fiscalizacao,  "
					+ "numero_portaria,  ano_portaria,  data_assinatua_portaria,  id_unidade_fiscalizadora from scsisaudit.portaria where ano_portaria='"+ anop + "'";
					*/
			//List<Portaria> listaPortaria = executaSqlNativo(sql, Portaria.class, entityManager);
			
			//return listaPortaria;
			return resultSql.toString();

		} catch (RuntimeException re) {
			re.printStackTrace();
			throw new Exception(" Erro" + re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(" Erro" + e.getMessage());
		}
	}

/*	public List<Portaria>  ultimoNumeroPortaria(String anop) throws Exception {
		try {
			//String sql = "select * from scsisaudit.portaria where ano_portaria = '" + anop + "' order by numero_portaria desc limit 1";
			String sql = "select max(cast(numero_portaria as integer))+1 as numero_portaria from scsisaudit.portaria where ano_portaria = '" + anop + "' ";
			List<Portaria> listaPortaria = executaSqlNativo(sql, Portaria.class, entityManager);
			return listaPortaria;			 

		} catch (RuntimeException re) {
			re.printStackTrace();
			throw new Exception(" Erro" + re.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(" Erro" + e.getMessage());
		}
	}*/

	
}
