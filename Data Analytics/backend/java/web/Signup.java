package web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import async.AppAsyncListener;
import async.AsyncSignup;
import dao.DaoUser;
import helper.UrlHelper;
import model.Roles;
import model.User;
import security.MyEncoder;


@WebServlet(urlPatterns ="/signup",asyncSupported = true)
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String EXECUTOR="EXECUTOR";
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin","*");
		JSONObject jsonObject = new UrlHelper().getJsonObject(request);
		MyEncoder encoder = new MyEncoder();
		if(encoder.isTokenValid(request)) {
			User user = new User();
			user.setUser_name(jsonObject.getString("username"));
			user.setPassword(jsonObject.getString("password"));
			user.setRole(Roles.valueOf(jsonObject.getString("role")));
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			user.setAdded_date(dateFormat.format(date));
			
			new DaoUser().saveUser(user);
		}else {
			jsonObject.put("data", "{\"data\":\"INVALID\"}");
		}

		response.setContentType("application/json");
		try {
			response.getWriter().print(jsonObject);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
