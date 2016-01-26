package br.leg.tcerr.difip.relaudit.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.leg.tcerr.difip.relaudit.ejb.AuditoriaEjb;
import br.leg.tcerr.difip.relaudit.ejb.EquipeFiscalizacaoEjb;
import br.leg.tcerr.difip.relaudit.ejb.PortariaEjb;
import br.leg.tcerr.difip.relaudit.entity.Auditoria;
import br.leg.tcerr.difip.relaudit.entity.EquipeFiscalizacao;
import br.leg.tcerr.difip.relaudit.entity.Portaria;


@Named
@SessionScoped
public class EquipeFiscalizacaoBean extends AbstractBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EquipeFiscalizacao equipeFiscalizacao;
	
	@Inject
	private Auditoria auditoria;
	
	@Inject
	private Portaria portaria;
	
	@Inject
	private transient SistemaBean sistemaBean;
	
	@EJB
	private EquipeFiscalizacaoEjb equipeFiscalizacaoEjb;
	
	@EJB
	private AuditoriaEjb auditoriaEjb;
	
	@EJB
	private PortariaEjb portariaEjb;
	
	private List<Auditoria> listaAuditoria = new ArrayList<Auditoria>();

	private List<EquipeFiscalizacao> listaEquipeFiscalizacao = new ArrayList<EquipeFiscalizacao>();
	
	public EquipeFiscalizacaoBean() {
		super();
	}


	public String abreCadastroEquipeFiscalizacao(){
		try {
			Integer id = 10;
			portaria = new Portaria();
			portaria = portariaEjb.pegarPortaria(id);
			
			equipeFiscalizacao = new EquipeFiscalizacao();
			equipeFiscalizacao.setPortaria(portaria);
			listaEquipeFiscalizacao = new ArrayList<EquipeFiscalizacao>();
			listaEquipeFiscalizacao = equipeFiscalizacaoEjb.findIdPortaria(portaria.getId());
			//auditoria = new Auditoria();
			//auditoria = auditoriaEjb.carregarAuditoria(aux.getId());
			//equipeFiscalizacao = new EquipeFiscalizacao();z
			
			return redirect("/sistema/portaria/cadastroEquipes.xhtml"); 
			
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
			return null;
		}
	}
	
	public void addServidor(){
		try {
			
			//listaEquipeFiscalizacao = new ArrayList<EquipeFiscalizacao>();
			//listaEquipeFiscalizacao.add(e)
			
			
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
		}
	}
	
	
	
	public void salvar(){
		try {
			equipeFiscalizacao.setPortaria(portaria);
			equipeFiscalizacaoEjb.salvar(equipeFiscalizacao);
			equipeFiscalizacao = new EquipeFiscalizacao();
			equipeFiscalizacao.setPortaria(portaria);
			listaEquipeFiscalizacao = new ArrayList<EquipeFiscalizacao>();
			listaEquipeFiscalizacao = equipeFiscalizacaoEjb.findIdPortaria(portaria.getId());

			showFacesMessage("salvo com sucesso!!!", 2);
			
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
		}
	}
	
	public EquipeFiscalizacao getEquipeFiscalizacao() {
		return equipeFiscalizacao;
	}

	public void setEquipeFiscalizacao(EquipeFiscalizacao equipeFiscalizacao) {
		this.equipeFiscalizacao = equipeFiscalizacao;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public Portaria getPortaria() {
		return portaria;
	}


	public void setPortaria(Portaria portaria) {
		this.portaria = portaria;
	}

	public List<Auditoria> getListaAuditoria() {
		return listaAuditoria;
	}

	public void setListaAuditoria(List<Auditoria> listaAuditoria) {
		this.listaAuditoria = listaAuditoria;
	}

	public List<EquipeFiscalizacao> getListaEquipeFiscalizacao() {
		return listaEquipeFiscalizacao;
	}

	public void setListaEquipeFiscalizacao(List<EquipeFiscalizacao> listaEquipeFiscalizacao) {
		this.listaEquipeFiscalizacao = listaEquipeFiscalizacao;
	}



	
	
	
	
}
