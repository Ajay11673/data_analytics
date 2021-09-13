package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.DaoLogin;
import helper.UrlHelper;
import model.User;
import security.MyEncoder;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("login");
		JSONObject handleRequest = new UrlHelper().getJsonObject(request);
		String username = handleRequest.getString("username");
		String password = handleRequest.getString("password");
		String tempString = username;
		tempString = new MyEncoder().encode(tempString);
		System.out.println(tempString);
		User user = DaoLogin.login(username, password);
		response.setContentType("application/json");
		JSONObject handleResponse = new JSONObject();
		handleRequest.put("data", user);
		handleRequest.put("token", tempString);
		response.getWriter().print(handleRequest);
		//System.out.println(handleRequest);
		
	}

}
