package web;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
//import java.util.concurrent.ThreadPoolExecutor;

//import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.json.JSONException;
import org.json.JSONObject;

//import async.AppAsyncListener;
//import async.AsyncAddDataSource;
import dao.DaoDSRowData;
import dao.DaoDataSource;
import dao.DaoDataSourceColumnHeader;
import dao.DaoUser;
import model.DataSource;
import model.DataSourceColumnHeader;
import security.MyEncoder;

@MultipartConfig
@WebServlet(urlPatterns = "/addDataSource",asyncSupported = true)
public class AddDataSource extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String EXECUTOR="EXECUTOR";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin","*");
		MyEncoder encoder = new MyEncoder();
		Part userPart;
		//regex ",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))"
		Part filePart;
		Part dataSourcePart;
		JSONObject jsonObject = new JSONObject();
		if(encoder.isTokenValid(request)) {
			try {
				filePart= request.getPart("file");
				dataSourcePart = request.getPart("fileName");
				userPart = request.getPart("userData");
				
				int id = getUserId(userPart);
				String dataSourceName = getDataSourceName(dataSourcePart);
				BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream()));
				String columnString = reader.readLine();
				List<DataSourceColumnHeader> columnHeaders = getColumnHeaders(columnString);
				
				DataSource dataSource = new DataSource(dataSourceName);
				dataSource.setUser(new DaoUser().getUserById(id));
				dataSource.setAllowed_to(id);
				Date date =new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
				dataSource.setCreated_date(dateFormat.format(date));
				
				new DaoDataSource().saveDataSource(dataSource);
				new DaoDataSourceColumnHeader().saveDSColumnHeader(dataSource, columnHeaders);
				int count = new DaoDSRowData().saveDRowData(reader, columnHeaders, dataSource);
				
				dataSource.setRow_count(count);
				new DaoDataSource().updateDataSource(dataSource);
				
				System.out.println(getUserId(userPart));
				System.out.println(getDataSourceName(dataSourcePart));
				System.out.println(getColumnHeaders(columnString));

				jsonObject.put("data", new DaoUser().getUserById(id));
				
			} catch (IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try {
				filePart= request.getPart("file");
				dataSourcePart = request.getPart("fileName");
				userPart = request.getPart("userData");
			} catch (IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("invalid");
			jsonObject.put("data","{\"data\":\"INVALID\"}");
		}
		response.setContentType("application/json");
		try {
			response.getWriter().print(jsonObject.get("data"));
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getUserId(Part userPart) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(userPart.getInputStream()));
		String userDetail=reader.readLine();
		JSONObject jsonObject = new JSONObject(userDetail);
		return Integer.parseInt(jsonObject.getString("id"));
	}
	public String getDataSourceName(Part dataSourcePart) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(dataSourcePart.getInputStream()));
		String fileName=reader.readLine();
		return fileName;
	}
	
	public List<DataSourceColumnHeader> getColumnHeaders(String columnString) throws IOException{
		
		List<String> headerStrings= Arrays.asList(columnString.split(","));
		List<DataSourceColumnHeader> dataSourceColumnHeaders = new ArrayList<>();
		for(String header : headerStrings) {
			DataSourceColumnHeader columnHeader = new DataSourceColumnHeader();
			columnHeader.setColumn_name(header);
			dataSourceColumnHeaders.add(columnHeader);
		}
		
		return dataSourceColumnHeaders;
	}
}
