package dentalclinic.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dentalclinic.connection.PostgreSqlConn;

public class BranchSearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PostgreSqlConn con = new PostgreSqlConn();
		
		String province = req.getParameter("branchProvinceSearchInput");
		String city = req.getParameter("branchCitySearchInput");
		String[] dentists = con.getDentistsByBranchId(con.getBranchByLocation(province, city));
		
		req.setAttribute("dentists", dentists);
		
		req.getRequestDispatcher("receptionist_view.jsp").forward(req, resp);
	}
}
