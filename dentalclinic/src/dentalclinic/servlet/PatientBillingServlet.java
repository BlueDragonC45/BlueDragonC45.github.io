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
		
		String patientSINBillForm = req.getParameter("patientSINBillForm");
		if (patientSINBillForm == null) {
			
			String invoiceIDSelected = req.getParameter("invoiceIDSelected");
			if (invoiceIDSelected == null) {

				String patientSINBill = req.getParameter("patientSINBill");
				
				ArrayList<Invoice> invoices = con.getAllInvoicesByPatientSIN(patientSINBill);
				req.setAttribute("invoices", invoices);
				
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				return;	
			} else {
				
				Invoice invoice = con.getInvoiceByID(invoiceIDSelected);
				
				PatientBilling bill = con.getPatientBillingByKey(invoice.getPatientSIN(), invoice.getInvoiceID());
				if (bill.getTotalAmount() == null) {//has not paid

					req.setAttribute("invoice", invoice);
					req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
					
				} else if (bill.getTotalAmount().equals(invoice.getTotalFeeCharge())) {//paid in full

					req.setAttribute("outcomeB", "no pending fees");
					req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
					
				} else {//partially indebted

					req.setAttribute("invoice", invoice);
					req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				}
			}
		} else {

			//patientSINBillForm defined before first if statement
			String invoiceID = req.getParameter("invoiceIDBillForm");
			String employeeSIN = req.getParameter("employeeSINBillForm");
			String patientPortion = req.getParameter("patientPortionBillForm");
			String insurancePortion = req.getParameter("insurancePortionBillForm");
			String employeePortion = req.getParameter("employeePortionBillForm");
			String payMethod = req.getParameter("payMethodBillForm");
			String total = req.getParameter("totalBillForm");
			
			PatientBilling newBill =
					new PatientBilling(patientSINBillForm, invoiceID,
									   employeeSIN, patientPortion, employeePortion,
									   insurancePortion, total, payMethod);
			
			if (con.billPatient(newBill)) {

				req.setAttribute("patientSINAfterBill", patientSINBillForm);
				req.setAttribute("billTotal", total);
				
				req.setAttribute("outcomeB", "bill success");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				
			} else {
				
				req.setAttribute("outcomeB", "bill failed");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			}
			
		}
	}
}
