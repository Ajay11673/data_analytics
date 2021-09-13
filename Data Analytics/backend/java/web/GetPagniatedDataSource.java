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
//import async.AsyncDeleteDataSource;
//import async.AsyncGetPagniatedDataSource;
import dao.DaoDSRowData;
import model.DataSourceRowData;
import security.MyEncoder;

@WebServlet(urlPatterns ="/getPagniatedDataSource",asyncSupported = true)
public class GetPagniatedDataSource extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String EXECUTOR="EXECUTOR";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin","*");
		JSONObject jsonObject = new JSONObject();
		int dataSourceId = Integer.parseInt(request.getParameter("dataSourceId"));
		int page = Integer.parseInt(request.getParameter("page"));
		int recordCount = Integer.parseInt(request.getParameter("recordCount"));
		MyEncoder encoder = new MyEncoder();
		if(encoder.isTokenValid(request)) {
			List<DataSourceRowData> dataSourceRowDatas = new DaoDSRowData().getPaginatedDataSourceData(dataSourceId, page, recordCount);

			jsonObject = new JSONObject();
			jsonObject.put("data",dataSourceRowDatas.toString());
		}
		else {
			jsonObject.put("data", "{\"data\":\"INVALID\"}");
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
