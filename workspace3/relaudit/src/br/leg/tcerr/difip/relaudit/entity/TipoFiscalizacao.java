package br.leg.tcerr.difip.relaudit.entity;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the tipo_fiscalizacao database table.
 * 
 */
@Dependent
@Entity
@Table(name="tipo_fiscalizacao", schema="scsisaudit")
@NamedQuery(name="TipoFiscalizacao.findAll", query="SELECT t FROM TipoFiscalizacao t")
public class TipoFiscalizacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	private String objetivo;

	//bi-directional many-to-one association to Auditoria
	@OneToMany(mappedBy="tipoFiscalizacao")
	private List<Auditoria> auditorias;
	
	//bi-directional many-to-one association to Portaria
		@OneToMany(mappedBy="tipoFiscalizacao")
		private List<Portaria> portarias;

	public TipoFiscalizacao() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObjetivo() {
		return this.objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public List<Auditoria> getAuditorias() {
		return this.auditorias;
	}

	public void setAuditorias(List<Auditoria> auditorias) {
		this.auditorias = auditorias;
	}

	public Auditoria addAuditoria(Auditoria auditoria) {
		getAuditorias().add(auditoria);
		auditoria.setTipoFiscalizacao(this);

		return auditoria;
	}

	public Auditoria removeAuditoria(Auditoria auditoria) {
		getAuditorias().remove(auditoria);
		auditoria.setTipoFiscalizacao(null);

		return auditoria;
	}
	
	public List<Portaria> getPortarias() {
		return this.portarias;
	}

	public void setPortarias(List<Portaria> portarias) {
		this.portarias = portarias;
	}

	public Portaria addPortaria(Portaria portaria) {
		getPortarias().add(portaria);
		portaria.setTipoFiscalizacao(this);

		return portaria;
	}

	public Portaria removePortaria(Portaria portaria) {
		getPortarias().remove(portaria);
		portaria.setTipoFiscalizacao(null);

		return portaria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoFiscalizacao other = (TipoFiscalizacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TipoFiscalizacao [id=" + id + "]";
	}

	
	
}