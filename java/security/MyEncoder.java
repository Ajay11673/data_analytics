package security;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class MyEncoder {
	public String encode(String str) {
		String dateTime = ""+System.currentTimeMillis();
		StringBuffer buffer = new StringBuffer();
		str=str+"~"+dateTime;
		//System.out.println(str);
		char ch[]  = str.toCharArray();
		for(char c: ch) {
			buffer.append((int)c);
			buffer.append(",");
		}
		buffer.replace(buffer.length()-1,buffer.length(),"");
		String encodeString = buffer.toString();
		
		return encodeString;
	}
	public String decode(String str) {
		StringBuffer buffer = new StringBuffer();
		String ch[] = str.split(",");
		for(String c:ch ) {
			
			buffer.append((char) Integer.parseInt(c));
		}
		String jsonData = buffer.toString();

		return jsonData;
	}
	public String passwordEncode(String str) {
		StringBuffer buffer = new StringBuffer();
		char ch[]  = str.toCharArray();
		for(char c: ch) {
			buffer.append((int)c);
			buffer.append(",");
		}
		buffer.replace(buffer.length()-1,buffer.length(),"");
		String encodeString = buffer.toString();
		
		return encodeString;
	}
	public boolean isTokenValid(HttpServletRequest request) {
		String header = request.getHeader("auth");
		//System.out.println(header);
		long token = Long.parseLong(decode(header).split("~")[1]);
		
		if(System.currentTimeMillis()-token<1000*60*60*2) {
			return true;
		}
		return false;
		
	}

}
