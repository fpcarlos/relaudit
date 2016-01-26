package br.leg.tcerr.difip.relaudit.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import br.leg.tcerr.difip.relaudit.comum.entity.UnidadeGestora;
import br.leg.tcerr.difip.relaudit.comum.util.Util;
import br.leg.tcerr.difip.relaudit.ejb.AuditoriaEjb;
import br.leg.tcerr.difip.relaudit.ejb.EquipeFiscalizacaoEjb;
import br.leg.tcerr.difip.relaudit.ejb.PortariaEjb;
import br.leg.tcerr.difip.relaudit.ejb.UnidadeGestoraEjb;
import br.leg.tcerr.difip.relaudit.ejb.UnidadeGestoraPortariaEjb;
import br.leg.tcerr.difip.relaudit.entity.Auditoria;
import br.leg.tcerr.difip.relaudit.entity.EquipeFiscalizacao;
import br.leg.tcerr.difip.relaudit.entity.Portaria;
import br.leg.tcerr.difip.relaudit.entity.Servidor;
import br.leg.tcerr.difip.relaudit.entity.UnidadeGestoraAuditoria;
import br.leg.tcerr.difip.relaudit.entity.UnidadeGestoraPortaria;



@Named
@SessionScoped
public class CadasroPortariaBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private transient SistemaBean sistemaBean;
	
	@Inject
	private Portaria portaria;
	
	@Inject
	private Auditoria auditoria;
	
	@Inject
	private EquipeFiscalizacao equipeFiscalizacao;
	
	@Inject
	private UnidadeGestora unidadeGestora;
	
	@EJB
	private AuditoriaEjb auditoriaEjb;
	
	@EJB
	private PortariaEjb portariaEjb;
	
	@EJB
	private UnidadeGestoraPortariaEjb unidadeGestoraPortariaEjb;	
	
	@EJB
	private UnidadeGestoraEjb unidadeGestoraEjb;
	
	@EJB
	private EquipeFiscalizacaoEjb equipeFiscalizacaoEjb;
	
	private List<UnidadeGestora> unidadeGestoraLista  = new ArrayList<UnidadeGestora>();
	private List<Portaria> portariaList = new ArrayList<Portaria>();
	private List<UnidadeGestoraPortaria> unidadeGestoraPortariaList = new ArrayList<UnidadeGestoraPortaria>();
	private List<UnidadeGestoraPortaria> unidadeGestoraPortariaList2 = new ArrayList<UnidadeGestoraPortaria>();
	
	private List<UnidadeGestora> unidadeGestoraDaAuditoria = new ArrayList<UnidadeGestora>();
	private List<UnidadeGestora> unidadeGestoraSelecionadas = new ArrayList<UnidadeGestora>();
	private List<UnidadeGestora> unidadeGestoraDaPortaria = new ArrayList<UnidadeGestora>();
	
	private List<EquipeFiscalizacao> equipeFiscalizacaoList = new ArrayList<EquipeFiscalizacao>();
	
	private List<Servidor> servidorList = new ArrayList<Servidor>();
	
	private String msgTexto;
	private Portaria portariaView = new Portaria();
	
	
	//private Tex textoPortaria;
	
	public CadasroPortariaBean() {
		super();
	}
	
	public String prepararEditPortaria(Portaria aux){		
		try {
			auditoria = new Auditoria();
			auditoria = auditoriaEjb.carregarAuditoria(aux.getIdAuditoria());
			portaria = aux;
			portaria.setAuditoria(auditoria);
			portaria.setIdAuditoria(portaria.getAuditoria().getId());
			
			unidadeGestoraDaAuditoria = new ArrayList<UnidadeGestora>();
			unidadeGestoraSelecionadas = new ArrayList<UnidadeGestora>();
			unidadeGestoraDaPortaria = new ArrayList<UnidadeGestora>();
			
			for(UnidadeGestoraAuditoria x: portaria.getAuditoria().getUnidadeGestoraAuditorias()){
				UnidadeGestoraPortaria unidGP = new UnidadeGestoraPortaria();
				unidGP.setUnidadeGestora(x.getUnidadeGestora());
				//unidGP.setIdUnidadeGestora(x.getUnidadeGestora().getId());
				unidGP.setPortaria(aux);
				unidadeGestoraDaAuditoria.add(x.getUnidadeGestora());
				portaria.getListaUnidadeGestoraDaPortaria().add(unidGP);
			}
			
			
			
			for(UnidadeGestoraPortaria x: unidadeGestoraPortariaEjb.findIdPortaria(portaria.getId())){
				 UnidadeGestora unG = new UnidadeGestora();
				 //unG=sistemaBean.getUnidadeGestoraList().get(x.getIdUnidadeGestora());
				 unG=x.getUnidadeGestora();
				 unidadeGestoraSelecionadas.add(unG);
				 //unGP.setIdUnidadeGestora(x.getId());
				 //unGP.setPortaria(portaria);
				 //portaria.addUnidadeGestoraPortaria(unGP);
			}
			
			sistemaBean.getServidorList();
			portaria.setServidor(sistemaBean.getServidorMap().get(portaria.getServidor().getId()));
			
			equipeFiscalizacaoList = new ArrayList<EquipeFiscalizacao>();
			equipeFiscalizacaoList = equipeFiscalizacaoEjb.findIdPortaria(aux.getId());


			
			return redirect("/sistema/portaria/cadastroPortaria.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
			return null;
		}		
	}

	//prepara editar portaria de auditoria
	public void selecionandoUGP(){		 
		 try {
			 unidadeGestoraPortariaList = new ArrayList<UnidadeGestoraPortaria>();
			 unidadeGestoraPortariaList2 = new ArrayList<UnidadeGestoraPortaria>();
			 
			 Map<Integer, UnidadeGestora> mapUGS = new HashMap<Integer, UnidadeGestora>();
			 Map<Integer, UnidadeGestora> mapUGE = new HashMap<Integer, UnidadeGestora>();
			 Map<Integer, UnidadeGestora> mapUGP = new HashMap<Integer, UnidadeGestora>();
			 //unidades selecionadas
			 for(UnidadeGestora x: unidadeGestoraSelecionadas){
				 mapUGS.put(x.getId(), x);
				 UnidadeGestoraPortaria ugp = new UnidadeGestoraPortaria();				 
				 ugp.setPortaria(portaria);
				 ugp.setUnidadeGestora(x);
				 unidadeGestoraPortariaList.add(ugp);				 
			 }
			 //da auditoria
			 for(UnidadeGestoraAuditoria x: portaria.getAuditoria().getUnidadeGestoraAuditorias()){
				 mapUGE.put(x.getUnidadeGestora().getId(), x.getUnidadeGestora());
				 UnidadeGestoraPortaria ugp = new UnidadeGestoraPortaria();				 
				 ugp.setPortaria(portaria);
				 ugp.setUnidadeGestora(x.getUnidadeGestora());
				 //portaria.getUnidadeGestoraPortariaExcluidas().add(ugp);
			 }
			 //daportaria
			 for(UnidadeGestoraPortaria x: portaria.getUnidadeGestoraPortarias()){
				 mapUGP.put(x.getUnidadeGestora().getId(), x.getUnidadeGestora());
			     UnidadeGestoraPortaria ugp = new UnidadeGestoraPortaria();
				 ugp.setId(x.getId());
				 ugp.setPortaria(portaria);
				 ugp.setUnidadeGestora(x.getUnidadeGestora());
				 unidadeGestoraPortariaList2.add(ugp);
				 if(mapUGS.containsKey(x.getUnidadeGestora().getId())){
					 portaria.removeUnidadeGestoraPortaria(ugp);
				 }
			  }
			  portaria.setUnidadeGestoraPortarias(getUnidadeGestoraPortariaList());
		      portaria.setUnidadeGestoraPortariaExcluidas(unidadeGestoraPortariaList2);
		      this.salvar();
			 
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
		}
		 
	 }

	public String prepararNovaPortaria(){		
		try {
			
			portaria = portariaEjb.iniciarPortiariaComAuditoria(auditoria);
			//System.out.println("");
			portaria.setIdAuditoria(auditoria.getId());
			portaria.setObjetivo(auditoria.getObjetivo());
			
			portaria.setPlanInicio(auditoria.getPlanInicioPrev());
			portaria.setPlanFim(auditoria.getPlanFimPrev());
			portaria.setPlanDiasUteis(auditoria.getPlanDiasUteisPrev());

			portaria.setExecInicio(auditoria.getExecInicioPrev());
			portaria.setExecFim(auditoria.getExecFimPrev());
			portaria.setExecDiasUteis(auditoria.getExecDiasUteisPrev());
			
			portaria.setRelaInicio(auditoria.getRelaInicioPrev());
			portaria.setRelaFim(auditoria.getRelaFimPrev());
			portaria.setRelaDiasUteis(auditoria.getRelaDiasUteisPrev());		
			
			portaria.setTipoFiscalizacao(auditoria.getTipoFiscalizacao());

			sistemaBean.getServidorList();
			portaria.setServidor(sistemaBean.getServidorMap().get(portaria.getServidor().getId()));
			
			
			
			return redirect("/sistema/portaria/cadastroPortaria.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
			return null;
		}		
	}
	
	
	public String preprarCadastroPortaria(Auditoria aux){
		try {
			//auditoria = new Auditoria();
			//auditoria = auditoriaEjb.carregarAuditoria(aux.getId());
			//auditoria.setPortariaList(portariaEjb.findIdAuditoria(aux.getId()));
			//portaria = new Portaria();
			//portaria = portariaEjb.iniciarPortiariaComAuditoria(aux);
			//auditoria = portaria.getAuditoria();
			auditoria = new Auditoria();
			auditoria = aux;
			auditoria.setPortariaList(portariaEjb.findIdAuditoria(aux.getId()));
			
			//portaria =  portariaEjb.findIdAuditoria(aux.getId()).get(0);
			
			return redirect("/sistema/portaria/cadastroPortariaAuditoria.xhtml"); 
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
			return null;

		}
		
	}
	
	public String preparaCadastroPortariaSemAuditoria(){
		try {
			portaria = new Portaria();		
			unidadeGestoraSelecionadas = new ArrayList<UnidadeGestora>();
			unidadeGestoraLista = new ArrayList<UnidadeGestora>();
			unidadeGestoraLista.addAll(sistemaBean.getUnidadeGestoraList());
			return redirect("/sistema/portaria/cadastroPortariaSemAuditoria.xhtml"); 
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
			return null;
		}
	}
	
	
	public void addEquipe() {
		try {
			EquipeFiscalizacao eqLista = new EquipeFiscalizacao();
			eqLista.setPortaria(portaria);
			eqLista.setServidor(equipeFiscalizacao.getServidor());
			eqLista.setTipoAuditor(equipeFiscalizacao.getTipoAuditor());
			equipeFiscalizacaoList.add(eqLista);
			equipeFiscalizacaoEjb.salvar(eqLista);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void remEquipe(EquipeFiscalizacao eq){
		try {
			equipeFiscalizacaoList.remove(eq);
			equipeFiscalizacaoEjb.remove(eq);						
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void addEquipeSemAuditoria(){
		EquipeFiscalizacao eqLista = new EquipeFiscalizacao();
		eqLista.setPortaria(portaria);
		eqLista.setServidor(equipeFiscalizacao.getServidor());
		eqLista.setTipoAuditor(equipeFiscalizacao.getTipoAuditor());		
		equipeFiscalizacaoList.add(eqLista);
		portaria.getEquipeFiscalizacaoList().add(eqLista);
	}
	public void remEquipeSemAuditoria(EquipeFiscalizacao eq){
		equipeFiscalizacaoList.remove(eq);
		portaria.getEquipeFiscalizacaoList().remove(eq);
	}
	
	public String abrirListaPortaria() throws Exception{
		portariaList = new ArrayList<Portaria>();
		portariaList = portariaEjb.findAll();		
		return redirect("/sistema/portaria/listaPortarias.xhtml");
	}
	
	
	public String viewPortaria(Portaria aux){
		portariaView = portariaEjb.pegarPortaria(aux.getId());
		
		msgTexto="<t:panelGrid style=\"margin-top:20px; align: justufy\" width=\"800\" >"  	 
			     + "<div align=\"center\"><img src=\"../../resources/images/logorr.jpg\" /></div>"
			     + "<div align=\"center\">PORTARIA No XXXX/2015-TCE/RR</div>"
		         + "</t:panelGrid><div width=\"300px\">"+portariaView.getObjetivo()+"</div>"
				+ "<div></div>";
		return redirect("/sistema/portaria/_viewPortaria.xhtml");
	}
	
	public String abrirPdf(){
		return redirect("/sistema/portaria/_viewPortaria.xhtml");
	}
	
	//executar opção salvar
	public void salvar(){
		try {
			
			//selecionandoUGP();
		
			portariaEjb.salvarPacote(portaria);
			
			showFacesMessage("salvo com sucesso!!!", 2);
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
		}
	}
	
	//remove portaria
	public void remover(Portaria aux){
		try {
			Integer id = aux.getIdAuditoria();
			portariaEjb.remove(aux);
			showFacesMessage("portaria deletada com sucesso!!!", 2);
			auditoria.setPortariaList(portariaEjb.findIdAuditoria(id));
			
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
		}
	}
	
	
	 public void dateChange() {
		if(portaria.getPlanInicio()!=null && portaria.getPlanFim()!=null){
			portaria.setPlanDiasUteis(Util.diasEntreDatas(portaria.getPlanInicio(),portaria.getPlanFim() ));
		}
		if(portaria.getExecInicio()!=null && portaria.getExecFim()!=null){
			portaria.setExecDiasUteis(Util.diasEntreDatas(portaria.getExecInicio(),portaria.getExecFim() ));
		}
		if(portaria.getRelaInicio()!=null && portaria.getRelaFim()!=null){
			portaria.setRelaDiasUteis(Util.diasEntreDatas(portaria.getRelaInicio(),portaria.getRelaFim() ));
		}
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
	 
	//pega evento autocomplet remoção de seleção e atualiza listas
		public void unselectUGA(final UnselectEvent event) {
			try {
			    final UnidadeGestora tmp = (UnidadeGestora) event.getObject();
			    portaria.adincionarNaListaExcluidos(tmp);
			    unidadeGestoraLista.add(tmp);			
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		//pega evento autocomplet seleção de objetos e atualiza listas
		public void selectUGA(final SelectEvent event) {
			try {
			    final UnidadeGestora tmp = (UnidadeGestora) event.getObject();
		  			unidadeGestoraLista.remove(tmp);
		  			portaria.selecionarUGA(tmp);			
			} catch (Exception e) {
				// TODO: handle exception
			}
	  			
		}

	public Portaria getPortaria() {
		return portaria;
	}


	public void setPortaria(Portaria portaria) {
		this.portaria = portaria;
	}


	public Auditoria getAuditoria() {
		return auditoria;
	}


	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	public List<Portaria> getPortariaList() {
		return portariaList;
	}

	public void setPortariaList(List<Portaria> portariaList) {
		this.portariaList = portariaList;
	}

	public List<UnidadeGestoraPortaria> getUnidadeGestoraPortariaList() {
		return unidadeGestoraPortariaList;
	}

	public void setUnidadeGestoraPortariaList(List<UnidadeGestoraPortaria> unidadeGestoraPortariaList) {
		this.unidadeGestoraPortariaList = unidadeGestoraPortariaList;
	}

	public List<UnidadeGestora> getUnidadeGestoraSelecionadas() {
		return unidadeGestoraSelecionadas;
	}

	public void setUnidadeGestoraSelecionadas(List<UnidadeGestora> unidadeGestoraSelecionadas) {
		this.unidadeGestoraSelecionadas = unidadeGestoraSelecionadas;
	}
	
	public List<UnidadeGestora> getUnidadeGestoraLista() {
		return unidadeGestoraLista;
	}

	public List<UnidadeGestora> getUnidadeGestoraDaAuditoria() {
		return unidadeGestoraDaAuditoria;
	}

	public void setUnidadeGestoraDaAuditoria(List<UnidadeGestora> unidadeGestoraDaAuditoria) {
		this.unidadeGestoraDaAuditoria = unidadeGestoraDaAuditoria;
	}

	public List<UnidadeGestora> getUnidadeGestoraDaPortaria() {
		return unidadeGestoraDaPortaria;
	}

	public void setUnidadeGestoraDaPortaria(List<UnidadeGestora> unidadeGestoraDaPortaria) {
		this.unidadeGestoraDaPortaria = unidadeGestoraDaPortaria;
	}

	public UnidadeGestora getUnidadeGestora() {
		return unidadeGestora;
	}

	public void setUnidadeGestora(UnidadeGestora unidadeGestora) {
		this.unidadeGestora = unidadeGestora;
	}

	public List<UnidadeGestoraPortaria> getUnidadeGestoraPortariaList2() {
		return unidadeGestoraPortariaList2;
	}

	public void setUnidadeGestoraPortariaList2(List<UnidadeGestoraPortaria> unidadeGestoraPortariaList2) {
		this.unidadeGestoraPortariaList2 = unidadeGestoraPortariaList2;
	}

	public EquipeFiscalizacao getEquipeFiscalizacao() {
		return equipeFiscalizacao;
	}

	public void setEquipeFiscalizacao(EquipeFiscalizacao equipeFiscalizacao) {
		this.equipeFiscalizacao = equipeFiscalizacao;
	}

	public List<EquipeFiscalizacao> getEquipeFiscalizacaoList() {
		return equipeFiscalizacaoList;
	}

	public void setEquipeFiscalizacaoList(List<EquipeFiscalizacao> equipeFiscalizacaoList) {
		this.equipeFiscalizacaoList = equipeFiscalizacaoList;
	}

	public String getMsgTexto() {
		return msgTexto;
	}

	public void setMsgTexto(String msgTexto) {
		this.msgTexto = msgTexto;
	}

	public Portaria getPortariaView() {
		return portariaView;
	}

	public void setPortariaView(Portaria portariaView) {
		this.portariaView = portariaView;
	}

	
	
	
}
