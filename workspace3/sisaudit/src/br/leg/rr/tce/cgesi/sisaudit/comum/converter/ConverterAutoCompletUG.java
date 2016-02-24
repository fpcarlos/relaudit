package br.leg.rr.tce.cgesi.sisaudit.comum.converter;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.leg.rr.tce.cgesi.sisaudit.bean.SistemaBean;
import br.leg.rr.tce.cgesi.sisaudit.comum.entity.UnidadeGestora;
import br.leg.rr.tce.cgesi.sisaudit.ejb.SistemaEjb;

@FacesConverter("converterAutoCompletUG")
public class ConverterAutoCompletUG implements Converter {

	@EJB
	SistemaEjb sistemaEjb;
	
	@Inject
	private transient SistemaBean sistemaBean;
	
	
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		
		
        if(value != null && value.trim().length() > 0) {
            try {
            	SistemaBean service = (SistemaBean) fc.getExternalContext().getSessionMap().get("sistemaBean");
            	Integer aux=Integer.parseInt(value); 
            	
            	//return service.getUnidadeGestoraList().get(Integer.parseInt(value));
            	return sistemaBean.selecionarUnidadeGestora(Integer.parseInt(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((UnidadeGestora) object).getId());
        }
        else {
            return null;
        }
    }   
	
	
}
