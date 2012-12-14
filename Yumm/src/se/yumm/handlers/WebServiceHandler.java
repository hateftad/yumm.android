package se.yumm.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import se.yumm.R;

import android.content.Context;
import android.util.Log;

public class WebServiceHandler
{

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	public static ArrayList<String> AutoCompletePlaces(String input,
			Context context)
	{
		// should not store apiKey with application, but for now
		String key = context.getResources().getString(R.string.placesApiKey);

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

	public static ArrayList<String> GetRestaurantsList(Context context)
	{
		ArrayList<String> fetchsosfromID = new ArrayList<String>();
		String result = "";
		InputStream is = null;

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username", "%@"));
		nameValuePairs.add(new BasicNameValuePair("password", "%@"));

		try
		{
			DefaultHttpClient httpclient = new DefaultHttpClient();
			httpclient.getCredentialsProvider().setCredentials(
					new AuthScope(null, -1),
					new UsernamePasswordCredentials("bob@bob.bob", "bob"));

			HttpPost httppost = new HttpPost(
					"http://yummapp.appspot.com/places");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e)
		{
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		// convert response to string
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.v("log_tag", "Append String " + result);
		} catch (Exception e)
		{
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		// parse json data
		try
		{

			JSONObject json_data = new JSONObject(result);

			Log.v("log_tag", "daily_data " + fetchsosfromID);

		} catch (JSONException e)
		{
			Log.e("log_tag", "Error parsing data " + e.toString());
		}
		return fetchsosfromID;

	}
}
