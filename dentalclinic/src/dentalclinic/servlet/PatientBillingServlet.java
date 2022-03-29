package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Invoice;
import dentalclinic.entities.PatientBilling;

@SuppressWarnings("serial")
public class PatientBillingServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PostgreSqlConn con = new PostgreSqlConn();
		
		String invoiceIDSelected = req.getParameter("invoiceIDSelected");
		if (invoiceIDSelected == null) {

			String patientSINBill = req.getParameter("patientSINBill");
			
			ArrayList<Invoice> invoices = con.getAllInvoicesByPatientSIN(patientSINBill);
			req.setAttribute("invoices", invoices);
			
			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			return;	
		}
		
		Invoice invoice = con.getInvoiceByID(invoiceIDSelected);
		req.setAttribute("invoice", invoice);
		
		req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
	}
}
