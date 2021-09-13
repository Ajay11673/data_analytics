package web;

import java.io.IOException;
import java.util.List;
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
//import async.AsyncGetDataSourceHeaders;
import dao.DaoDataSourceColumnHeader;
import helper.UrlHelper;
import model.DataSourceColumnHeader;
import security.MyEncoder;


@WebServlet(urlPatterns ="/getDataSourceHeaders",asyncSupported = true)
public class GetDataSourceHeaders extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String EXECUTOR="EXECUTOR";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin","*");
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
		
	}


}
