package br.leg.tcerr.difip.sisaudit.entity;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the portaria database table.
 * 
 */
@Dependent
@Entity
@Table(name="portaria", schema="scsisaudit")
@NamedQuery(name="Portaria.findAll", query="SELECT p FROM Portaria p")
public class Portaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="ano_portaria_revogada")
	private String anoPortariaRevogada;

	@Temporal(TemporalType.DATE)
	@Column(name="data_assinatura")
	private Date dataAssinatura;

	@Temporal(TemporalType.DATE)
	@Column(name="data_publica_doe")
	private Date dataPublicaDoe;

	private String deliberacao;

	@Column(name="exec_dias_uteis")
	private Integer execDiasUteis;

	@Temporal(TemporalType.DATE)
	@Column(name="exec_fim")
	private Date execFim;

	@Temporal(TemporalType.DATE)
	@Column(name="exec_inicio")
	private Date execInicio;

	@Column(name="id_auditoria")
	private Integer idAuditoria;

	@Column(name="id_servidor")
	private Integer idServidor;

	@Column(name="id_tipo_fiscalizacao")
	private Integer idTipoFiscalizacao;

	@Column(name="numero_portaria_revogada")
	private String numeroPortariaRevogada;

	@Column(name="numero_publica_doe")
	private String numeroPublicaDoe;

	private String objetivo;

	@Column(name="plan_dias_uteis")
	private Integer planDiasUteis;

	@Temporal(TemporalType.DATE)
	@Column(name="plan_fim")
	private Date planFim;

	@Temporal(TemporalType.DATE)
	@Column(name="plan_inicio")
	private Date planInicio;

	@Column(name="processo_ano")
	private String processoAno;

	@Column(name="processo_numero")
	private String processoNumero;

	@Column(name="rela_dias_uteis")
	private Integer relaDiasUteis;

	@Temporal(TemporalType.DATE)
	@Column(name="rela_fim")
	private Date relaFim;

	@Temporal(TemporalType.DATE)
	@Column(name="rela_inicio")
	private Date relaInicio;

	//bi-directional many-to-one association to PortariasAndamento
	@OneToMany(mappedBy="portaria")
	private List<PortariasAndamento> portariasAndamentos;

	//bi-directional many-to-one association to UnidadeGestoraPortaria
	@OneToMany(mappedBy="portaria")
	private List<UnidadeGestoraPortaria> unidadeGestoraPortarias;

	public Portaria() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnoPortariaRevogada() {
		return this.anoPortariaRevogada;
	}

	public void setAnoPortariaRevogada(String anoPortariaRevogada) {
		this.anoPortariaRevogada = anoPortariaRevogada;
	}

	public Date getDataAssinatura() {
		return this.dataAssinatura;
	}

	public void setDataAssinatura(Date dataAssinatura) {
		this.dataAssinatura = dataAssinatura;
	}

	public Date getDataPublicaDoe() {
		return this.dataPublicaDoe;
	}

	public void setDataPublicaDoe(Date dataPublicaDoe) {
		this.dataPublicaDoe = dataPublicaDoe;
	}

	public String getDeliberacao() {
		return this.deliberacao;
	}

	public void setDeliberacao(String deliberacao) {
		this.deliberacao = deliberacao;
	}

	public Integer getExecDiasUteis() {
		return this.execDiasUteis;
	}

	public void setExecDiasUteis(Integer execDiasUteis) {
		this.execDiasUteis = execDiasUteis;
	}

	public Date getExecFim() {
		return this.execFim;
	}

	public void setExecFim(Date execFim) {
		this.execFim = execFim;
	}

	public Date getExecInicio() {
		return this.execInicio;
	}

	public void setExecInicio(Date execInicio) {
		this.execInicio = execInicio;
	}

	public Integer getIdAuditoria() {
		return this.idAuditoria;
	}

	public void setIdAuditoria(Integer idAuditoria) {
		this.idAuditoria = idAuditoria;
	}

	public Integer getIdServidor() {
		return this.idServidor;
	}

	public void setIdServidor(Integer idServidor) {
		this.idServidor = idServidor;
	}

	public Integer getIdTipoFiscalizacao() {
		return this.idTipoFiscalizacao;
	}

	public void setIdTipoFiscalizacao(Integer idTipoFiscalizacao) {
		this.idTipoFiscalizacao = idTipoFiscalizacao;
	}

	public String getNumeroPortariaRevogada() {
		return this.numeroPortariaRevogada;
	}

	public void setNumeroPortariaRevogada(String numeroPortariaRevogada) {
		this.numeroPortariaRevogada = numeroPortariaRevogada;
	}

	public String getNumeroPublicaDoe() {
		return this.numeroPublicaDoe;
	}

	public void setNumeroPublicaDoe(String numeroPublicaDoe) {
		this.numeroPublicaDoe = numeroPublicaDoe;
	}

	public String getObjetivo() {
		return this.objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public Integer getPlanDiasUteis() {
		return this.planDiasUteis;
	}

	public void setPlanDiasUteis(Integer planDiasUteis) {
		this.planDiasUteis = planDiasUteis;
	}

	public Date getPlanFim() {
		return this.planFim;
	}

	public void setPlanFim(Date planFim) {
		this.planFim = planFim;
	}

	public Date getPlanInicio() {
		return this.planInicio;
	}

	public void setPlanInicio(Date planInicio) {
		this.planInicio = planInicio;
	}

	public String getProcessoAno() {
		return this.processoAno;
	}

	public void setProcessoAno(String processoAno) {
		this.processoAno = processoAno;
	}

	public String getProcessoNumero() {
		return this.processoNumero;
	}

	public void setProcessoNumero(String processoNumero) {
		this.processoNumero = processoNumero;
	}

	public Integer getRelaDiasUteis() {
		return this.relaDiasUteis;
	}

	public void setRelaDiasUteis(Integer relaDiasUteis) {
		this.relaDiasUteis = relaDiasUteis;
	}

	public Date getRelaFim() {
		return this.relaFim;
	}

	public void setRelaFim(Date relaFim) {
		this.relaFim = relaFim;
	}

	public Date getRelaInicio() {
		return this.relaInicio;
	}

	public void setRelaInicio(Date relaInicio) {
		this.relaInicio = relaInicio;
	}

	public List<PortariasAndamento> getPortariasAndamentos() {
		return this.portariasAndamentos;
	}

	public void setPortariasAndamentos(List<PortariasAndamento> portariasAndamentos) {
		this.portariasAndamentos = portariasAndamentos;
	}

	public PortariasAndamento addPortariasAndamento(PortariasAndamento portariasAndamento) {
		getPortariasAndamentos().add(portariasAndamento);
		portariasAndamento.setPortaria(this);

		return portariasAndamento;
	}

	public PortariasAndamento removePortariasAndamento(PortariasAndamento portariasAndamento) {
		getPortariasAndamentos().remove(portariasAndamento);
		portariasAndamento.setPortaria(null);

		return portariasAndamento;
	}

	public List<UnidadeGestoraPortaria> getUnidadeGestoraPortarias() {
		return this.unidadeGestoraPortarias;
	}

	public void setUnidadeGestoraPortarias(List<UnidadeGestoraPortaria> unidadeGestoraPortarias) {
		this.unidadeGestoraPortarias = unidadeGestoraPortarias;
	}

	public UnidadeGestoraPortaria addUnidadeGestoraPortaria(UnidadeGestoraPortaria unidadeGestoraPortaria) {
		getUnidadeGestoraPortarias().add(unidadeGestoraPortaria);
		unidadeGestoraPortaria.setPortaria(this);

		return unidadeGestoraPortaria;
	}

	public UnidadeGestoraPortaria removeUnidadeGestoraPortaria(UnidadeGestoraPortaria unidadeGestoraPortaria) {
		getUnidadeGestoraPortarias().remove(unidadeGestoraPortaria);
		unidadeGestoraPortaria.setPortaria(null);

		return unidadeGestoraPortaria;
	}

}