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

public class PatientBillingServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PostgreSqlConn con = new PostgreSqlConn();
		
		String invoiceID = req.getParameter("invoiceID");
		String patientSINBill = req.getParameter("patientSINBill");
		if (invoiceID == null) {
			
			ArrayList<Invoice> invoices = con.getAllInvoicesByPatientSIN(patientSINBill);
			req.setAttribute("invoices", invoices);
			
			req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			return;	
		}
		
		Invoice invoice = con.getInvoiceByID(invoiceID);
		req.setAttribute("invoice", invoice);
		
		req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
	}
}
