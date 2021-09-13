package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.DaoDataSource;
import helper.UrlHelper;
import model.DataSource;
import security.MyEncoder;

@WebServlet("/updateDataSourceVisibility")
public class UpdateDataSourceVisibility extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		int value = Integer.parseInt(request.getParameter("value"));
		MyEncoder encoder = new MyEncoder();
		JSONObject jsonObject = new JSONObject();
		if(encoder.isTokenValid(request)) {
			DataSource dataSource = new DaoDataSource().getDataSource(id);
			dataSource.setAllowed_to(value);
			Boolean isUpdated = new DaoDataSource().updateDataSource(dataSource);
			jsonObject = new JSONObject();
			jsonObject.put("data", isUpdated);
		}
		else {
			jsonObject.put("data", "INVALID");
		}

		response.setContentType("application/json");
		response.getWriter().print(jsonObject);
	}

}
