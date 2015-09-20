package user.com.commons;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpHelper {
	
	HttpClient client;
	HttpResponse response;
	
	public HttpHelper(){
		client = new DefaultHttpClient();
	}
	
	public HttpResponse get(String urn)
	{
		String url = buildUrl(urn);
		HttpGet httpGet = new HttpGet(url);
		try {
			response = client.execute(httpGet);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private String buildUrl(String urn)
	{
		return "http://192.168.0.104:8081/FoodExUser/rest/User/" + urn;
	}

}
