package async;

import java.io.IOException;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import dao.DaoUser;
import model.User;
import security.MyEncoder;

public class AsyncGetAllUsers implements Runnable{
	AsyncContext context;
	public AsyncGetAllUsers(AsyncContext context) {
		this.context = context;
	}
	
	@Override
	public void run() {
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		HttpServletResponse response =  (HttpServletResponse) context.getResponse();
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
		
		context.complete();
	}

}
