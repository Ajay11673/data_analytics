package async;

import java.io.IOException;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import dao.DaoDSRowData;
import model.DataSourceRowData;
import security.MyEncoder;

public class AsyncGetPagniatedDataSource implements Runnable {
AsyncContext context;
	
	public AsyncGetPagniatedDataSource(AsyncContext context) {
		this.context = context;
	}
	
	@Override
	public void run() {
		HttpServletRequest request = (HttpServletRequest) context.getRequest();
		HttpServletResponse response =  (HttpServletResponse) context.getResponse();
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
		context.complete();
	}
}