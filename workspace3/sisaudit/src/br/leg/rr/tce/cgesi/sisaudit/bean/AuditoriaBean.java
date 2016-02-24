package br.leg.rr.tce.cgesi.sisaudit.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import br.leg.rr.tce.cgesi.sisaudit.comum.entity.UnidadeGestora;
import br.leg.rr.tce.cgesi.sisaudit.comum.util.Util;
import br.leg.rr.tce.cgesi.sisaudit.ejb.AuditoriaEjb;
import br.leg.rr.tce.cgesi.sisaudit.entity.Auditoria;
import br.leg.rr.tce.cgesi.sisaudit.entity.UnidadeGestoraAuditoria;

@Named
@SessionScoped
public class AuditoriaBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;
	//@ManagedProperty(value="#{AuditoriaBean}")

	@Inject
	private Auditoria auditoria;
	
	@Inject
	private transient SistemaBean sistemaBean;
	
	@EJB
	private AuditoriaEjb auditoriaEjb;
		
	private List<Auditoria> items = new ArrayList<Auditoria>();
	
	private List<UnidadeGestora> unidadeGestoraLista  = new ArrayList<UnidadeGestora>();

	public AuditoriaBean() {
		super();
	}
    	
	public List<Auditoria> getItems() {
		return items;
	}
	
	public String abrirListaAuditoria() throws Exception{
		items = new ArrayList<Auditoria>();		
        items = auditoriaEjb.findAll();
		return redirect("/sistema/auditoria/listaAuditorias.xhtml");
	}

	public List<UnidadeGestora> completeUG(String query){
		   List<UnidadeGestora> ugFiltrada = new ArrayList<UnidadeGestora>();
		   for(int i=0;i<unidadeGestoraLista.size();i++){
			  UnidadeGestora skin = unidadeGestoraLista.get(i);
			  if(skin.getNomeSilga().toLowerCase().startsWith(query)){
				  ugFiltrada.add(skin);
			  }
		   }
		   return ugFiltrada;
	}
	
	
	public String abrirCadastroAuditoria(){
		auditoria = new Auditoria();
		unidadeGestoraLista = new ArrayList<>();
		unidadeGestoraLista.addAll(sistemaBean.getUnidadeGestoraList());
		return redirect("/sistema/auditoria/cadastroAuditorias.xhtml");
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public void setItems(List<Auditoria> items) {
		this.items = items;
	}
	
	//pega evento autocomplet remo��o de sele��o e atualiza listas
	public void unselectUGA(final UnselectEvent event) {
		try {
		    final UnidadeGestora tmp = (UnidadeGestora) event.getObject();
		    auditoria.adincionarNaListaExcluidos(tmp);	
		    unidadeGestoraLista.add(tmp);			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//pega evento autocomplet sele��o de objetos e atualiza listas
	public void selectUGA(final SelectEvent event) {
		try {
		    final UnidadeGestora tmp = (UnidadeGestora) event.getObject();
	  			unidadeGestoraLista.remove(tmp);
	  			auditoria.selecionarUGA(tmp);			
		} catch (Exception e) {
			// TODO: handle exception
		}
  			
	}
	
	public String prepareEdit(Auditoria aux) {
		try {
			auditoria = new Auditoria();
	        auditoria = auditoriaEjb.carregarAuditoria(aux.getId());
	        
	        unidadeGestoraLista = new ArrayList<UnidadeGestora>();
			unidadeGestoraLista.addAll(sistemaBean.getUnidadeGestoraList());
	        auditoria.setListaUnidadeGestoraTmp(new ArrayList<UnidadeGestora>());
	        //preencher lista temp de unidade gestora e eleiminar os duplicados da lista unidadeGestoraLista
	        for(UnidadeGestoraAuditoria tmp : auditoria.getUnidadeGestoraAuditorias()){
	        	auditoria.getListaUnidadeGestoraTmp().add(sistemaBean.getUnidadeGestoraMap().get(tmp.getUnidadeGestora().getId()));
	        	for(UnidadeGestora tmp2 : unidadeGestoraLista){
	        		//System.out.println(tmp2.getId());
	        		if(tmp2.getId()==tmp.getUnidadeGestora().getId()){
	        			unidadeGestoraLista.remove(tmp2);
	        			break;
	        		}
	        	}
	        	//System.out.println("f:" + tmp.getId());
	        }
	        return redirect("/sistema/auditoria/cadastroAuditorias.xhtml");	
	        
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
			return null;
		}
		
		
    }

	
	public void salvar(){
		try {
			
			auditoriaEjb.salvarBloco(auditoria);
			
			showFacesMessage("salvo com sucesso!!!", 2);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			showFacesMessage(e.getMessage(), 4);
		}
	}
	
	
	 public void dateChange() {
		// System.out.println("cheguei");
		 
		if(auditoria.getPlanInicioPrev()!=null && auditoria.getPlanFimPrev()!=null){
			auditoria.setPlanDiasUteisPrev(Util.diasEntreDatas(auditoria.getPlanInicioPrev(),auditoria.getPlanFimPrev() ));
		}
		if(auditoria.getPlanInicioReal()!=null && auditoria.getPlanFimReal()!=null){
			auditoria.setPlanDiasUteisReal(Util.diasEntreDatas(auditoria.getPlanInicioReal(),auditoria.getPlanFimReal() ));
		}		
		if(auditoria.getExecInicioPrev()!=null && auditoria.getExecFimPrev()!=null){
			auditoria.setExecDiasUteisPrev(Util.diasEntreDatas(auditoria.getExecInicioPrev(),auditoria.getExecFimPrev() ));
		}		
		if(auditoria.getExecInicioReal()!=null && auditoria.getExecFimReal()!=null){
			auditoria.setExecDiasUteisReal(Util.diasEntreDatas(auditoria.getExecInicioReal(),auditoria.getExecFimReal() ));
		}		
		if(auditoria.getRelaInicioPrev()!=null && auditoria.getRelaFimPrev()!=null){
			auditoria.setRelaDiasUteisPrev(Util.diasEntreDatas(auditoria.getRelaInicioPrev(),auditoria.getRelaFimPrev() ));
		}		
		if(auditoria.getRelaInicioReal()!=null && auditoria.getRelaFimReal()!=null){
			auditoria.setRelaDiasUteisReal(Util.diasEntreDatas(auditoria.getRelaInicioReal(),auditoria.getRelaFimReal() ));
		}		
		
	}


}
