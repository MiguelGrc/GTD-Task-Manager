package uo.sdi.tags;

import java.io.IOException;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import alb.util.date.DateUtil;

public class PlaneadoTag extends SimpleTagSupport {

	private Date fecha;

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void doTag() throws JspException, IOException {
		String mensaje = "<td>   </td>";
		if (fecha != null) {
			mensaje = "<td><p>" + fecha + "<p></td>";
			if (fecha.before(DateUtil.today())) {
				mensaje = "<td><p style=\"color:red;\">" + fecha + "</p></td>";
			}
		}

		getJspContext().getOut().write(mensaje);

	}
}
