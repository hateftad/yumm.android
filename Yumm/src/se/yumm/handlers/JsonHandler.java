package se.yumm.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import se.yumm.R;
import se.yumm.poi.Restaurants;
import se.yumm.utils.IO;

import android.content.Context;
import android.util.Log;

public class JsonHandler
{

	private InputStream m_inStream = null;
	private JSONObject m_jsonObj = null;
	private String json = "";
	static DefaultHttpClient client;
	private final Context m_context;

	// TAGS
	private static final String WEBSITE = "www";
	private static final String DESCRIPTION = "description";
	private static final String MENU = "menu";
	private static final String NAME = "name";
	private static final String PHONENR = "phone";
	private static final String PRICERNG = "price_range";
	private static final String ADDRESS = "address";
	private static final String LOCATION = "location";
	private static final String MENUHDR = "menu_headers";

	// username & password
	final String username = "bob@bob.bob";
	final String password = "bob";
	String host = "http://www.yummapp.appspot.com/";
	String uri = "http://yummapp.appspot.com/places/?q=&json=true";
	String urls = "http://yummapp.appspot.com/login/";

	public JsonHandler(Context context)
	{
		this.m_context = context;
	}

	public String JsonFromRawFile() throws IOException
	{
		m_inStream = m_context.getResources().openRawResource(R.raw.json_file);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];

		try
		{
			Reader reader = new BufferedReader(new InputStreamReader(
					m_inStream, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1)
			{
				writer.write(buffer, 0, n);
			}

		} finally
		{
			m_inStream.close();
		}

		String result = writer.toString();

		return result;

	}

	public ArrayList<Restaurants> GetListFromGson(String jsonString)
	{

		Type listType = new TypeToken<Collection<Restaurants>>()
		{
		}.getType();

		Gson gson = new Gson();

		ArrayList<Restaurants> list = gson.fromJson(jsonString, listType);

		return list;
	}

	@SuppressWarnings("unused")
	public List<Restaurants> JsonSorter(String jsonString)
	{
		Restaurants restaurant = null;
		List<Restaurants> rList = null;
		try
		{
			// JSONObject json = new JSONObject(jsonString);
			JSONArray restaurants = new JSONArray(jsonString);

			for (int i = 0; i < restaurants.length(); i++)
			{
				restaurant = new Restaurants();
				JSONObject rest = restaurants.getJSONObject(i);
				restaurant.setName(rest.getString(NAME));
				restaurant.setDescription(rest.getString(DESCRIPTION));
				restaurant.setPhoneNr(rest.getString(PHONENR));
				restaurant.setWebpage(rest.getString(WEBSITE));
				// location, address, price range

				JSONArray m = rest.getJSONArray(MENUHDR);

				JSONArray menus = rest.getJSONArray(MENU);
				// specify which menu, sushi or bento
				JSONArray menu = menus.getJSONArray(i);
				JSONArray z = menu.getJSONArray(i);
				JSONArray b = z.optJSONArray(i);
				for (int j = 0; j < z.length(); j++)
				{
					// JSONObject b = new JSONObject(m1);
					JSONArray first = menu.getJSONArray(j);
					JSONArray w = first.getJSONArray(j + 1);
					Object q = w.get(j);

					// Object z = q.get(j);
					// JSONObject b = z.getJSONObject(j);
					// String q = w.toString();
					System.out.println(w);
					// String d = menuIt.toString();
				}
				rList.add(restaurant);

			}
		} catch (JSONException e)
		{
			Log.v("YUMM", e.toString());
		}
		return rList;

	}

	public JSONObject GetJsonFromURL(String url)
	{

		HttpParams httpParam;
		try
		{
			httpParam = new BasicHttpParams();
			String auth = android.util.Base64.encodeToString(
					(username + ":" + password).getBytes("UTF-8"),
					android.util.Base64.NO_WRAP);
			HttpGet request = new HttpGet(urls);
			request.addHeader("Authorization", "Basic " + auth);
			request.addHeader("Content-Type",
					"application/x-www-form-urlencoded");

			HttpConnectionParams.setSoTimeout(httpParam, 2000);
			client = new DefaultHttpClient(httpParam);

			HttpResponse response = client.execute(request);
			String userAuth = EntityUtils.toString(response.getEntity());

			System.out.println("Data. in login.." + userAuth);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK)
			{
				HttpEntity entity = response.getEntity();
				m_inStream = entity.getContent();
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (ClientProtocolException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			json = IO.ConvertStreamToString(m_inStream);
		} catch (Exception e)
		{

		}

		try
		{
			m_jsonObj = new JSONObject(json);
		} catch (JSONException e)
		{
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		return m_jsonObj;
	}

	public JSONObject MakeWebCall(String inUrl) throws IOException
	{

		Authenticator.setDefault(new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(username, password
						.toCharArray());
			}
		});

		try
		{

			HttpURLConnection connection = (HttpURLConnection) new URL(inUrl)
					.openConnection();
			connection.setUseCaches(false);
			List<NameValuePair> form = new ArrayList<NameValuePair>();

			form.add(new BasicNameValuePair("email", username));
			form.add(new BasicNameValuePair("password", password));

			// connection.setEntity(new UrlEncodedFormEntity(form, HTTP.UTF_8));
			connection.connect();

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK)
			{
				m_inStream = connection.getInputStream();
			}

		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (ClientProtocolException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			json = IO.ConvertStreamToString(m_inStream);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try
		{
			m_jsonObj = new JSONObject(json);
		} catch (JSONException e)
		{
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		return m_jsonObj;
	}

	public void WebCall(String URL)
	{
		try
		{
			// String s=status.getText().toString();

			HttpPost post = new HttpPost(uri);

			String auth = android.util.Base64.encodeToString(
					(username + ":" + password).getBytes("UTF-8"),
					android.util.Base64.NO_WRAP);

			post.addHeader("Authorization", "Basic " + auth);

			List<NameValuePair> form = new ArrayList<NameValuePair>();

			form.add(new BasicNameValuePair("email", username));
			form.add(new BasicNameValuePair("password", password));

			post.setEntity(new UrlEncodedFormEntity(form, HTTP.UTF_8));

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = client.execute(post, responseHandler);
			JSONObject response = new JSONObject(responseBody);
		} catch (Throwable t)
		{
			Log.e("Patchy", "Exception in WebCall()", t);
			// goBlooey(t);
		}
		/*
		 * finally { m_inStream.close(); }
		 */

	}

}
