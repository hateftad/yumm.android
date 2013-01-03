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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import se.yumm.R;
import se.yumm.utils.URLS;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;
/*
 * Class is working Asynchronously, using AsynHttpClient.
 * Makes use of YummWebClient where BASEURL is hardcoded
 */


public class WebServiceHandler
{
	//URLS
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";
	private static final String YUMM_JSON = "/places/?q=&json=true";
	private static final String LOGIN = "/login/";
	private static final String PROXIMITY = "/places/proximity/?location=";
	private static final String RADIUS = "&json=true&radius=100000";
	
	//Member Variables
	private Activity m_context;
	private PersistentCookieStore m_cookieStore;
	private Cookie m_cookie;
	private boolean m_loggedIn;
	private RestaurantHandler m_restHndlr;
	
	public WebServiceHandler(Activity context)
	{
		m_context = context;
		m_cookieStore = new PersistentCookieStore(context);
		m_restHndlr = new RestaurantHandler(context);
		YummWebClient.GetClient().setCookieStore(m_cookieStore);
	}
	
	//temporary method needs to be moved
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

	//method might be split up more, but for now
	public void LoginClient()
	{
		
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
				setLoggedIn(false);
				m_dialog.dismiss();
				Toast.makeText(m_context, "Login Failed! Check Connection.", Toast.LENGTH_LONG).show();
			}
		});
		
	}
	
	public void RetrieveData(String url)
	{
		if (!m_loggedIn) {
			YummWebClient.AddHeader("Cookie", "name="+m_cookie.getValue());
		}
		
		YummWebClient.get(url, null, new AsyncHttpResponseHandler(){
			
			public ProgressDialog m_dialog;
			
			@Override
			public void onStart() {
				m_dialog = ProgressDialog.show(m_context, "Retrieving...", "Please Wait", true, false);
			}
			
			@Override
		     public void onSuccess(String response) 
		     {
		    	 System.out.println("Retrieving Data Success!");
		    	 if(isLoggedIn())
		    	 {
		    		 m_restHndlr.RestaurantsFromJson(response);
		    	 }
		    	 m_dialog.dismiss();
		     }
			
		     @Override
		     public void onFailure(Throwable e, String response) 
		     {
		        System.out.println("Retrieving Data Failed");
		        Toast.makeText(m_context, "Retreiving Data Failed! Check Connection.", Toast.LENGTH_LONG).show();
		     }
		});

	}
	
	public String UrlBuilder(String in, URLS tag )
	{
		String url = null;
		
		switch (tag) {
		case CLOSEST:
			url = PROXIMITY + in + RADIUS;
			break;

		default:
			break;
		}
		
		return url;
	}

	public boolean isLoggedIn() {
		return m_loggedIn;
	}

	public void setLoggedIn(boolean m_loggedIn) {
		this.m_loggedIn = m_loggedIn;
	}

	public RestaurantHandler GetRestaurantHandler()
	{
		return m_restHndlr;
	}
	public AsyncHttpClient GetClient()
	{
		return YummWebClient.GetClient();
	}
}
