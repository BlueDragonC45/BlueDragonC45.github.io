package dentalclinic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dentalclinic.connection.PostgreSqlConn;
import dentalclinic.entities.Invoice;
import dentalclinic.entities.Patient;
import dentalclinic.entities.PatientBilling;
import dentalclinic.entities.InsuranceClaim;
import dentalclinic.entities.Appointment;

@SuppressWarnings("serial")
public class AppointmentServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PostgreSqlConn con = new PostgreSqlConn();
		
		String appointmentDateQuery = req.getParameter("appointmentDateQuery");
		
		ArrayList<Appointment> appointmentsDay = con.getAppointmentsByDate(appointmentDateQuery);
		ArrayList<String> occupiedHours = new ArrayList<String>();
		for (int i = 0 ; i < appointmentsDay.size(); i++) {
			occupiedHours.add(appointmentsDay.get(i).getAppointmentStartTime())
		}
			
			/*
			int outcomeCode = con.billUser(newBill, insuranceClaim);
			if (outcomeCode == 0) {

				req.setAttribute("patientSINAfterBill", patientSINBillForm);
				req.setAttribute("billTotal", total);
				
				req.setAttribute("outcomeB", "bill success");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
				
			} else if (outcomeCode == 1) {
				
				req.setAttribute("outcomeB", "employee not found");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			} else if (outcomeCode == 2) {
				
				req.setAttribute("outcomeB", "already paid in full");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			} else if (outcomeCode == 3) {
				
				req.setAttribute("outcomeB", "unknown error");
				req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
			}*/
			
		
	}
}
