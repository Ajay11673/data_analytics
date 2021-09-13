package helper;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class UrlHelper {
	
	public JSONObject getJsonObject(HttpServletRequest request) {
		StringBuffer jb = new StringBuffer();
		BufferedReader reader;
		JSONObject jsonObject= null;
		try {
			reader = request.getReader();
			String ln="";
			while((ln= reader.readLine())!=null) {
				jb.append(ln);
			}
			if(!jb.toString().isEmpty())
				jsonObject = new JSONObject(jb.toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (JSONException e) {
				System.out.println(e);
			}
		return jsonObject;
	}
}
