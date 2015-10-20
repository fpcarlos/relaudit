package br.leg.tcerr.difip.sisaudit.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the unidade_gestora_portaria database table.
 * 
 */
@Entity
@Table(name="unidade_gestora_portaria")
@NamedQuery(name="UnidadeGestoraPortaria.findAll", query="SELECT u FROM UnidadeGestoraPortaria u")
public class UnidadeGestoraPortaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="id_unidade_gestora")
	private Integer idUnidadeGestora;

	//bi-directional many-to-one association to Portaria
	@ManyToOne
	@JoinColumn(name="id_portaria")
	private Portaria portaria;

	public UnidadeGestoraPortaria() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUnidadeGestora() {
		return this.idUnidadeGestora;
	}

	public void setIdUnidadeGestora(Integer idUnidadeGestora) {
		this.idUnidadeGestora = idUnidadeGestora;
	}

	public Portaria getPortaria() {
		return this.portaria;
	}

	public void setPortaria(Portaria portaria) {
		this.portaria = portaria;
	}

}