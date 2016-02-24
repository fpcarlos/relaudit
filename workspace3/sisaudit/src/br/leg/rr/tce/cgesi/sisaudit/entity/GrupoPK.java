package br.leg.rr.tce.cgesi.sisaudit.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the grupo database table.
 * 
 */
@Embeddable
public class GrupoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Integer id;

	@Column(insertable=false, updatable=false)
	private String login;

	public GrupoPK() {
	}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogin() {
		return this.login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GrupoPK)) {
			return false;
		}
		GrupoPK castOther = (GrupoPK)other;
		return 
			this.id.equals(castOther.id)
			&& this.login.equals(castOther.login);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.login.hashCode();
		
		return hash;
	}
}