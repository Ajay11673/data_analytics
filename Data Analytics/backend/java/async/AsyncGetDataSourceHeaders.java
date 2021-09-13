package async;

import java.io.IOException;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import dao.DaoDataSourceColumnHeader;
import helper.UrlHelper;
import model.DataSourceColumnHeader;
import security.MyEncoder;

public class AsyncGetDataSourceHeaders implements Runnable {
	AsyncContext context;
	
	public AsyncGetDataSourceHeaders(AsyncContext context) {
		this.context = context;
	}
	
	@Override
	public void run() {
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		HttpServletResponse response =  (HttpServletResponse) context.getResponse();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject = new UrlHelper().getJsonObject(request);
		MyEncoder encoder = new MyEncoder();
		if(encoder.isTokenValid(request)) {
			List<DataSourceColumnHeader> dataSourceColumnHeaders = new DaoDataSourceColumnHeader()
					.getColumnHeaders(jsonObject.getInt("id"));
			jsonObject = new JSONObject();
			jsonObject.put("data",dataSourceColumnHeaders.toString());
		}
		else {
			jsonObject.put("data", "{\"data\":\"INVALID\"}");
		}

		response.setContentType("application/json");
		try {
			response.getWriter().print(jsonObject.get("data"));
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		context.complete();
	}

}
