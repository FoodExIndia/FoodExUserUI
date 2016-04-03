package user.com.commons;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

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
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import user.com.foodexuserui.Login;

public class HttpHelper {

	HttpClient client;
	HttpResponse response;

	public HttpHelper(){
		HttpParams params=new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params,7000);
		HttpConnectionParams.setSoTimeout(params,7000);
		client = new DefaultHttpClient(params);
	}

	public HttpResponse get(String urn,Activity currentActitvity)
	{
		Login login = new Login();
		String url = buildUrl(urn);
		HttpGet httpGet = new HttpGet(url);
			try {
			response = client.execute(httpGet);
		}
			catch(ConnectTimeoutException e){
				printconnectionError(currentActitvity);
				e.printStackTrace();
			}
			catch (ClientProtocolException e) {
				e.printStackTrace();
		}
		catch( IOException e ){
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
		return "http://192.168.0.101:8081/FoodExUser/rest/User/" + urn;
	}

	public void printconnectionError(Activity currentActivity){

		AlertDialog alertDialog = new AlertDialog.Builder(currentActivity).create();
		alertDialog.setTitle("Alert");
		alertDialog.setMessage("Our Connection is temporarily down.. Try again after some time !!!");
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		alertDialog.show();

		/*Toast.makeText(currentActivity, "Our Connection is temporarily down.. Try again after some time !!!",
				Toast.LENGTH_LONG).show();*/
		System.out.println("In Login Method");
	}

}
