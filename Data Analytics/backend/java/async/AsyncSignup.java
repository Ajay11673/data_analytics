package async;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.DaoDataSource;
import dao.DaoUser;
import helper.UrlHelper;
import model.DataSource;
import model.Roles;
import model.User;
import security.MyEncoder;

public class AsyncSignup implements Runnable{
	
	AsyncContext context;
	
	public AsyncSignup(AsyncContext context) {
		this.context = context;
	}
	
	@Override
	public void run() {
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		HttpServletResponse response =  (HttpServletResponse) context.getResponse();
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
		context.complete();
	}

}
