package br.leg.tcerr.difip.relaudit.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.leg.tcerr.difip.relaudit.ejb.CriteriosSelecaoEjb;
import br.leg.tcerr.difip.relaudit.entity.CriteriosSelecao;

@Named
@RequestScoped
public class CriteriosSelecaoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	CriteriosSelecao criteriosSelecao;
	
	@EJB
	CriteriosSelecaoEjb criteriosSelecaoEjb; 
	
	public CriteriosSelecaoBean() {
        super();
    }
	
	
	public void save(){
		criteriosSelecao.setNome("Teste ejb");
		criteriosSelecaoEjb.salvar(criteriosSelecao);		
	}
	
	public String abrirListaAuditoria(){
		
		return "/sistema/auditoria/listaAuditorias.xhtml";
	}
	

}
