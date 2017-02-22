package uo.sdi.tags;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import alb.util.date.DateUtil;

public class DateFormattedTag extends SimpleTagSupport {

	private Date fecha;

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void doTag() throws JspException, IOException {
		if(fecha != null){
			String mensaje = DateUtil.toString(fecha);
			getJspContext().getOut().write(mensaje);
		}
	}
}
