package user.com.commons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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

	public  HttpResponse post(String urn, String requestJson)
	{
		String url = buildUrl(urn);
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("signUpJson", requestJson));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(param));
			response = client.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private String buildUrl(String urn)
	{
		return "http://192.168.0.103:8081/FoodExUser/rest/User/" + urn;
	}

}
