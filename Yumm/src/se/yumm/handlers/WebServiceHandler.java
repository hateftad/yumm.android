package se.yumm.handlers;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.cookie.Cookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import se.yumm.R;
import se.yumm.asyncTasks.GetPlaces;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class WebServiceHandler
{
	//URLS
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";
	private static final String YUMM_JSON = "/places/?q=&json=true";
	private static final String LOGIN = "/login/";
	
	//Member Variables
	private Activity m_context;
	private PersistentCookieStore m_cookieStore;
	private Cookie m_cookie;
	private String m_responseString = null;
	private boolean m_loggedIn;
	
	public WebServiceHandler(Activity context)
	{
		m_context = context;
		m_cookieStore = new PersistentCookieStore(context);
	}
	
	public ArrayList<String> AutoCompletePlaces(String input)
	{
		// should not store apiKey with application, but for now
		String key = m_context.getResources().getString(R.string.placesApiKey);

		ArrayList<String> resultList = null;

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try
		{
			StringBuilder sb = new StringBuilder(PLACES_API_BASE
					+ TYPE_AUTOCOMPLETE + OUT_JSON);
			sb.append("?sensor=false&key=" + key);
			sb.append("&components=country:se");
			sb.append("&input=" + URLEncoder.encode(input, "UTF-8"));

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();

			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1)
			{
				jsonResults.append(buff, 0, read);
			}

		} catch (MalformedURLException e)
		{
			Log.d("WebSerices", "getstringFromWebService", e);
			return resultList;
		} catch (IOException e)
		{
			Log.d("WebSerices", "getstringFromWebService", e);
			return resultList;
		} finally
		{
			if (conn != null)
			{
				conn.disconnect();
			}
		}
		try
		{
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

			// Extract the Place descriptions from the results
			resultList = new ArrayList<String>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++)
			{
				resultList.add(predsJsonArray.getJSONObject(i).getString(
						"description"));
			}
		} catch (JSONException e)
		{
			Log.d("WebSerices", "Cannot process JSON results", e);
		}

		return resultList;
	}

	public void LoginClient()
	{
		
    	YummWebClient.GetClient().setCookieStore(m_cookieStore);
    	
    	String username = m_context.getResources().getString(R.string.username);
    	String password = m_context.getResources().getString(R.string.password);
    	
    	RequestParams params = new RequestParams();
		params.put("email", username);
		params.put("password", password);
		
		YummWebClient.GetClient().addHeader("Content", "application/x-www-form-urlencoded");
		YummWebClient.post(LOGIN, params, new AsyncHttpResponseHandler(){
		    
			public ProgressDialog m_dialog;
			@Override
			public void onStart() {
				m_dialog = ProgressDialog.show(m_context, "Logging In..", "Please Wait", true, false);
			}
			@Override
			public void onSuccess(String response) 
			{
				System.out.println("Login Success!");
				for (Cookie cookie : m_cookieStore.getCookies()) {
					if(cookie.getName().equals("sid"))
						m_cookie = cookie;
				}
				setLoggedIn(true);
				m_dialog.dismiss();
				RetrieveData(YUMM_JSON);
			}
			@Override
			public void onFailure(Throwable e, String response) 
			{
				m_dialog.dismiss();
				Toast.makeText(m_context, "Login Failed! Check Connection.", Toast.LENGTH_LONG).show();
			}
		});
		
	}
	
	public void RetrieveData(String url)
	{

		YummWebClient.GetClient().addHeader("Cookie", "name="+m_cookie.getValue());
		YummWebClient.get(url, null, new AsyncHttpResponseHandler(){
			@Override
		     public void onSuccess(String response) 
		     {
		    	 System.out.println("Retrieving Data Success!");
		    	 setResponseString(response);
		    	 GetPlaces gp = new GetPlaces(m_context);
		    	 gp.execute(response);
		    	 
		     }
		     @Override
		     public void onFailure(Throwable e, String response) 
		     {
		        System.out.println("Retrieving Data Failed");
		     }
		});

	}

	public String getResponseString() {
		return m_responseString;
	}

	public void setResponseString(String m_responseString) {
		this.m_responseString = m_responseString;
	}

	public boolean isLoggedIn() {
		return m_loggedIn;
	}

	public void setLoggedIn(boolean m_loggedIn) {
		this.m_loggedIn = m_loggedIn;
	}
}
