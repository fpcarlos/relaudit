package br.leg.rr.tce.cgesi.sisaudit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.leg.rr.tce.cgesi.sisaudit.comum.entity.UnidadeGestora;


/**
 * The persistent class for the unidade_gestora_portaria database table.
 * 
 */
@Entity
@Table(name="unidade_gestora_portaria", schema="scsisaudit")
@NamedQuery(name="UnidadeGestoraPortaria.findAll", query="SELECT u FROM UnidadeGestoraPortaria u")
public class UnidadeGestoraPortaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	
	//@ManyToOne
	//@JoinColumn(name="id_unidade_gestora")
	@Transient
	private UnidadeGestora unidadeGestora = new UnidadeGestora();

	//bi-directional many-to-one association to Portaria
	//@ManyToOne
	//@JoinColumn(name="id_portaria")
	@Transient
	private Portaria portaria = new Portaria();
	
	@Column(name="id_unidade_gestora")
	private Integer id_unidade_gestora;

	
	
	@Column(name="id_portaria")
	private Integer id_portaria;

	public UnidadeGestoraPortaria() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
		
	public UnidadeGestora getUnidadeGestora() {
		return unidadeGestora;
	}

	public void setUnidadeGestora(UnidadeGestora unidadeGestora) {
		this.unidadeGestora = unidadeGestora;
	}

	/*
	public Integer getIdUnidadeGestora() {
		return this.idUnidadeGestora;
	}

	public void setIdUnidadeGestora(Integer idUnidadeGestora) {
		this.idUnidadeGestora = idUnidadeGestora;
	}
*/
	public Portaria getPortaria() {
		return this.portaria;
	}

	public void setPortaria(Portaria portaria) {
		this.portaria = portaria;
	}

	public Integer getId_unidade_gestora() {
		return id_unidade_gestora;
	}

	public void setId_unidade_gestora(Integer id_unidade_gestora) {
		this.id_unidade_gestora = id_unidade_gestora;
	}

	public Integer getId_portaria() {
		return id_portaria;
	}

	public void setId_portaria(Integer id_portaria) {
		this.id_portaria = id_portaria;
	}
	
	
	

}