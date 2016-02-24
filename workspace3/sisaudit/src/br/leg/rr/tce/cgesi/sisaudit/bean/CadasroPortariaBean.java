package br.leg.rr.tce.cgesi.sisaudit.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.sun.enterprise.universal.StringUtils;

import br.leg.rr.tce.cgesi.sisaudit.comum.entity.UnidadeGestora;
import br.leg.rr.tce.cgesi.sisaudit.comum.util.Util;
import br.leg.rr.tce.cgesi.sisaudit.ejb.AuditoriaEjb;
import br.leg.rr.tce.cgesi.sisaudit.ejb.EquipeFiscalizacaoEjb;
import br.leg.rr.tce.cgesi.sisaudit.ejb.PortariaEjb;
import br.leg.rr.tce.cgesi.sisaudit.ejb.ServidorEjb;
import br.leg.rr.tce.cgesi.sisaudit.ejb.StatusPortariaEjb;
import br.leg.rr.tce.cgesi.sisaudit.ejb.UnidadeGestoraEjb;
import br.leg.rr.tce.cgesi.sisaudit.ejb.UnidadeGestoraPortariaEjb;
import br.leg.rr.tce.cgesi.sisaudit.entity.Auditoria;
import br.leg.rr.tce.cgesi.sisaudit.entity.EquipeFiscalizacao;
import br.leg.rr.tce.cgesi.sisaudit.entity.Portaria;
import br.leg.rr.tce.cgesi.sisaudit.entity.PortariasAndamento;
import br.leg.rr.tce.cgesi.sisaudit.entity.Servidor;
import br.leg.rr.tce.cgesi.sisaudit.entity.StatusPortaria;
import br.leg.rr.tce.cgesi.sisaudit.entity.UnidadeGestoraAuditoria;
import br.leg.rr.tce.cgesi.sisaudit.entity.UnidadeGestoraPortaria;
import br.leg.rr.tce.cgesi.sisaudit.seguranca.bean.UsuarioBean;

