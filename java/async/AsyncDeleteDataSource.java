package async;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.xml.fastinfoset.Encoder;

import dao.DaoDataSource;
import dao.DaoUser;
import model.User;
import security.MyEncoder;

public class AsyncDeleteDataSource implements Runnable{
	AsyncContext context;
	
	public AsyncDeleteDataSource(AsyncContext context) {
		this.context = context;
	}

	@Override
	public void run() {
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		HttpServletResponse response =  (HttpServletResponse) context.getResponse();
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
		
		context.complete();
	}

}
