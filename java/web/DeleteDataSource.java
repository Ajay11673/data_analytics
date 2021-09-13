package web;

import java.io.IOException;
//import java.util.concurrent.ThreadPoolExecutor;

//import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

//import async.AppAsyncListener;
//import async.AsyncDeleteDataSource;
import dao.DaoDataSource;
import dao.DaoUser;
import model.User;
import security.MyEncoder;

@WebServlet(urlPatterns = "/deleteDataSource",asyncSupported = true)
public class DeleteDataSource extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private static final String EXECUTOR="EXECUTOR";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin","*");
		JSONObject jsonObject = new JSONObject();
		int id = Integer.parseInt(request.getParameter("id"));
		int userId = Integer.parseInt(request.getParameter("user_id"));
		MyEncoder encoder = new MyEncoder();
		if(encoder.isTokenValid(request)) {
			new DaoDataSource().deleteFromDataSource(id);
			User user = new DaoUser().getUserById(userId);
			
			
			jsonObject.put("data",user);
		}
		else {
			jsonObject.put("data","{\"data\":\"INVALID\"}");
		}

		response.setContentType("application/json");
		
		try {
			response.getWriter().print(jsonObject.get("data"));
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}



}