@Named
@SessionScoped
public class CadasroPortariaBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Inject
	private transient SistemaBean sistemaBean;
	
	@Inject
	private transient UsuarioBean usuarioBean;
	
	@Inject
	private Portaria portaria;

	@Inject
	private Auditoria auditoria;

	@Inject
	private EquipeFiscalizacao equipeFiscalizacao;

	@Inject
	private UnidadeGestora unidadeGestora;
	
	@Inject
	private PortariasAndamento portariasAndamento;

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

	@EJB
	private ServidorEjb servidorEjb;
	
	@EJB
	private StatusPortariaEjb statusPortariaEjb;

	private List<UnidadeGestora> unidadeGestoraLista = new ArrayList<UnidadeGestora>();
	private List<Portaria> portariaList = new ArrayList<Portaria>();
	private List<UnidadeGestoraPortaria> unidadeGestoraPortariaList = new ArrayList<UnidadeGestoraPortaria>();
	private List<UnidadeGestoraPortaria> unidadeGestoraPortariaList2 = new ArrayList<UnidadeGestoraPortaria>();

	private List<UnidadeGestora> unidadeGestoraDaAuditoria = new ArrayList<UnidadeGestora>();
	private List<UnidadeGestora> unidadeGestoraSelecionadas = new ArrayList<UnidadeGestora>();
	private List<UnidadeGestora> unidadeGestoraDaPortaria = new ArrayList<UnidadeGestora>();

	private List<EquipeFiscalizacao> equipeFiscalizacaoList = new ArrayList<EquipeFiscalizacao>();

	private List<Servidor> servidorList = new ArrayList<Servidor>();

	private List<Servidor> servidorAutoridadeList = new ArrayList<Servidor>();
	
	private String msgTexto;
	private Portaria portariaView = new Portaria();

	// private Tex textoPortaria;

	public CadasroPortariaBean() {
		super();
	}

	public String prepararEditPortaria(Portaria aux) {
		try {
			auditoria = new Auditoria();
			auditoria = auditoriaEjb.carregarAuditoria(aux.getIdAuditoria());
			portaria = aux;
			portaria.setAuditoria(auditoria);
			portaria.setIdAuditoria(portaria.getAuditoria().getId());

			unidadeGestoraDaAuditoria = new ArrayList<UnidadeGestora>();
			unidadeGestoraSelecionadas = new ArrayList<UnidadeGestora>();
			unidadeGestoraDaPortaria = new ArrayList<UnidadeGestora>();

			for (UnidadeGestoraAuditoria x : portaria.getAuditoria().getUnidadeGestoraAuditorias()) {
				UnidadeGestoraPortaria unidGP = new UnidadeGestoraPortaria();
				unidGP.setUnidadeGestora(x.getUnidadeGestora());
				// unidGP.setIdUnidadeGestora(x.getUnidadeGestora().getId());
				unidGP.setPortaria(aux);
				unidadeGestoraDaAuditoria.add(x.getUnidadeGestora());
				portaria.getListaUnidadeGestoraDaPortaria().add(unidGP);
			}

			for (UnidadeGestoraPortaria x : unidadeGestoraPortariaEjb.findIdPortaria(portaria.getId())) {
				UnidadeGestora unG = new UnidadeGestora();
				// unG=sistemaBean.getUnidadeGestoraList().get(x.getIdUnidadeGestora());
				unG = x.getUnidadeGestora();
				unidadeGestoraSelecionadas.add(unG);
				// unGP.setIdUnidadeGestora(x.getId());
				// unGP.setPortaria(portaria);
				// portaria.addUnidadeGestoraPortaria(unGP);
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

	// prepara editar portaria de auditoria
	public void selecionandoUGP() {
		try {
			unidadeGestoraPortariaList = new ArrayList<UnidadeGestoraPortaria>();
			unidadeGestoraPortariaList2 = new ArrayList<UnidadeGestoraPortaria>();

			Map<Integer, UnidadeGestora> mapUGS = new HashMap<Integer, UnidadeGestora>();
			Map<Integer, UnidadeGestora> mapUGE = new HashMap<Integer, UnidadeGestora>();
			Map<Integer, UnidadeGestora> mapUGP = new HashMap<Integer, UnidadeGestora>();
			// unidades selecionadas
			for (UnidadeGestora x : unidadeGestoraSelecionadas) {
				mapUGS.put(x.getId(), x);
				UnidadeGestoraPortaria ugp = new UnidadeGestoraPortaria();
				ugp.setPortaria(portaria);
				ugp.setUnidadeGestora(x);
				unidadeGestoraPortariaList.add(ugp);
			}
			// da auditoria
			for (UnidadeGestoraAuditoria x : portaria.getAuditoria().getUnidadeGestoraAuditorias()) {
				mapUGE.put(x.getUnidadeGestora().getId(), x.getUnidadeGestora());
				UnidadeGestoraPortaria ugp = new UnidadeGestoraPortaria();
				ugp.setPortaria(portaria);
				ugp.setUnidadeGestora(x.getUnidadeGestora());
				// portaria.getUnidadeGestoraPortariaExcluidas().add(ugp);
			}
			// daportaria
			for (UnidadeGestoraPortaria x : portaria.getUnidadeGestoraPortarias()) {
				mapUGP.put(x.getUnidadeGestora().getId(), x.getUnidadeGestora());
				UnidadeGestoraPortaria ugp = new UnidadeGestoraPortaria();
				ugp.setId(x.getId());
				ugp.setPortaria(portaria);
				ugp.setUnidadeGestora(x.getUnidadeGestora());
				unidadeGestoraPortariaList2.add(ugp);
				if (mapUGS.containsKey(x.getUnidadeGestora().getId())) {
					portaria.removeUnidadeGestoraPortaria(ugp);
				}
			}
			portaria.setUnidadeGestoraPortarias(getUnidadeGestoraPortariaList());
			portaria.setUnidadeGestoraPortariaExcluidas(unidadeGestoraPortariaList2);
			portaria.setEquipeFiscalizacaoList(getEquipeFiscalizacaoList());
			this.salvar();

		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
		}

	}

	public String prepararNovaPortaria() {
		try {
			portaria = new Portaria();

			// portaria = portariaEjb.iniciarPortiariaComAuditoria(auditoria);
			// portaria.setEquipeFiscalizacaoList(new
			// ArrayList<EquipeFiscalizacao>()); // System.out.println("");
			// portaria.setIdAuditoria(auditoria.getId());
			// portaria.setObjetivo(auditoria.getObjetivo());

			// portaria.setPlanInicio(auditoria.getPlanInicioPrev());
			// portaria.setPlanFim(auditoria.getPlanFimPrev());
			// portaria.setPlanDiasUteis(auditoria.getPlanDiasUteisPrev());

			// portaria.setExecInicio(auditoria.getExecInicioPrev());
			// portaria.setExecFim(auditoria.getExecFimPrev());
			// portaria.setExecDiasUteis(auditoria.getExecDiasUteisPrev());

			// portaria.setRelaInicio(auditoria.getRelaInicioPrev());
			// portaria.setRelaFim(auditoria.getRelaFimPrev());
			// portaria.setRelaDiasUteis(auditoria.getRelaDiasUteisPrev());

			// portaria.setTipoFiscalizacao(auditoria.getTipoFiscalizacao());

			servidorAutoridadeList = new ArrayList<Servidor>();

			for (Servidor stemp : servidorEjb.findAll()) {
				String vtipo = stemp.getAutoridade();
				if (vtipo.contains("S"))
					servidorAutoridadeList.add(stemp);
			}
			/*
			 * unidadeGestoraDaAuditoria = new ArrayList<UnidadeGestora>();
			 * unidadeGestoraSelecionadas = new ArrayList<UnidadeGestora>();
			 * unidadeGestoraDaPortaria = new ArrayList<UnidadeGestora>();
			 * 
			 * for (UnidadeGestoraAuditoria x :
			 * portaria.getAuditoria().getUnidadeGestoraAuditorias()) {
			 * UnidadeGestoraPortaria unidGP = new UnidadeGestoraPortaria();
			 * unidGP.setUnidadeGestora(x.getUnidadeGestora()); //
			 * unidGP.setIdUnidadeGestora(x.getUnidadeGestora().getId());
			 * //unidGP.setPortaria(aux);
			 * unidadeGestoraDaAuditoria.add(x.getUnidadeGestora());
			 * //portaria.getListaUnidadeGestoraDaPortaria().add(unidGP); }
			 */
			/*
			 * for (UnidadeGestoraPortaria x :
			 * unidadeGestoraPortariaEjb.findIdPortaria(portaria.getId())) {
			 * UnidadeGestora unG = new UnidadeGestora(); //
			 * unG=sistemaBean.getUnidadeGestoraList().get(x.getIdUnidadeGestora
			 * ()); unG = x.getUnidadeGestora();
			 * unidadeGestoraSelecionadas.add(unG); //
			 * unGP.setIdUnidadeGestora(x.getId()); //
			 * unGP.setPortaria(portaria); //
			 * portaria.addUnidadeGestoraPortaria(unGP); }
			 */

			// portaria.setServidor(sistemaBean.getServidorMap().get(portaria.getServidor().getId()));
			// Calendar cal = Calendar.getInstance();
			// int year = cal.get(Calendar.YEAR);
			String vano = String.valueOf((Calendar.getInstance().get(Calendar.YEAR)));
			String vnum = "";
			/*
			for (Portaria temp : portariaEjb.ultimoNumeroPortaria(vano)) {
				vnum = temp.getNumeroPortaria();
			}
			*/
			//Integer temp = portariaEjb.ultimoNumeroPortaria(vano);
			//vnum=temp.toString();
			//List<Portaria> temp=portariaEjb.ultimoNumeroPortaria(vano);
			String temp=portariaEjb.ultimoNumeroPortaria(vano);
			//vnum=temp.get(0).getNumeroPortaria();
			vnum=temp;
			if (vnum.isEmpty())
				vnum = "000";

			//DecimalFormat df = new DecimalFormat("000");
			//vnum =  df.format(vnum.toString());

			//int nnum = Integer.valueOf(vnum) + 1;
			//DecimalFormat df = new DecimalFormat("000");
			//vnum = df.format(vnum = df.format(temp.get(0).getNumeroPortaria());
			// return df.format(Integer.parseInt(sb.toString()));
			//String vnum2 = String.format("%03d", vnum);
			String vnum2 = StringUtils.padLeft(vnum, 3,'0');
			
			portaria.setNumeroPortaria(vnum2);
			portaria.setAnoPortaria(vano);
			
			 //portariasAndamentoList = new ArrayList<PortariasAndamento>();
			//portaria.setPortariasAndamentos(new ArrayList<PortariasAndamentos>());

			// return
			// redirect("/sistema/portaria/cadastro/frmCadastroPortaria.xhtml");
			return redirect("/sistema/portaria/cadastro/frmCadPortaria.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
			return null;
		}
	}

	public String editarMinuta(Portaria aux) {
		try {
			portaria = portariaEjb.pegarPortaria(aux.getId());
			servidorAutoridadeList = new ArrayList<Servidor>();

			for (Servidor stemp : servidorEjb.findAll()) {
				String vtipo = stemp.getAutoridade();
				if (vtipo.contains("S"))
					servidorAutoridadeList.add(stemp);
			}
			portaria.setNumeroPortaria(StringUtils.padLeft(portaria.getNumeroPortaria(), 3,'0'));
			return redirect("/sistema/portaria/cadastro/frmCadPortariaEtapa1.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
			return null;
		}
	}

	public void salvarMinuta() {
		try {

			// selecionandoUGP();
			StatusPortaria stPortaria = new StatusPortaria();
			stPortaria=statusPortariaEjb.pegarStatusPortariaId(1);
			
			System.out.println(stPortaria);
			
			PortariasAndamento pAndamento = new PortariasAndamento();
			
			
			pAndamento.setStatusDate(Util.hoje());
			pAndamento.setStatusJustificativa(stPortaria.getNome());
			pAndamento.setStatusPortaria(stPortaria.getId());
			pAndamento.setStatusUsr(usuarioBean.getMostraUser());
			portaria.setPortariasAndamentos(new ArrayList<PortariasAndamento>());
			portaria.addPortariasAndamento(pAndamento);

			//portariaEjb.salvar(portaria);
			portariaEjb.salvarMinuta(portaria);
			portariaList = new ArrayList<Portaria>();
			portariaList = portariaEjb.listaPortaria();
			
			showFacesMessage("salvo com sucesso!!!", 2);
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
		}
	}

	public String abrirListaPortaria() throws Exception {
		portariaList = new ArrayList<Portaria>();
		portariaList = portariaEjb.listaPortaria();

		Map<Integer, Portaria> mapPortaria = new HashMap<Integer, Portaria>();
		String listaId = "";

		List<UnidadeGestoraPortaria> listaUGP = new ArrayList<UnidadeGestoraPortaria>();
		listaUGP=unidadeGestoraPortariaEjb.findIAll();

		//if (!listaUGP.isEmpty()) {
			portaria.setMostraCampo(true);

			for (Portaria ptemp : portariaList) {
				mapPortaria.put(ptemp.getId(), ptemp);

				if (listaId.length() > 0) {
					listaId = listaId + "," + ptemp.getId();
				} else {
					listaId = ptemp.getId().toString();
				}

			}
			listaUGP = unidadeGestoraPortariaEjb.listaUGDasPortaria(listaId);

			for (UnidadeGestoraPortaria ltemp : listaUGP) {
				mapPortaria.get(ltemp.getId_portaria()).getListaUnidadeGestoraDaPortaria().add(ltemp);

				ltemp.setUnidadeGestora(sistemaBean.getUnidadeGestoraMap().get(ltemp.getId_unidade_gestora()));
				ltemp.setPortaria(mapPortaria.get(ltemp.getId_portaria()));
			}
		//}
		return redirect("/sistema/portaria/listaPortarias.xhtml");
	}

	public String preprarCadastroPortaria(Auditoria aux) {
		try {
			// auditoria = new Auditoria();
			// auditoria = auditoriaEjb.carregarAuditoria(aux.getId());
			// auditoria.setPortariaList(portariaEjb.findIdAuditoria(aux.getId()));
			// portaria = new Portaria();
			// portaria = portariaEjb.iniciarPortiariaComAuditoria(aux);
			// auditoria = portaria.getAuditoria();
			auditoria = new Auditoria();
			auditoria = aux;
			auditoria.setPortariaList(portariaEjb.findIdAuditoria(aux.getId()));

			// portaria = portariaEjb.findIdAuditoria(aux.getId()).get(0);

			return redirect("/sistema/portaria/cadastroPortariaAuditoria.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
			return null;

		}

	}

	public String preparaCadastroPortariaSemAuditoria() {
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

	public void remEquipe(EquipeFiscalizacao eq) {
		try {
			equipeFiscalizacaoList.remove(eq);
			equipeFiscalizacaoEjb.remove(eq);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void addEquipeSemAuditoria() {
		EquipeFiscalizacao eqLista = new EquipeFiscalizacao();
		eqLista.setPortaria(portaria);
		eqLista.setServidor(equipeFiscalizacao.getServidor());
		eqLista.setTipoAuditor(equipeFiscalizacao.getTipoAuditor());
		equipeFiscalizacaoList.add(eqLista);
		portaria.getEquipeFiscalizacaoList().add(eqLista);
	}

	public void remEquipeSemAuditoria(EquipeFiscalizacao eq) {
		equipeFiscalizacaoList.remove(eq);
		portaria.getEquipeFiscalizacaoList().remove(eq);
	}

	public String viewPortaria(Portaria aux) {
		portariaView = portariaEjb.pegarPortaria(aux.getId());

		msgTexto = "<t:panelGrid style=\"margin-top:20px; align: justufy\" width=\"800\" >"
				+ "<div align=\"center\"><img src=\"../../resources/images/logorr.jpg\" /></div>"
				+ "<div align=\"center\">PORTARIA No XXXX/2015-TCE/RR</div>" + "</t:panelGrid><div width=\"300px\">"
				+ portariaView.getObjetivo() + "</div>" + "<div></div>";
		return redirect("/sistema/portaria/_viewPortaria.xhtml");
	}

	public String abrirPdf() {
		return redirect("/sistema/portaria/_viewPortaria.xhtml");
	}

	// executar opção salvar
	public void salvar() {
		try {

			// selecionandoUGP();

			portariaEjb.salvarPacote(portaria);

			showFacesMessage("salvo com sucesso!!!", 2);
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
		}
	}

	// remove portaria
	public void remover(Portaria aux) {
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
		if (portaria.getPlanInicio() != null && portaria.getPlanFim() != null) {
			portaria.setPlanDiasUteis(Util.diasEntreDatas(portaria.getPlanInicio(), portaria.getPlanFim()));
		}
		if (portaria.getExecInicio() != null && portaria.getExecFim() != null) {
			portaria.setExecDiasUteis(Util.diasEntreDatas(portaria.getExecInicio(), portaria.getExecFim()));
		}
		if (portaria.getRelaInicio() != null && portaria.getRelaFim() != null) {
			portaria.setRelaDiasUteis(Util.diasEntreDatas(portaria.getRelaInicio(), portaria.getRelaFim()));
		}
	}

	public List<UnidadeGestora> completeUG(String query) {
		List<UnidadeGestora> ugFiltrada = new ArrayList<UnidadeGestora>();
		for (int i = 0; i < unidadeGestoraLista.size(); i++) {
			UnidadeGestora skin = unidadeGestoraLista.get(i);
			if (skin.getNomeSilga().toLowerCase().startsWith(query)) {
				ugFiltrada.add(skin);
			}
		}
		return ugFiltrada;
	}

	// pega evento autocomplet remoï¿½ï¿½o de seleï¿½ï¿½o e atualiza listas
	public void unselectUGA(final UnselectEvent event) {
		try {
			final UnidadeGestora tmp = (UnidadeGestora) event.getObject();
			portaria.adincionarNaListaExcluidos(tmp);
			unidadeGestoraLista.add(tmp);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// pega evento autocomplet seleï¿½ï¿½o de objetos e atualiza listas
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

	public List<Servidor> getServidorAutoridadeList() {
		return servidorAutoridadeList;
	}

	public void setServidorAutoridadeList(List<Servidor> servidorAutoridadeList) {
		this.servidorAutoridadeList = servidorAutoridadeList;
	}

	public PortariasAndamento getPortariasAndamento() {
		return portariasAndamento;
	}

	public void setPortariasAndamento(PortariasAndamento portariasAndamento) {
		this.portariasAndamento = portariasAndamento;
	}

	
	
}
