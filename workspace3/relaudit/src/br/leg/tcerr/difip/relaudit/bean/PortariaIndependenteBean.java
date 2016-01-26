package br.leg.tcerr.difip.relaudit.bean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.leg.tcerr.difip.relaudit.ejb.PortariaEjb;
import br.leg.tcerr.difip.relaudit.entity.Portaria;

@Named
@SessionScoped
public class PortariaIndependenteBean extends AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private transient SistemaBean sistemaBean;
	
	@Inject
	private Portaria portaria;

	@EJB
	private PortariaEjb portariaEjb;
	
	private String corpoPortaria;
	
	public String preparaCadastro(){
		try {
			Portaria portaria = new Portaria();
			/*
			setCorpoPortaria("<center><img src=\"../../resources/images/logorr.jpg\" /></center>"
					+ "<center>PORTARIA No XXXX/XXXX TCE/RR</center>"
					+ "<br></br><br></br>"
					+ "O Presidente do Tribunal de Contas do Estado de Roraima, no uso de suas atribuições legais,"
					+ "<br></br><br></br>"
					+ "RESOLVE:"
					+ "<br></br><br></br>"
					+ "");
					*/
			//portaria.setObjetivo(corpoPortaria);
			
			return redirect("/sistema/portaria/cadastroPortariaIndependente.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
			return null;
		}
		
	}
	
	public String viewPortaria(Portaria aux){
		try {
			setCorpoPortaria("<center><img src=\"../../resources/images/logorr.jpg\" /></center>"
					+ "<center>PORTARIA No"+ portaria.getNumeroPortaria()+"/"+portaria.getAnoPortaria()+"XXXX TCE/RR</center>"
					+ "<br></br><br></br>"
					+ portaria.getObjetivo());
			return redirect("/sistema/portaria/_viewPortaira.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
			showFacesMessage(e.getMessage(), 4);
			return null;
		}
		
	}

	public String getCorpoPortaria() {
		return corpoPortaria;
	}

	public void setCorpoPortaria(String corpoPortaria) {
		this.corpoPortaria = corpoPortaria;
	}

	public Portaria getPortaria() {
		return portaria;
	}

	public void setPortaria(Portaria portaria) {
		this.portaria = portaria;
	}


	
	
	
	
}
