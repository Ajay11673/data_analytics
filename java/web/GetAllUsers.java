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
//import async.AsyncGetAllUsers;
import dao.DaoUser;
import security.MyEncoder;

@WebServlet(urlPatterns ="/getAllUsers",asyncSupported = true)
public class GetAllUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String EXECUTOR="EXECUTOR";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin","*");
		JSONObject jsonObject = new JSONObject();
		System.out.println(request.getParameter("page")+", "+request.getParameter("rowCount"));
		
		int pageNo= Integer.parseInt(request.getParameter("page"));
		int rowCount = Integer.parseInt(request.getParameter("rowCount"));
		MyEncoder encoder = new MyEncoder();
		if(encoder.isTokenValid(request)) {
			System.out.println(pageNo+", "+rowCount);
			jsonObject = new DaoUser().getUsersWithLimit(pageNo,rowCount);
		}
		else {
			jsonObject.put("data","INVALID");
		}
		
		response.setContentType("application/json");
		try {
			response.getWriter().print(jsonObject);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}

	}


}
