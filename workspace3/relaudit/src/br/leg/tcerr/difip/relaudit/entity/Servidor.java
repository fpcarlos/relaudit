package br.leg.tcerr.difip.relaudit.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the servidor database table.
 * 
 */
@Entity
@Table(name="servidor", schema="scsisaudit")
@NamedQuery(name="Servidor.findAll", query="SELECT s FROM Servidor s")
public class Servidor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	@Column(name="auditor_fiscal")
	private String auditorFiscal;

	private String autoridade;

	private String formacao;

	private String nome;

	private String senha;
	
	private String login;

	@Column(name="servidor_administrador")
	private String servidorAdministrador;

	@Column(name="servidor_atualiza_publicacao")
	private String servidorAtualizaPublicacao;

	@Column(name="servidor_autorizador")
	private String servidorAutorizador;

	@Column(name="servidor_gabinete_autoridade")
	private String servidorGabineteAutoridade;

	private String sexo;

	//bi-directional many-to-one association to Grupo
	@OneToMany(mappedBy="servidor")
	private List<Grupo> grupos;

	@Transient
	private List<EquipeFiscalizacao> equipeFiscalizacaos;

	//bi-directional many-to-one association to Portaria
	//@OneToMany(mappedBy="servidor")
	@Transient
	private List<Portaria> portarias;

	
	public Servidor() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuditorFiscal() {
		return this.auditorFiscal;
	}

	public void setAuditorFiscal(String auditorFiscal) {
		this.auditorFiscal = auditorFiscal;
	}

	public String getAutoridade() {
		return this.autoridade;
	}

	public void setAutoridade(String autoridade) {
		this.autoridade = autoridade;
	}

	public String getFormacao() {
		return this.formacao;
	}

	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getServidorAdministrador() {
		return this.servidorAdministrador;
	}

	public void setServidorAdministrador(String servidorAdministrador) {
		this.servidorAdministrador = servidorAdministrador;
	}

	public String getServidorAtualizaPublicacao() {
		return this.servidorAtualizaPublicacao;
	}

	public void setServidorAtualizaPublicacao(String servidorAtualizaPublicacao) {
		this.servidorAtualizaPublicacao = servidorAtualizaPublicacao;
	}

	public String getServidorAutorizador() {
		return this.servidorAutorizador;
	}

	public void setServidorAutorizador(String servidorAutorizador) {
		this.servidorAutorizador = servidorAutorizador;
	}

	public String getServidorGabineteAutoridade() {
		return this.servidorGabineteAutoridade;
	}

	public void setServidorGabineteAutoridade(String servidorGabineteAutoridade) {
		this.servidorGabineteAutoridade = servidorGabineteAutoridade;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<Grupo> getGrupos() {
		return this.grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Grupo addGrupo(Grupo grupo) {
		getGrupos().add(grupo);
		grupo.setServidor(this);

		return grupo;
	}

	public Grupo removeGrupo(Grupo grupo) {
		getGrupos().remove(grupo);
		grupo.setServidor(null);

		return grupo;
	}

	public List<EquipeFiscalizacao> getEquipeFiscalizacaos() {
		return this.equipeFiscalizacaos;
	}

	public void setEquipeFiscalizacaos(List<EquipeFiscalizacao> equipeFiscalizacaos) {
		this.equipeFiscalizacaos = equipeFiscalizacaos;
	}

	public EquipeFiscalizacao addEquipeFiscalizacao(EquipeFiscalizacao equipeFiscalizacao) {
		getEquipeFiscalizacaos().add(equipeFiscalizacao);
		equipeFiscalizacao.setServidor(this);

		return equipeFiscalizacao;
	}

	public EquipeFiscalizacao removeEquipeFiscalizacao(EquipeFiscalizacao equipeFiscalizacao) {
		getEquipeFiscalizacaos().remove(equipeFiscalizacao);
		equipeFiscalizacao.setServidor(null);

		return equipeFiscalizacao;
	}

	public List<Portaria> getPortarias() {
		return this.portarias;
	}

	public void setPortarias(List<Portaria> portarias) {
		this.portarias = portarias;
	}
/*
	public Portaria addPortaria(Portaria portaria) {
		getPortarias().add(portaria);
		portaria.setServidor(this);

		return portaria;
	}

	public Portaria removePortaria(Portaria portaria) {
		getPortarias().remove(portaria);
		portaria.setServidor(null);

		return portaria;
	}
*/

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	
}